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
 * along with OpenGotha.  If not, see <http://www.gnu.org/licenses/>.
 */

package ru.gofederation.api

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import kotlin.math.abs

/**
 * Temporary Date class.
 *
 * TODO: replace with [https://github.com/korlibs/klock]'s Date once kotlinx.serialization supports inline class serialization
 */
@Serializable(with = Date.Serializer::class)
class Date(private val encoded: Int) : Comparable<Date> {
    companion object {
        private val regex: Regex = Regex("(\\d{4})-(\\d{2})-(\\d{2})")

        operator fun invoke() = currentDate()
        operator fun invoke(year: Int, month: Int, day: Int) = Date((year shl 16) or (month shl 8) or (day shl 0))
        operator fun invoke(dateS: String): Date {
            regex.find(dateS)?.let { match ->
                val parts = match.groupValues
                require(parts.size == 4)
                return Date(parts[1].toInt(), parts[2].toInt(), parts[3].toInt())
            }
            throw IllegalArgumentException("Failed to parse date: $dateS")
        }
    }

    val year: Int get() = encoded shr 16
    val month: Int get() = (encoded ushr 8) and 0xFF
    val day: Int get() = (encoded ushr 0) and 0xFF

    override fun toString(): String = "${if (year < 0) "-" else ""}${abs(year).toString()}-${abs(month).toString().padStart(2, '0')}-${abs(day).toString().padStart(2, '0')}"
    override fun compareTo(other: Date): Int = this.encoded.compareTo(other.encoded)
    override fun equals(other: Any?): Boolean {
        return if (other is Date) {
            encoded == encoded
        } else {
            super.equals(other)
        }
    }

    override fun hashCode() = encoded

    object Serializer: KSerializer<Date> {
        override val descriptor: SerialDescriptor = StringDescriptor.withName("Default")
        override fun deserialize(decoder: Decoder) = Date(decoder.decodeString())
        override fun serialize(encoder: Encoder, obj: Date) = encoder.encodeString(obj.toString())
    }

}

expect fun currentDate(): Date
