/*
 * This file is part of OpenGotha.
 *
 * OpenGotha is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * OpenGotha is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenGotha. If not, see <http://www.gnu.org/licenses/>.
 */

package ru.gofederation.api

import info.vannier.gotha.GeneralParameterSet
import info.vannier.gotha.PlacementCriterion
import info.vannier.gotha.Player
import info.vannier.gotha.ScoredPlayer
import info.vannier.gotha.Tournament
import info.vannier.gotha.TournamentException
import info.vannier.gotha.TournamentInterface
import info.vannier.gotha.TournamentPlayerDoubleException
import ru.gofederation.gotha.model.Game
import ru.gofederation.gotha.model.PlayerRegistrationStatus
import ru.gofederation.gotha.model.Rating
import ru.gofederation.gotha.model.RatingOrigin

fun RgfTournament.rgf2gotha(importMode: RgfTournament.ImportMode): Pair<TournamentInterface, RgfTournamentImportReport> {
    val tournament = Tournament()
    val report = RgfTournamentImportReport()

    val tps = tournament.tournamentParameterSet.also { tps ->
        tps.initForMM()
    }

    tps.generalParameterSet.also { gps ->
        gps.name = this.name
        gps.shortName = this.name // TODO actually shorten the name
        gps.beginDate = this.startDate.toJvmDate()
        gps.endDate = this.endDate.toJvmDate()
        gps.rgfId = this.id ?: 0
        gps.numberOfRounds = this.roundCount ?: 5
        this.timing?.also { timing ->
            gps.basicTime = timing.basicTime
            when (timing) {
                is RgfTournament.Timing.SuddenDeath -> {
                    gps.complementaryTimeSystem = GeneralParameterSet.GEN_GP_CTS_SUDDENDEATH
                }
                is RgfTournament.Timing.Canadian -> {
                    gps.complementaryTimeSystem = GeneralParameterSet.GEN_GP_CTS_CANBYOYOMI
                    gps.nbMovesCanTime = timing.boyomiMoves
                    gps.canByoYomiTime = timing.boyomiTime
                }
                is RgfTournament.Timing.Japanese -> {
                    gps.complementaryTimeSystem = GeneralParameterSet.GEN_GP_CTS_STDBYOYOMI
                    gps.stdByoYomiTime = timing.boyomiTime
                    // TODO: number of moves?
                }
                is RgfTournament.Timing.Fischer -> {
                    gps.complementaryTimeSystem = GeneralParameterSet.GEN_GP_CTS_FISCHER
                    gps.fischerTime = timing.bonus
                }
            }
        }
    }

    when (importMode) {
        RgfTournament.ImportMode.APPLICATIONS ->
            if (null != this.applications) {
                for (application in this.applications as List<RgfTournament.Application>) {
                    val player = Player.Builder().also { builder ->
                        builder.firstName = application.firstName
                        builder.name = application.lastName
                        if (application.playerId != null) {
                            builder.rgfId = application.playerId?:0
                        }
                        try {
                            val rating = Rating.clampRating(RatingOrigin.RGF, Integer.parseInt(application.rating))
                            builder.setRating(rating, RatingOrigin.RGF)
                            builder.rank = Rating.ratingToRank(RatingOrigin.RGF, rating)
                        } catch (e: NumberFormatException) {
                            // NOOP is ok
                        }
                    }.build()

                    try {
                        tournament.addPlayer(player)
                        report.players += 1
                    } catch (e: TournamentPlayerDoubleException) {
                        report.playerDoubles.add(e.playerName)
                        report.hadError = true
                    } catch (e: TournamentException) {
                        report.reportBuilder.append(e.message).append("\n")
                        report.hadError = true
                    }
                }
            }

        RgfTournament.ImportMode.PARTICIPANTS -> {
            val playersMap = mutableMapOf<Int, Player>()

            if (null != this.players) {
                for (apiPlayer in this.players!!) {
                    val player = Player.Builder().also { builder ->
                        builder.firstName = apiPlayer.firstName
                        builder.name = apiPlayer.lastName
                        if (apiPlayer.playerId != null) {
                            builder.rgfId = apiPlayer.playerId ?: 0
                        }
                        builder.isRgfNew = apiPlayer.newPlayer
                        builder.isRgfAssessmentRating = apiPlayer.assessmentRating
                        builder.setRating(apiPlayer.rating, RatingOrigin.RGF)
                        builder.rank = Rating.ratingToRank(RatingOrigin.RGF, apiPlayer.rating)
                        builder.registrationStatus = PlayerRegistrationStatus.FINAL
                        builder.smmsByHand = apiPlayer.mm0_4
                    }.build()
                    playersMap[apiPlayer.id] = player

                    try {
                        tournament.addPlayer(player)
                        report.players += 1
                    } catch (e: TournamentPlayerDoubleException) {
                        report.playerDoubles.add(e.playerName)
                        report.hadError = true
                    } catch (e: TournamentException) {
                        report.reportBuilder.append(e.message).append("\n")
                        report.hadError = true
                    }
                }
            }

            if (null != this.games) {
                for (apiGame in this.games!!) {
                    val blackPlayer = playersMap[apiGame.player1id]
                    val whitePlayer = playersMap[apiGame.player2id]
                    val roundNumber = apiGame.round - 1
                    if (null != whitePlayer) {
                        if (null != blackPlayer) {
                            val game = Game.create(
                                blackPlayer = blackPlayer,
                                whitePlayer = whitePlayer,
                                round = roundNumber,
                                board = if (apiGame.board == 0) {
                                    (tournament
                                        .gamesList(roundNumber)
                                        .maxBy { it.board }
                                        ?.board ?: 0) + 1
                                } else {
                                    apiGame.board
                                },
                                handicap = apiGame.handicap,
                                knownColor = apiGame.color == RgfTournament.Game.Color.PLAYER_1_BLACK,
                                result = apiGame.result.toGotha()
                            )
                            val success = tournament.addGame(game)
                            if (success) report.games += 1
                        } else {
                            tournament.setByePlayer(whitePlayer, apiGame.round - 1)
                        }
                    }
                }
            }
        }
    }

    return Pair(tournament, report)

}

