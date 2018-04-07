package panels;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WoodPanel extends JPanel {
    private ImageIcon background;

    public WoodPanel() {
        Border emptyBorder = new EmptyBorder(20, 20, 20, 20);
        this.setBorder(emptyBorder);
        worker.execute();
    }

    SwingWorker worker = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
            background = new ImageIcon(getClass().getClassLoader().getResource("icons/background2.jpg"));
            return null;
        }

        @Override
        protected void done() {
            repaint();
        }
    };

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
        }
    }
}
