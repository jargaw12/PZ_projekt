package panels;

import events.ChangeLanguageEvent;
import events.ChangeModeEvent;
import events.listeners.BoardListener;
import events.listeners.FrameListener;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class PanelMode extends JComponent implements BoardListener, FrameListener {
    private String textMode;
    private Font font;
    private String mode;
    private ResourceBundle resourceBundle;

    public PanelMode(Locale locale) {
        setPreferredSize(new Dimension(490,50));
        resourceBundle = ResourceBundle.getBundle("Resource", locale);
        font=new Font("TimesRoman", Font.PLAIN, 15);
        mode ="place";
        textMode =resourceBundle.getString("text.place");
    }
    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(font);
        g.setColor(new Color(236, 197, 118));
        g.drawString(textMode,0,30);
    }

    @Override
    public void zmienTryb(ChangeModeEvent zmianaTrybu) {
        mode =zmianaTrybu.getMode();
        textMode =resourceBundle.getString("text."+ mode);
        repaint();
    }

    @Override
    public void changeLanguage(ChangeLanguageEvent changeLanguageEvent) {
        resourceBundle = ResourceBundle.getBundle("Resource", changeLanguageEvent.getLocale());
        textMode =resourceBundle.getString("text."+ mode);
        repaint();
    }
}
