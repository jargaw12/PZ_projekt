package panels;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class HowToPlay extends JFrame {
    private ResourceBundle resourceBundle;
    private Locale locale;
    private JPanel cards;
    private JPanel card1;
    private JPanel card2;
    private JPanel card3;
    private JPanel card4;


    public HowToPlay(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("Resource", locale);
        setTitle(resourceBundle.getString("howToPlay"));
        this.locale=locale;
        card1 = new PanelCard(1, locale);
        card2 = new PanelCard(2, locale);
        card3 = new PanelCard(3, locale);
        card4 = new PanelCard(4, locale);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(670, 370);
        setResizable(false);
        getContentPane().setLayout(new FlowLayout());
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("icons/help.png")).getImage());
        MyCardLayout cardLayout = new MyCardLayout();
        cards = new JPanel(cardLayout);
        cards.add(card1, "1");
        cards.add(card2, "2");
        cards.add(card3, "3");
        cards.add(card4, "4");
        BasicArrowButton next = new BasicArrowButton(BasicArrowButton.EAST);
        next.setBackground(new Color(236, 197, 118));
        BasicArrowButton prev = new BasicArrowButton(BasicArrowButton.WEST);
        prev.setBackground(new Color(236, 197, 118));
        prev.setBorderPainted(false);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.next(cards);

            }
        });
        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.previous(cards);
            }
        });
        add(next, BorderLayout.EAST);
        add(prev, BorderLayout.WEST);
        add(cards, BorderLayout.CENTER);
        setVisible(true);
    }
}
