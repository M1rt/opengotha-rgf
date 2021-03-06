/*
 * JFrPlayersMMG.java
 */

package info.vannier.gotha;

import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author  Administrateur
 */
public class JFrPlayersMMG extends javax.swing.JFrame{
    private static final long REFRESH_DELAY = 2000;
    private long lastComponentsUpdateTime = 0;


    private final int NAME_COL = 0;
    private final int FIRSTNAME_COL = 1;
    private final int RANK_COL = 2;
    private final int CORR_COL = 3;
    private final int RATING_COL = 4;
    private TournamentInterface tournament;
    /** Creates new form JFrPlayersMMG */
    public JFrPlayersMMG() {
        initComponents();
        setupRefreshTimer();
    }

    private volatile boolean running = true;
    javax.swing.Timer timer = null;
    private void setupRefreshTimer() {
        ActionListener taskPerformer;
        taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (!running){
                    timer.stop();
                }
                try {
                    if (tournament.getLastTournamentModificationTime() > lastComponentsUpdateTime) {
                        updateAllViews();
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(JFrGamesResults.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        timer = new javax.swing.Timer((int) REFRESH_DELAY, taskPerformer);
        timer.start();
    }


    /** Creates new form JFrPlayerManager */
    public JFrPlayersMMG(TournamentInterface tournament) throws RemoteException{
        this.tournament = tournament;

        initComponents();
        customInitComponents();
        setupRefreshTimer();

    }

    private void initComponents() {

        pnlMMG0 = new javax.swing.JPanel();
        scpMMG0 = new javax.swing.JScrollPane();
        tblMMG0 = new javax.swing.JTable();
        txfNbPlayersMMG0 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        pnlMMG1 = new javax.swing.JPanel();
        scpMMG1 = new javax.swing.JScrollPane();
        tblMMG1 = new javax.swing.JTable();
        txfNbPlayersMMG1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        pnlMMG2 = new javax.swing.JPanel();
        scpMMG2 = new javax.swing.JScrollPane();
        tblMMG2 = new javax.swing.JTable();
        txfNbPlayersMMG2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        pnlbarM1 = new javax.swing.JPanel();
        scpBarM1 = new javax.swing.JScrollPane();
        tblBarM1 = new javax.swing.JTable();
        txfNbPlayersBarM1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnM1_0 = new javax.swing.JButton();
        btn0_M1 = new javax.swing.JButton();
        btn0_1 = new javax.swing.JButton();
        btn1_0 = new javax.swing.JButton();
        btn1_2 = new javax.swing.JButton();
        btn2_1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnResetOnRk = new javax.swing.JButton();
        txfMMBar = new javax.swing.JTextField();
        btnHelp = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("McMahon Groups");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new MigLayout("insets dialog", "[sg a][sg b][sg a][sg b][sg a]", "[sg c][][sg c]"));

        pnlMMG0.setBorder(javax.swing.BorderFactory.createTitledBorder("Bar (Top Group)"));
        pnlMMG0.setLayout(new MigLayout("insets panel"));

        txfNbPlayersMMG0.setEditable(false);
        txfNbPlayersMMG0.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txfNbPlayersMMG0.setText("1234");
        pnlMMG0.add(txfNbPlayersMMG0, "wmin 48lp");

        jLabel2.setText("players");
        pnlMMG0.add(jLabel2, "wrap");

        tblMMG0.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Last name", "First name", "Rank", "Corr", "Rating"
            }
        ));
        scpMMG0.setViewportView(tblMMG0);

        pnlMMG0.add(scpMMG0, "spanx 2");

        getContentPane().add(pnlMMG0);

        btn0_1.setText(">>>");
        btn0_1.addActionListener(this::btn0_1ActionPerformed);
        getContentPane().add(btn0_1, "split 2, flowy");

        btn1_0.setText("<<<");
        btn1_0.addActionListener(this::btn1_0ActionPerformed);
        getContentPane().add(btn1_0);

        pnlMMG1.setBorder(javax.swing.BorderFactory.createTitledBorder("Bar + 1 (Super Group)"));
        pnlMMG1.setLayout(new MigLayout("insets panel"));

