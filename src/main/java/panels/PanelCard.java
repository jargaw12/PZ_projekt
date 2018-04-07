package panels;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class PanelCard extends WoodPanel {
    private ImageIcon nameBar;
    private ImageIcon gif;
    private JTextArea jTextArea;
    private JScrollPane jScrollPane;
    private int id;
    private ResourceBundle resourceBundle;

    public PanelCard(int i, Locale locale) {
        resourceBundle = ResourceBundle.getBundle("Resource", locale);
        id = i;
        jTextArea = new JTextArea();
        setLayout(null);
        jTextArea.setForeground(new Color(236, 197, 118));
        jTextArea.setFont(new Font("TimesRoman", Font.PLAIN, 12));
        jTextArea.setColumns(10);
        jTextArea.setRows(10);
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setBorder(new LineBorder(Color.WHITE, 0));
        jScrollPane = new JScrollPane();
        jScrollPane.setBorder(new LineBorder(Color.WHITE, 0));
        jScrollPane.getViewport().setOpaque(false);
        jScrollPane.setOpaque(false);
        jTextArea.setOpaque(false);
        jTextArea.setEditable(false);
        jScrollPane.setBounds(10, 80, 220, 250);
        worker1.execute();
        worker2.execute();
        worker3.execute();
    }

    SwingWorker<Void, Void> worker1 = new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() throws Exception {
            nameBar = new ImageIcon(getClass().getClassLoader().getResource("icons/belkaL.png"));
            return null;
        }

        @Override
        protected void done() {
            repaint();
        }
    };

    SwingWorker<Void, Void> worker2 = new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() throws Exception {
            if (id < 4)
                gif=new ImageIcon(getClass().getClassLoader().getResource("icons/f" + id + ".gif"));
            else
                gif=new ImageIcon(getClass().getClassLoader().getResource("icons/f" + id + ".png"));
            return null;
        }

        @Override
        protected void done() {
            repaint();
        }
    };

    SwingWorker<Void, Void> worker3 = new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() throws Exception {
            jTextArea.setText(resourceBundle.getString("text.t" + id));
            return null;
        }

        @Override
        protected void done() {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    jScrollPane.setViewportView(jTextArea);
                    add(jScrollPane);
                    revalidate();
                    repaint();
                }
            });
        }
    };

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (nameBar != null) {
            g.drawImage(nameBar.getImage(), 0, 10, 227, 50, null);
        }
        g.setColor(new Color(90, 45, 25));
        g.setFont(new Font("TimesRoman", Font.BOLD, 25));
        g.drawString(resourceBundle.getString("howToPlay") + " " + id + "/4", 5, 45);
        if (gif != null) {
            g.drawImage(gif.getImage(), 350, 60, 250, 250, this);
        }
    }
}
