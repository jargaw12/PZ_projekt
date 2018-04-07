package panels;

import events.ChangeLanguageEvent;
import events.listeners.FrameListener;
import panels.myColor.ColorComboBoxRenderer;
import panels.myColor.MyColor;
import panels.myColor.MyColors;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class PanelNewGame extends Container implements FrameListener {
    private Frame frame;
    private Locale locale;
    private ButtonGroup group1;
    private JLabel name;
    JRadioButton mButton1;
    private JRadioButton fButton1;
    JTextField nameField1;
    private MyColors myColors;
    JComboBox<MyColor> colorCombo1;
    private ButtonGroup group2;
    JRadioButton mButton2;
    private JRadioButton fButton2;
    JTextField nameField2;
    JComboBox<MyColor> colorCombo2;
    private GridBagConstraints c;
    private ResourceBundle resourceBundle;


    public PanelNewGame(Frame frame, Locale locale) {
        c = new GridBagConstraints();
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        setLayout(new GridBagLayout());
        this.locale = locale;
        resourceBundle = ResourceBundle.getBundle("Resource", locale);
        this.frame = frame;
        frame.addLanguageListener(this);
        player1Settings();
        player2Settings();
        worker.execute();
    }

    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() throws Exception {
            myColors = new MyColors();
            return null;
        }

        @Override
        protected void done() {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    for (MyColor m : myColors.getMyColors()) {
                        colorCombo1.addItem(m);
                        colorCombo2.addItem(m);
                    }
                    name = new JLabel(resourceBundle.getString("settings.player"));
                    c.gridx = 0;
                    c.gridy = 0;
                    add(name, c);
                    c.gridy = 5;
                    add(colorCombo1, c);
                    c.gridx = 1;
                    c.gridy = 5;
                    colorCombo2.setSelectedIndex(1);
                    add(colorCombo2, c);
                    frame.pack();
                }
            });
        }
    };

    private void refresh(Locale locale) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                resourceBundle = ResourceBundle.getBundle("Resource", locale);
                mButton1.setText(resourceBundle.getString("sex.male"));
                fButton1.setText(resourceBundle.getString("sex.female"));
                nameField1.setText(resourceBundle.getString("player") + " 1");
                colorCombo1.setRenderer(new ColorComboBoxRenderer(locale));
                mButton2.setText(resourceBundle.getString("sex.male"));
                fButton2.setText(resourceBundle.getString("sex.female"));
                nameField2.setText(resourceBundle.getString("player") + " 2");
                name.setText(resourceBundle.getString("settings.player"));
                colorCombo2.setRenderer(new ColorComboBoxRenderer(locale));
            }
        });
    }

    @Override
    public void changeLanguage(ChangeLanguageEvent changeLanguageEvent) {
        refresh(changeLanguageEvent.getLocale());
    }

    private void player1Settings() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                nameField1 = new JTextField(resourceBundle.getString("player") + " 1", 30);
                c.gridx = 0;
                c.gridy = 2;
                add(nameField1, c);
                group1 = new ButtonGroup();
                mButton1 = new JRadioButton(resourceBundle.getString("sex.male"), false);
                group1.add(mButton1);
                fButton1 = new JRadioButton(resourceBundle.getString("sex.female"), true);
                group1.add(fButton1);
                c.gridy = 3;
                add(fButton1, c);
                c.gridy = 4;
                add(mButton1, c);
                colorCombo1 = new JComboBox<>();
                colorCombo1.setRenderer(new ColorComboBoxRenderer(locale));
            }
        });
    }

    private void player2Settings() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                nameField2 = new JTextField(resourceBundle.getString("player") + " 2", 30);
                c.gridx = 1;
                c.gridy = 2;
                add(nameField2, c);
                group2 = new ButtonGroup();
                mButton2 = new JRadioButton(resourceBundle.getString("sex.male"), false);
                group2.add(mButton2);
                fButton2 = new JRadioButton(resourceBundle.getString("sex.female"), true);
                group2.add(fButton2);
                c.gridy = 3;
                add(fButton2, c);
                c.gridy = 4;
                add(mButton2, c);
                colorCombo2 = new JComboBox<>();
                colorCombo2.setRenderer(new ColorComboBoxRenderer(locale));
            }
        });
    }
}