        txfNbPlayersMMG1.setEditable(false);
        txfNbPlayersMMG1.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txfNbPlayersMMG1.setText("1234");
        pnlMMG1.add(txfNbPlayersMMG1, "wmin 48lp");

        jLabel3.setText("players");
        pnlMMG1.add(jLabel3, "wrap");

        tblMMG1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Last name", "First name", "Rank", "Corr", "Rating"
            }
        ));
        scpMMG1.setViewportView(tblMMG1);

        pnlMMG1.add(scpMMG1, "spanx 2");

        getContentPane().add(pnlMMG1);

        btn1_2.setText(">>>");
        btn1_2.addActionListener(this::btn1_2ActionPerformed);
        getContentPane().add(btn1_2, "split 2, flowy");

        btn2_1.setText("<<<");
        btn2_1.addActionListener(this::btn2_1ActionPerformed);
        getContentPane().add(btn2_1);

        pnlMMG2.setBorder(javax.swing.BorderFactory.createTitledBorder("Bar + 2 (Super-super Group)"));
        pnlMMG2.setLayout(new MigLayout("insets panel"));

        txfNbPlayersMMG2.setEditable(false);
        txfNbPlayersMMG2.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txfNbPlayersMMG2.setText("1234");
        pnlMMG2.add(txfNbPlayersMMG2, "wmin 48lp");

        jLabel4.setText("players");
        pnlMMG2.add(jLabel4, "wrap");

        tblMMG2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Last name", "First name", "Rank", "Corr", "Rating"
            }
        ));
        scpMMG2.setViewportView(tblMMG2);

        pnlMMG2.add(scpMMG2, "spanx 2");

        getContentPane().add(pnlMMG2, "wrap");

        btnM1_0.setText("^");
        btnM1_0.addActionListener(this::btnM1_0ActionPerformed);
        getContentPane().add(btnM1_0, "split 2");

        btn0_M1.setText("V");
        btn0_M1.addActionListener(this::btn0_M1ActionPerformed);
        getContentPane().add(btn0_M1, "wrap");

        pnlbarM1.setBorder(javax.swing.BorderFactory.createTitledBorder("Bar - 1"));
        pnlbarM1.setLayout(new MigLayout("insets panel"));

        txfNbPlayersBarM1.setEditable(false);
        txfNbPlayersBarM1.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txfNbPlayersBarM1.setText("1234");
        pnlbarM1.add(txfNbPlayersBarM1, "wmin 48lp");

        jLabel5.setText("players");
        pnlbarM1.add(jLabel5, "wrap");

        tblBarM1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Last name", "First name", "Rank", "Corr", "Rating"
            }
        ));
        scpBarM1.setViewportView(tblBarM1);

        pnlbarM1.add(scpBarM1, "spanx 2");

        getContentPane().add(pnlbarM1);
        pnlbarM1.setBounds(10, 350, 290, 210);

        JPanel panel = new JPanel(new MigLayout("flowy, insets 0, fill", "[fill]", "[fill]unrel[]"));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(new MigLayout("insets panel", "push[][]push", "push[]unrel[]push"));

        jLabel1.setText("McMahon bar");
        jPanel1.add(jLabel1);

        txfMMBar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txfMMBarFocusLost(evt);
            }
        });
        jPanel1.add(txfMMBar, "wmin 32lp, ax r, wrap");

        btnResetOnRk.setText("Reset All SMMS according to rank");
        btnResetOnRk.setToolTipText("SMMS will be reset on a \"1D = 1D => SMMS = 30 \" basis");
        btnResetOnRk.addActionListener(this::btnResetOnRkActionPerformed);
        jPanel1.add(btnResetOnRk, "spanx 2");

        panel.add(jPanel1);

        btnHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/info/vannier/gotha/gothalogo16.jpg")));
        btnHelp.setText("help");
        btnHelp.addActionListener(this::btnHelpActionPerformed);
        panel.add(btnHelp, "split 2, flowx, tag help");

        btnClose.setText("Close");
        btnClose.addActionListener(this::btnCloseActionPerformed);
        panel.add(btnClose, "tag cancel");

        getContentPane().add(panel, "skip 1, spanx 3, grow");

        pack();
    }

    private void txfMMBarFocusLost(java.awt.event.FocusEvent evt) {
        TournamentParameterSet tps = null;
        GeneralParameterSet gps = null;
        try {
            tps = tournament.getTournamentParameterSet();
            gps = tps.getGeneralParameterSet();
        } catch (RemoteException ex) {
            Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        int oldGenMMBar = gps.getGenMMBar();
        int newGenMMBar = Player.convertKDPToInt(this.txfMMBar.getText());
        if ((newGenMMBar > GeneralParameterSet.GEN_MM_BAR_MAX)
            || (newGenMMBar < GeneralParameterSet.GEN_MM_BAR_MIN)
            || (newGenMMBar < gps.getGenMMFloor())){
            // Error. Keep old value
            txfMMBar.setText("" + Player.convertIntToKD(oldGenMMBar));
            return;
        }
        if (newGenMMBar != oldGenMMBar){
            gps.setGenMMBar(newGenMMBar);
            try {
                tournament.setTournamentParameterSet(tps);
                this.tournamentChanged();
            } catch (RemoteException ex) {
                Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void btnResetOnRkActionPerformed(java.awt.event.ActionEvent evt) {
        ArrayList<Player> alPlayers = null;
        try {
            alPlayers = tournament.playersList();
        } catch (RemoteException ex) {
            Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        for (Player p : alPlayers){
            p.setSmmsCorrection(0);
            try {
                tournament.modifyPlayer(p, p);
            } catch (RemoteException ex) {
                Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TournamentException ex) {
                Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.tournamentChanged();
    }

    private void btn2_1ActionPerformed(java.awt.event.ActionEvent evt) {
        // get SelectedPlayers
        ArrayList<Player> alSelectedPlayers = selectedPlayersList(this.tblMMG2);
        if (alSelectedPlayers.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please, select at least one player in Bar + 2 table", "Message",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (Player p : alSelectedPlayers){
            decrementSMMS(p);
            try {
                tournament.modifyPlayer(p, p);
            } catch (RemoteException ex) {
                Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TournamentException ex) {
                Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.tournamentChanged();

    }

    private void btn0_M1ActionPerformed(java.awt.event.ActionEvent evt) {
        // get SelectedPlayers
        ArrayList<Player> alSelectedPlayers = selectedPlayersList(this.tblMMG0);
        if (alSelectedPlayers.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please, select at least one player in Bar table", "Message",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (Player p : alSelectedPlayers){
            decrementSMMS(p);
            try {
                tournament.modifyPlayer(p, p);
            } catch (RemoteException ex) {
                Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TournamentException ex) {
                Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.tournamentChanged();

    }

    private void btn0_1ActionPerformed(java.awt.event.ActionEvent evt) {
        // get SelectedPlayers
        ArrayList<Player> alSelectedPlayers = selectedPlayersList(this.tblMMG0);
        if (alSelectedPlayers.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please, select at least one player in Bar table", "Message",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (Player p : alSelectedPlayers){
            incrementSMMS(p);
            try {
                tournament.modifyPlayer(p, p);
            } catch (RemoteException ex) {
                Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TournamentException ex) {
                Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            this.tournamentChanged();
    }


    private void btnM1_0ActionPerformed(java.awt.event.ActionEvent evt) {
        // get SelectedPlayers
        ArrayList<Player> alSelectedPlayers = selectedPlayersList(this.tblBarM1);
        if (alSelectedPlayers.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please, select at least one player in Below Bar table", "Message",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (Player p : alSelectedPlayers){
            incrementSMMS(p);
            try {
                tournament.modifyPlayer(p, p);
            } catch (RemoteException ex) {
                Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TournamentException ex) {
                Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.tournamentChanged();

    }

    private void btn1_0ActionPerformed(java.awt.event.ActionEvent evt) {
        // get SelectedPlayers
        ArrayList<Player> alSelectedPlayers = selectedPlayersList(this.tblMMG1);
        if (alSelectedPlayers.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please, select at least one player in Bar + 1 table", "Message",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (Player p : alSelectedPlayers){
            decrementSMMS(p);
            try {
                tournament.modifyPlayer(p, p);
            } catch (RemoteException ex) {
                Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TournamentException ex) {
                Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        this.tournamentChanged();

    }

    private void btn1_2ActionPerformed(java.awt.event.ActionEvent evt) {
        // get SelectedPlayers
        ArrayList<Player> alSelectedPlayers = selectedPlayersList(this.tblMMG1);
        if (alSelectedPlayers.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please, select at least one player in Bar + 1 table", "Message",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (Player p : alSelectedPlayers){
            incrementSMMS(p);
            try {
                tournament.modifyPlayer(p, p);
            } catch (RemoteException ex) {
                Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TournamentException ex) {
                Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.tournamentChanged();

    }

    private void customInitComponents()throws RemoteException{
        initColumnHeaders(this.tblMMG0);
        initColumnHeaders(this.tblMMG1);
        initColumnHeaders(this.tblMMG2);
        initColumnHeaders(this.tblBarM1);
        updateAllViews();
        // updateComponents();
    }

    private void initColumnHeaders(JTable table){
        JFrGotha.formatColumn(table, NAME_COL, "Last name", 100, JLabel.LEFT, JLabel.LEFT);
        JFrGotha.formatColumn(table, FIRSTNAME_COL, "First name", 60, JLabel.LEFT, JLabel.LEFT);
        JFrGotha.formatColumn(table, RANK_COL, "Rk", 40, JLabel.RIGHT, JLabel.RIGHT);
        JFrGotha.formatColumn(table, CORR_COL, "Corr", 30, JLabel.RIGHT, JLabel.RIGHT);
        JFrGotha.formatColumn(table, RATING_COL, "Rating", 50, JLabel.RIGHT, JLabel.RIGHT);
    }

    private void updateComponents(){
        TournamentParameterSet tps = null;
        ArrayList<Player> alPlayers = null;
        try {
            tps = tournament.getTournamentParameterSet();
            alPlayers = tournament.playersList();
        } catch (RemoteException ex) {
            Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(tps.tournamentType() != TournamentParameterSet.TYPE_MCMAHON){
            cleanClose();
            return;
        }

        GeneralParameterSet gps = tps.getGeneralParameterSet();

        this.txfMMBar.setText(Player.convertIntToKD(gps.getGenMMBar()));
        ArrayList<Player> alMMG0Players = new ArrayList<Player>();
        ArrayList<Player> alMMG1Players = new ArrayList<Player>();
        ArrayList<Player> alMMG2Players = new ArrayList<Player>();
        ArrayList<Player> alOtherPlayers = new ArrayList<Player>();

        for (Player p : alPlayers){
            if (p.smms(gps) == gps.getGenMMBar() - Gotha.MIN_RANK ){
                alMMG0Players.add(p);
            }
            else if (p.smms(gps) == gps.getGenMMBar() - Gotha.MIN_RANK  + 1){
                alMMG1Players.add(p);
            }
            else if (p.smms(gps) == gps.getGenMMBar() - Gotha.MIN_RANK  + 2){
                alMMG2Players.add(p);
            }
            else if (p.smms(gps) == gps.getGenMMBar() - Gotha.MIN_RANK  - 1){
                alOtherPlayers.add(p);
            }
        }

        fillPlayersTable(alMMG0Players, this.tblMMG0);
        fillPlayersTable(alMMG1Players, this.tblMMG1);
        fillPlayersTable(alMMG2Players, this.tblMMG2);
        fillPlayersTable(alOtherPlayers, this.tblBarM1);
        this.txfNbPlayersMMG0.setText("" + alMMG0Players.size());
        this.txfNbPlayersMMG1.setText("" + alMMG1Players.size());
        this.txfNbPlayersMMG2.setText("" + alMMG2Players.size());
        this.txfNbPlayersBarM1.setText("" + alOtherPlayers.size());
    }

    private void fillPlayersTable(ArrayList<Player> alP, JTable tblP){
        DefaultTableModel model = (DefaultTableModel)tblP.getModel();
        while (model.getRowCount() > 0) model.removeRow(model.getRowCount()-1);
        ArrayList<Player> alDP = new ArrayList<Player>(alP); // Displayed players
        PlayerComparator playerComparator = new PlayerComparator(PlayerComparator.RATING_ORDER);
        Collections.sort(alDP, playerComparator);
        for(Player p : alDP){
           Vector<String> row = new Vector<String>();
            row.add(p.getName());
            row.add(p.getFirstName());
            row.add(Player.convertIntToKD(p.getRank()));
            row.add("" + p.getSmmsCorrection());
            row.add("" + p.getRating());
            model.addRow(row);
        }
    }

    private ArrayList<Player> selectedPlayersList(JTable tbl){
        ArrayList<Player> alSelectedPlayers = new ArrayList<Player>();

        // gather selected players into alSelectedPlayers
        for ( int iRow = 0; iRow < tbl.getModel().getRowCount(); iRow++){
            if (tbl.isRowSelected(iRow)){
                String name = (String)tbl.getModel().getValueAt(iRow, NAME_COL);
                String firstName = (String)tbl.getModel().getValueAt(iRow, FIRSTNAME_COL);
                Player p = null;
                try {
                    p = tournament.getPlayerByKeyString(name + firstName);
                } catch (RemoteException ex) {
                    Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
                }
                alSelectedPlayers.add(p);
            }
        }
        return alSelectedPlayers;
    }

    private void incrementSMMS(Player p){
        int smmsCorr = p.getSmmsCorrection();
        if (smmsCorr < 2) p.setSmmsCorrection(smmsCorr + 1);
        else{
            int answer = JOptionPane.showConfirmDialog(this,
                    p.fullName() + "'s rank is actually" + Player.convertIntToKD(p.getRank())
                    + "\n" + "Do you want to set it to " + Player.convertIntToKD(p.getRank() + 1),
                    "Message",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION)
                p.setRank(p.getRank() + 1);
        }
    }

    private void decrementSMMS(Player p){
        int smmsCorr = p.getSmmsCorrection();
        if (smmsCorr > -1) p.setSmmsCorrection(smmsCorr - 1);
        else{
            int answer = JOptionPane.showConfirmDialog(this,
                    p.fullName() + "'s rank is actually" + Player.convertIntToKD(p.getRank())
                    + "\n" + "Do you want to set it to " + Player.convertIntToKD(p.getRank() - 1),
                    "Message",
                    JOptionPane.QUESTION_MESSAGE);
            if (answer == JOptionPane.YES_OPTION)
                p.setRank(p.getRank() - 1);
        }
    }


    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {
        cleanClose();
    }

    private void cleanClose(){
        running = false;
        dispose();
    }

    private void btnHelpActionPerformed(java.awt.event.ActionEvent evt) {
        Gotha.displayGothaHelp("McMahon groups frame");
}

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        cleanClose();
    }


    private javax.swing.JButton btn0_1;
    private javax.swing.JButton btn0_M1;
    private javax.swing.JButton btn1_0;
    private javax.swing.JButton btn1_2;
    private javax.swing.JButton btn2_1;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnHelp;
    private javax.swing.JButton btnM1_0;
    private javax.swing.JButton btnResetOnRk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pnlMMG0;
    private javax.swing.JPanel pnlMMG1;
    private javax.swing.JPanel pnlMMG2;
    private javax.swing.JPanel pnlbarM1;
    private javax.swing.JScrollPane scpBarM1;
    private javax.swing.JScrollPane scpMMG0;
    private javax.swing.JScrollPane scpMMG1;
    private javax.swing.JScrollPane scpMMG2;
    private javax.swing.JTable tblBarM1;
    private javax.swing.JTable tblMMG0;
    private javax.swing.JTable tblMMG1;
    private javax.swing.JTable tblMMG2;
    private javax.swing.JTextField txfMMBar;
    private javax.swing.JTextField txfNbPlayersBarM1;
    private javax.swing.JTextField txfNbPlayersMMG0;
    private javax.swing.JTextField txfNbPlayersMMG1;
    private javax.swing.JTextField txfNbPlayersMMG2;

    private void tournamentChanged(){
        try {
            tournament.setLastTournamentModificationTime(tournament.getCurrentTournamentTime());
        } catch (RemoteException ex) {
            Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
        }

        updateAllViews();
    }

    private void updateAllViews(){
        try {
            if (!tournament.isOpen()) cleanClose();
            this.lastComponentsUpdateTime = tournament.getCurrentTournamentTime();
            setTitle("McMahon Groups. " + tournament.getFullName());
        } catch (RemoteException ex) {
            Logger.getLogger(JFrPlayersMMG.class.getName()).log(Level.SEVERE, null, ex);
        }

        updateComponents();
    }
}
