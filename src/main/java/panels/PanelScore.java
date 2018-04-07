package panels;

import events.ChangeLanguageEvent;
import events.listeners.FrameListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class PanelScore extends WoodPanel implements FrameListener {
    private Locale locale;
    private GridBagConstraints c;
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("Resource");
    private Frame frame;
    private JLabel name;
    private Table tt;
    private JScrollPane scrollPane;

    public PanelScore(Locale locale, Frame frame) {
        this.locale = locale;
        c = new GridBagConstraints();
        setLayout(new GridBagLayout());
        this.frame=frame;
        resourceBundle = ResourceBundle.getBundle("Resource", locale);
        name = new JLabel(resourceBundle.getString("scores"));
        name.setForeground(new Color(236, 197, 118));
        tt = new Table(locale);
        scrollPane = new JScrollPane(tt.table);
        tt.table.setPreferredScrollableViewportSize(new Dimension(600, 300));
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                tt.read();
                return null;
            }
        };
        worker.execute();
        c.gridx = 0;
        c.gridy = 0;
        add(name, c);
        c.gridx = 0;
        c.gridy = 1;
        add(scrollPane, c);
        c.gridx = 0;
        c.gridy = 2;
        add(new JButton(newGameAction), c);
    }

    public AbstractAction newGameAction = new AbstractAction(resourceBundle.getString("newGame")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            PanelStart panelStart=new PanelStart(locale,frame);
            frame.addLanguageListener(panelStart);
            frame.getContentPane().removeAll();
            frame.getContentPane().add(panelStart);
            frame.revalidate();
        }
    };

    private void refresh(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("Resource", locale);
        name.setText(resourceBundle.getString("scores"));
        newGameAction.putValue(Action.NAME, resourceBundle.getString("newGame"));
    }

    @Override
    public void changeLanguage(ChangeLanguageEvent changeLanguageEvent) {
        locale=changeLanguageEvent.getLocale();
        refresh(changeLanguageEvent.getLocale());
        tt.refresh(changeLanguageEvent.getLocale());
    }
}

