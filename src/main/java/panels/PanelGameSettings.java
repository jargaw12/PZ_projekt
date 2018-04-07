package panels;

import events.ChangeLanguageEvent;
import events.listeners.FrameListener;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class PanelGameSettings extends Container implements FrameListener {
    private ButtonGroup group;
    JRadioButton radioButton1;
    JRadioButton radioButton2;
    JRadioButton radioButton3;
    JLabel label;
    ResourceBundle resourceBundle;
    private GridBagConstraints c;

    public PanelGameSettings(Locale locale) {
        resourceBundle=ResourceBundle.getBundle("Resource",locale);
        c = new GridBagConstraints();
        c.weightx=0.5;
        c.gridy=0;
        c.gridx=0;
        c.gridwidth=2;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.anchor=GridBagConstraints.CENTER;
        setLayout(new GridBagLayout());
        //setBackground(Color.GRAY);
        label=new JLabel(resourceBundle.getString("settings.game"));
        add(label,c);
        group = new ButtonGroup();
        radioButton1 = new JRadioButton("5", false);
        group.add(radioButton1);
        radioButton2 = new JRadioButton("9", true);
        group.add(radioButton2);
        c.anchor=GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 2;
        add(radioButton1,c);
        c.gridy = 3;
        add(radioButton2,c);
        radioButton3 = new JRadioButton("11", false);
        group.add(radioButton3);
        c.gridy = 4;
        add(radioButton3,c);

    }

    @Override
    public void changeLanguage(ChangeLanguageEvent changeLanguageEvent) {
        resourceBundle = ResourceBundle.getBundle("Resource", changeLanguageEvent.getLocale());
        label.setText(resourceBundle.getString("settings.game"));
    }
}