fun gotha2rgf(gotha: TournamentInterface): RgfTournament {
    val tps = gotha.tournamentParameterSet
    val gps = tps.generalParameterSet
    val pps = tps.placementParameterSet

    val komi = (java.lang.Float.valueOf(gps.strKomi) * 4).toInt()

    val finalRound = gps.numberOfRounds - 1

    val playersMap = HashMap<String, RgfTournament.Player>()
    val players = gotha.orderedScoredPlayersList(finalRound, pps)
        .mapIndexed { index, scoredPlayer ->
            val player = RgfTournament.Player(
                id = index + 1,
                playerId = if (scoredPlayer.rgfId > 0) scoredPlayer.rgfId else null,
                newPlayer = scoredPlayer.isRgfNew,
                assessmentRating = scoredPlayer.isRgfAssessmentRating,
                firstName = scoredPlayer.firstName,
                lastName = scoredPlayer.name,
                patronymic = scoredPlayer.patronymic,
                rating = scoredPlayer.rating.value,
                place = index + 1,
                mm0_4 = scoredPlayer.smms(gps),
                mmF_4 = getCoef(scoredPlayer, finalRound, PlacementCriterion.MMS),
                sos_4 = getCoef(scoredPlayer, finalRound, PlacementCriterion.SOSM),
                sodos_4 = getCoef(scoredPlayer, finalRound, PlacementCriterion.SODOSM)
            )
            playersMap[scoredPlayer.keyString] = player
            player
        }

    val games = gotha.gamesList().map { game ->
        RgfTournament.Game(
            player1id = playersMap[game.blackPlayer.keyString]?.id,
            player2id = playersMap[game.whitePlayer.keyString]?.id,
            color = if (game.knownColor) RgfTournament.Game.Color.PLAYER_1_BLACK else RgfTournament.Game.Color.UNKNOWN,
            result = resultFromGotha(game.result),
            round = game.round + 1,
            board = game.board,
            komi_4 = komi,
            handicap = game.handicap
        )
    }.toMutableList()

    val byePlayers = gotha.byePlayers
    for (i in byePlayers.indices) {
        val player = byePlayers[i]
        if (null != player && playersMap.containsKey(player.keyString)) {
            games.add(RgfTournament.Game(
                player1id = null,
                player2id = playersMap[player.keyString]?.id,
                color = RgfTournament.Game.Color.UNKNOWN,
                result = RgfTournament.Game.Result.PLAYER_2_WIN,
                round = i + 1,
                board = 0,
                komi_4 = komi,
                handicap = 0
            ))
        }
    }

    return RgfTournament(
        id = if (gps.hasRgfId()) gps.rgfId else 0,
        name = gps.name,
        location = gps.location,
        startDate = gps.beginDate.toApiDate(),
        endDate = gps.endDate.toApiDate(),
        komi_4 = komi,
        // TODO system = ...
        roundCount = gps.numberOfRounds,
        category = RgfTournament.Category.NOT_SET,
        players = players,
        games = games
    )
}

private fun getCoef(player: ScoredPlayer, round: Int, crit: PlacementCriterion): Int {
    return 4 * player.getCritValue(crit, round) / crit.coef
}

fun resultFromGotha(result: Game.Result) = when (result) {
    Game.Result.BLACKWINS -> RgfTournament.Game.Result.PLAYER_1_WIN
    Game.Result.WHITEWINS -> RgfTournament.Game.Result.PLAYER_2_WIN
    Game.Result.BLACKWINS_BYDEF -> RgfTournament.Game.Result.PLAYER_1_WIN_BYREF
    Game.Result.WHITEWINS_BYDEF -> RgfTournament.Game.Result.PLAYER_2_WIN_BYREF
    Game.Result.EQUAL -> RgfTournament.Game.Result.TIE
    Game.Result.BOTHLOSE -> RgfTournament.Game.Result.BOTH_LOST
    Game.Result.UNKNOWN -> RgfTournament.Game.Result.NOT_PLAYED
    Game.Result.EQUAL_BYDEF -> RgfTournament.Game.Result.TIE_BYREF
    else -> RgfTournament.Game.Result.UNKNOWN
}

fun RgfTournament.Game.Result.toGotha() = when (this) {
    RgfTournament.Game.Result.PLAYER_1_WIN -> Game.Result.BLACKWINS
    RgfTournament.Game.Result.PLAYER_2_WIN -> Game.Result.WHITEWINS
    RgfTournament.Game.Result.PLAYER_1_WIN_BYREF -> Game.Result.BLACKWINS_BYDEF
    RgfTournament.Game.Result.PLAYER_2_WIN_BYREF -> Game.Result.WHITEWINS_BYDEF
    RgfTournament.Game.Result.NOT_PLAYED -> Game.Result.UNKNOWN
    RgfTournament.Game.Result.TIE -> Game.Result.EQUAL
    RgfTournament.Game.Result.BOTH_LOST -> Game.Result.BOTHLOSE
    RgfTournament.Game.Result.UNKNOWN -> Game.Result.UNKNOWN
    RgfTournament.Game.Result.TIE_BYREF -> Game.Result.EQUAL_BYDEF
}
