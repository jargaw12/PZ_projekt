package panels;

import panels.game.Player;
import events.listeners.WinListener;
import events.WinEvent;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PanelUser extends JComponent implements PropertyChangeListener {
    private Player player;
    private Font font;
    private Color color;
    private ImageIcon nameBar;
    private ImageIcon youIcon;
    private ImageIcon winIcon;
    private ImageIcon user;


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (nameBar != null) {
            g.drawImage(nameBar.getImage(), 10, 0, 227, 50, null);
        }
        if (player.isActive() && youIcon != null) {
            g.drawImage(youIcon.getImage(), 225, 0, 25, 50, null);
        }
        g.setColor(color);
        g.setFont(font);
        g.drawString(player.getName(), 15, 35);
        g.setColor(player.getMyColor().getDark());
        if (user != null) {
            g.drawImage(user.getImage(), 75, 75, null);
        }
        if (player.isWin()) {
            g.drawImage(winIcon.getImage(), 75, 75, null);
        }
        for (int i = 0; i < player.getPieceToPlace(); i++) {
            g.fillRect(180, 160 - i * 4, 15, 3);
        }

    }

    public PanelUser(Player player) {
        this.player = player;
        player.addPropertyChangeListener(this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 245));
        font = new Font("TimesRoman", Font.BOLD, 25);
        color = new Color(90, 45, 25);
        worker1.execute();
        worker2.execute();
        worker3.execute();
        worker4.execute();
    }

    SwingWorker worker1 = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
            nameBar = new ImageIcon(getClass().getClassLoader().getResource("icons/belkaL.png"));
            return null;
        }

        @Override
        protected void done() {
            repaint();
        }
    };

    SwingWorker worker2 = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
            youIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/belkaP.png"));
            return null;
        }

        @Override
        protected void done() {
            repaint();
        }
    };

    SwingWorker worker3 = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
            winIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/winner_100.png"));
            return null;
        }

        @Override
        protected void done() {
            repaint();
        }
    };

    SwingWorker worker4 = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
            user = new ImageIcon(getClass().getClassLoader().getResource(player.getUser()));
            return null;
        }

        @Override
        protected void done() {
            repaint();
        }
    };

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        repaint();
    }

}
