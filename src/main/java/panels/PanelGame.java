package panels;


import configuration.Logger;
import events.NewRecordEvent;
import events.WinEvent;
import events.listeners.BaseListener;
import events.listeners.WinListener;
import configuration.Base;
import configuration.MyConfiguration;
import panels.game.Player;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class PanelGame extends WoodPanel implements WinListener {
    private Board board;
    Frame frame;
    Base base;
    ResourceBundle resourceBundle;
    private PanelUser panelUser1;
    private PanelUser panelUser2;
    private PanelMode panelMode;

    public PanelGame(Locale locale, Frame frame, MyConfiguration config, Player player1, Player player2) {
        setLayout(new BorderLayout());
        this.frame=frame;
        board = new Board(player1,player2,frame,config,locale);
        panelUser1 = new PanelUser(player1);
        panelUser2 = new PanelUser(player2);
//        board.addListener(this);
        panelMode =new PanelMode(locale);
        board.setBoardListener(panelMode);
        Container c=new Container();
        c.setLayout(new GridLayout(2,1));
        c.add(panelUser1);
        c.add(panelUser2);
        add(c, BorderLayout.EAST);
        add(panelMode,BorderLayout.SOUTH);
        add(board, BorderLayout.CENTER);
        base = new Base();
    }

    public Board getBoard() {
        return board;
    }

    public PanelUser getPanelUser1() {
        return panelUser1;
    }

    public PanelUser getPanelUser2() {
        return panelUser2;
    }

    public PanelMode getPanelMode() {
        return panelMode;
    }

    public void setPanelMode(PanelMode panelMode) {
        this.panelMode = panelMode;
    }

    public void changePanel(){
        frame.getContentPane().removeAll();
        PanelScore panelScore = new PanelScore(Locale.getDefault(), frame);
        frame.addLanguageListener(panelScore);
        frame.getContentPane().add(panelScore);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }


    @Override
    public void win(WinEvent winEvent) {
        resourceBundle=ResourceBundle.getBundle("Resource",Locale.getDefault());
        base.addBaseListener(new BaseListener() {
            @Override
            public void celebrateNewRecord(NewRecordEvent newRecord) {
                JOptionPane.showMessageDialog(null, resourceBundle.getString("newRecord"), resourceBundle.getString("record"), JOptionPane.OK_OPTION, new ImageIcon(winEvent.getWinner().getUser()));
            }
        });
        SwingWorker<Void, Void> baseSwingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    base.polacz();
                    base.wstawienieWyniku(winEvent.getWinner().getName(), winEvent.getWinner().getScore());
                } catch (SQLException e1) {
                    Logger.getInstance().logger.log(Level.WARNING, "Błąd połączenia z bazą");
                    e1.printStackTrace();
                } finally {
                    base.rozlacz();
                }
                return null;
            }

            @Override
            protected void done() {
                changePanel();
            }
        };
        int selection = JOptionPane.showConfirmDialog(null, resourceBundle.getString("winner") + " " + winEvent.getWinner().getName() + "\n" + resourceBundle.getString("message.save"), resourceBundle.getString("winner"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (selection == JOptionPane.YES_OPTION) {
            baseSwingWorker.execute();
        }
        else changePanel();
    }
}
