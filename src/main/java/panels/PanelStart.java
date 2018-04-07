package panels;

import configuration.Logger;
import events.ChangeLanguageEvent;
import events.WeatherEvent;
import events.listeners.WeatherListener;
import exceptions.EqualsColorException;
import configuration.MyConfiguration;
import panels.myColor.MyColor;
import panels.game.Player;
import events.listeners.FrameListener;
import weatherModel.WeatherModel;
import weatherModel.WebClient;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;


public class PanelStart extends WoodPanel implements FrameListener {
    private Locale locale = Locale.getDefault();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("Resource", locale);
    private Frame frame;
    private Image image;
    private JButton button1;
    private JButton button2;
    private JLabel lTemp;
    private JLabel lDescription;
    private JLabel lWind;
    private JLabel lPressure;
    private JLabel lRain;
    private JButton search;
    private JTextField city;
    private JLabel pogoda;
    private MyConfiguration myConfiguration;
    private Color fontColor;
    private PanelNewGame panelNewGame;
    private PanelGameSettings panelGameSettings;
    private GridBagConstraints c;
    WebClient webClient;
    private WeatherModel weatherModel = null;
    private MyConfiguration configuration;

    public PanelStart(Locale locale, Frame frame) {
        this.locale=locale;
        this.frame = frame;
        myConfiguration = new MyConfiguration();
        fontColor = Color.decode(myConfiguration.getValue("fontColor"));
        panelNewGame = new PanelNewGame(frame, locale);
        panelGameSettings = new PanelGameSettings(locale);
        frame.addLanguageListener(panelGameSettings);
        UIManager.put("Label.foreground", fontColor);
        webClient = new WebClient();
        initGUI();
        worker.execute();
        workerconf.execute();
        webClient.addWeatherListener(new WeatherListener() {
            @Override
            public void severeWeather(WeatherEvent weather) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JOptionPane.showMessageDialog(null, resourceBundle.getString("weather." + weather.getWarning()), resourceBundle.getString("weather.warning"), JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getClassLoader().getResource("icons/" + weather.getWarning() + ".png")));
                    }
                });
            }
        });
    }

    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() {
            try {
                weatherModel = webClient.getWeatherModel(myConfiguration.getValue("city"), locale.getLanguage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void done() {
            refreshGui();
        }
    };

    SwingWorker<Void, Void> workerconf = new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() throws Exception {
            configuration = new MyConfiguration();
            return null;
        }
    };

    public AbstractAction newGameAction = new AbstractAction(resourceBundle.getString("settings.player")) {
        @Override
        public void actionPerformed(ActionEvent e) {
//           con.setVisible(false);
            c.gridx = 0;
            c.gridy = 5;
            c.gridwidth = 9;
            c.weightx = 1;
            c.weighty = 1;
            c.fill = GridBagConstraints.BOTH;
            panelGameSettings.setVisible(false);
            panelNewGame.setVisible(true);
            add(panelNewGame, c);
            revalidate();
        }
    };

    public AbstractAction playAction = new AbstractAction(resourceBundle.getString("newGame")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            Player player1 = new Player((MyColor) panelNewGame.colorCombo1.getSelectedItem(), panelNewGame.nameField1.getText(), panelNewGame.mButton1.isSelected() ? 'm' : 'f');
            Player player2 = new Player((MyColor) panelNewGame.colorCombo2.getSelectedItem(), panelNewGame.nameField2.getText(), panelNewGame.mButton2.isSelected() ? 'm' : 'f');
            try {
                startGame(player1, player2);
            } catch (EqualsColorException e1) {
                Logger.getInstance().logger.log(Level.WARNING, "Błąd spowodowany wybraniem identycznych kolorów", e1);
                JOptionPane.showMessageDialog(null, resourceBundle.getString("message.color"));
            }
            frame.pack();
            frame.setLocationRelativeTo(null);
        }
    };

    AbstractAction settingsAction = new AbstractAction(resourceBundle.getString("settings.game")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            c.gridx = 0;
            c.gridy = 5;
            c.gridwidth = 9;
            c.weightx = 1;
            c.weighty = 1;
            c.fill = GridBagConstraints.BOTH;
            panelGameSettings.setVisible(true);
            panelNewGame.setVisible(false);
            add(panelGameSettings, c);
            revalidate();
        }
    };

    AbstractAction searchAction = new AbstractAction("", new ImageIcon(getClass().getClassLoader().getResource("icons/search.png"))) {
        @Override
        public void actionPerformed(ActionEvent e) {
            myConfiguration.setValue("city", city.getText());
            try {
                weatherModel = webClient.getWeatherModel(myConfiguration.getValue("city"), locale.getLanguage());
                refreshGui();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    };

    private void initGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                c = new GridBagConstraints();
                setLayout(new GridBagLayout());

                c.fill = GridBagConstraints.BOTH;
                c.anchor = GridBagConstraints.FIRST_LINE_START;
                c.weightx = 0;
                c.weighty = 0;
                button1 = new JButton(newGameAction);
                c.gridx = 0;
                c.gridy = 0;
                c.gridheight = 4;
                add(button1, c);

                button2 = new JButton(settingsAction);
                c.gridx = 1;
                c.gridy = 0;
                c.gridheight = 4;
                add(button2, c);

                c.insets = new Insets(10, 0, 10, 10);
                c.gridx = 2;
                c.gridy = 0;
                c.weightx = 1;
                c.gridheight = 3;
                add(new Container(), c);

                c.gridx = 6;
                c.gridy = 1;
                c.weightx = 0;
                c.gridheight = 1;
                add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("icons/wiatr.png"))), c);

                c.gridx = 6;
                c.gridy = 2;
                add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("icons/cisnienie.png"))), c);

                c.gridx = 6;
                c.gridy = 3;
                add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("icons/deszcz.png"))), c);

                pogoda = new JLabel();
                c.gridx = 4;
                c.gridy = 1;
                c.gridheight = 2;
                c.gridwidth = 2;
                add(pogoda, c);

                c.gridheight = 2;
                c.gridwidth = 1;
                c.gridx = 3;
                c.gridy = 1;
                lTemp = new JLabel();
                add(lTemp, c);

                c.gridheight = 1;
                c.gridx = 7;
                c.gridy = 1;
                lWind = new JLabel();
                add(lWind, c);

                c.gridx = 7;
                c.gridy = 2;
                lPressure = new JLabel();
                add(lPressure, c);

                c.gridx = 7;
                c.gridy = 3;
                lRain = new JLabel();
                add(lRain, c);

                c.gridx = 3;
                c.gridy = 3;
                c.gridwidth = 3;
                lDescription = new JLabel();
                add(lDescription, c);

                c.fill = GridBagConstraints.BOTH;
                c.gridx = 3;
                c.gridy = 0;
                c.gridwidth = 3;
                city = new JTextField();
                add(city, c);

                search = new JButton(searchAction);
                c.fill = GridBagConstraints.NONE;
                c.gridwidth = 1;
                c.weightx = 0;
                c.gridx = 6;
                c.gridy = 0;
                add(search, c);


                JButton play = new JButton(playAction);
                c.gridx = 6;
                c.gridy = 7;
                c.gridwidth = 2;
                c.weightx = 1;
                c.weighty = 1;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.anchor = GridBagConstraints.SOUTH;
                add(play, c);
            }
        });
    }

    private void startGame(Player p1, Player p2) throws EqualsColorException {
        if (p1.getMyColor().equals(p2.getMyColor())) throw new EqualsColorException();
        PanelGame panelGame = new PanelGame(locale, frame, configuration, p1, p2);
        if (panelGameSettings.radioButton1.isSelected()) {
            panelGame.getBoard().setCountOfPieces(Integer.parseInt(panelGameSettings.radioButton1.getText()));
        } else if (panelGameSettings.radioButton2.isSelected()) {
            panelGame.getBoard().setCountOfPieces(Integer.parseInt(panelGameSettings.radioButton2.getText()));
        } else {
            panelGame.getBoard().setCountOfPieces(Integer.parseInt(panelGameSettings.radioButton3.getText()));
        }
        panelGame.getBoard().initGame();
        panelGame.getBoard().addListener(panelGame);
        frame.addLanguageListener(panelGame.getPanelMode());
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panelGame);
    }

    private void refresh(Locale locale) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                resourceBundle = ResourceBundle.getBundle("Resource", locale);
                newGameAction.putValue(Action.NAME, resourceBundle.getString("settings.player"));
                settingsAction.putValue(Action.NAME, resourceBundle.getString("settings.game"));
                playAction.putValue(Action.NAME, resourceBundle.getString("newGame"));
                try {
                    weatherModel = webClient.getWeatherModel(myConfiguration.getValue("city"), locale.getLanguage());
                    refreshGui();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        }

    public void refreshGui() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String adres = weatherModel.getCurrent().getCondition().getIcon();
                URL url = null;
                try {
                    url = new URL("https:" + adres);
                    image = ImageIO.read(url);
                } catch (MalformedURLException e) {
                    Logger.getInstance().logger.log(Level.WARNING, "Błąd pobierania ikony pogody", e);
                } catch (IOException e) {
                    Logger.getInstance().logger.log(Level.WARNING, "Błąd pobierania ikony pogody", e);
                }

                pogoda.setIcon(new ImageIcon(image));
                lTemp.setText(String.valueOf(weatherModel.getCurrent().temp_c) + "\u00B0" + " C");
                lWind.setText(String.valueOf(weatherModel.getCurrent().wind_kph) + " km/h");
                lPressure.setText(String.valueOf(weatherModel.getCurrent().pressure_mb) + " hPa");
                lRain.setText(String.valueOf(weatherModel.getCurrent().precip_mm) + " mm");
                city.setText(weatherModel.getLocation().name);
                lDescription.setText(String.valueOf(weatherModel.getCurrent().getCondition().getText()));
                revalidate();
                repaint();
            }
        });
    }

    @Override
    public void changeLanguage(ChangeLanguageEvent changeLanguageEvent) {
        locale = changeLanguageEvent.getLocale();
        refresh(changeLanguageEvent.getLocale());
    }
}
