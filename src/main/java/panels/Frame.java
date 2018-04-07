package panels;

import configuration.Logger;
import events.ChangeLanguageEvent;
import events.listeners.FrameListener;
import configuration.MyConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;
import java.util.logging.Level;

public class Frame extends JFrame{
    private Locale locale = new Locale("pl", "PL");
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("Resource", locale);
    private List<FrameListener> myListeners = new ArrayList<>();
    MyConfiguration configuration;
    private Frame frame;
    private FrameListener component;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu languageMenu;
    private JMenu wygladMenu;
    private JMenu helpMenu;

    public Frame(){
        configuration=new MyConfiguration();
        setMinimumSize(new Dimension(Integer.parseInt(configuration.getValue("width")),Integer.parseInt(configuration.getValue("height"))));
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("icons/ikona.png")).getImage());
        component= new PanelStart(locale, this);
         frame=this;
        this.addLanguageListener(component);
        getContentPane().add((JComponent)component);
        dodajMenu();
        pack();
        //setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Logger.getInstance().logger.log(Level.WARNING,"Błąd przy próbie wczytania systemowego wyglądu",e);
        } catch (InstantiationException e) {
            Logger.getInstance().logger.log(Level.WARNING,"Błąd przy próbie wczytania systemowego wyglądu",e);
        } catch (IllegalAccessException e) {
            Logger.getInstance().logger.log(Level.WARNING,"Błąd przy próbie wczytania systemowego wyglądu",e);
        } catch (UnsupportedLookAndFeelException e) {
            Logger.getInstance().logger.log(Level.WARNING,"Błąd przy próbie wczytania systemowego wyglądu",e);
        }
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int selection = JOptionPane.showConfirmDialog(null, resourceBundle.getString("message.wantexit"), resourceBundle.getString("exit"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (selection == JOptionPane.YES_OPTION) {
                    configuration.removeKey("city");
                    System.exit(NORMAL);
                }
            }
        });
    }

    private void dodajMenu() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu(resourceBundle.getString("file"));
        fileMenu.add(new JMenuItem(newGameAction));
//        fileMenu.add(new JMenuItem(statisticsAction));
        fileMenu.add(new JMenuItem(scoresAction));
        fileMenu.add(new JMenuItem(exitAction));

        editMenu = new JMenu(resourceBundle.getString("edit"));
        languageMenu = new JMenu(resourceBundle.getString("language"));
        languageMenu.add(new JMenuItem(language1Action));
        languageMenu.add(new JMenuItem(language2Action));
        editMenu.add(languageMenu);

        wygladMenu = new JMenu(resourceBundle.getString("look"));
        wygladMenu.add(new JMenuItem(look1Action));
        wygladMenu.add(new JMenuItem(look2Action));
        wygladMenu.add(new JMenuItem(look3Action));
        editMenu.add(wygladMenu);

        helpMenu = new JMenu(resourceBundle.getString("help"));
        helpMenu.add(new JMenuItem(howToPlayAction));

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
        refresh(locale);
    }

    Action exitAction = new AbstractAction() {
        public void actionPerformed(ActionEvent event) {
            int selection = JOptionPane.showConfirmDialog(null, resourceBundle.getString("message.wantexit")+" "+resourceBundle.getString("gameName"), resourceBundle.getString("exit"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (selection == JOptionPane.YES_OPTION) {
                configuration.removeKey("city");
                System.exit(NORMAL);
            }
        }
    };

    Action look1Action = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e1) {
                Logger.getInstance().logger.log(Level.WARNING,"Błąd przy próbie wczytania MetalLookAndFeel",e1);
            }

            SwingUtilities.updateComponentTreeUI(getContentPane());
            //SwingUtilities.updateComponentTreeUI(component);
            SwingUtilities.updateComponentTreeUI(menuBar);
        }
    };

    Action look2Action = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e1) {
                Logger.getInstance().logger.log(Level.WARNING,"Błąd przy próbie wczytania WindowsLookAndFeel",e1);
            }
            SwingUtilities.updateComponentTreeUI(getContentPane());
            //SwingUtilities.updateComponentTreeUI(component);
            SwingUtilities.updateComponentTreeUI(menuBar);
        }
    };

    Action look3Action = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e1) {
                Logger.getInstance().logger.log(Level.WARNING,"Błąd przy próbie wczytania NimbusLookAndFeel",e1);
            }
            SwingUtilities.updateComponentTreeUI(getContentPane());
            SwingUtilities.updateComponentTreeUI(menuBar);
        }
    };

    Action language1Action = new AbstractAction(resourceBundle.getString("language.english"), new ImageIcon("src\\main\\resources\\icons\\flag_en2.png")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            locale = Locale.ENGLISH;
            Locale.setDefault(locale);
            fireLanguageEvent();
            refresh(locale);
        }
    };

    Action language2Action = new AbstractAction(resourceBundle.getString("language.polish"), new ImageIcon("src\\main\\resources\\icons\\flag_pl2.png")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            locale = new Locale("pl", "PL");
            Locale.setDefault(locale);
            fireLanguageEvent();
            refresh(locale);
        }
    };

    Action newGameAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (getContentPane().getComponent(0) instanceof PanelGame){
                ((PanelGame)getContentPane().getComponent(0)).getBoard().initGame();
                ((PanelGame)getContentPane().getComponent(0)).repaint();
                ((PanelGame)getContentPane().getComponent(0)).revalidate();
                ((PanelGame)getContentPane().getComponent(0)).getPanelUser1().repaint();
                ((PanelGame)getContentPane().getComponent(0)).getPanelUser2().repaint();
                ((PanelGame)getContentPane().getComponent(0)).getPanelMode().revalidate();

            }
            else if (getContentPane().getComponent(0) instanceof PanelStart){
                ((PanelStart)getContentPane().getComponent(0)).playAction.actionPerformed(e);
            }
            else if (getContentPane().getComponent(0) instanceof PanelScore){
                ((PanelScore)getContentPane().getComponent(0)).newGameAction.actionPerformed(e);
            }
        }
    };

    Action scoresAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            getContentPane().removeAll();
            PanelScore panelScore=new PanelScore(locale,frame);
            getContentPane().add(panelScore);
            getContentPane().getComponent(0);
            frame.addLanguageListener(panelScore);
            revalidate();
            pack();
            setLocationRelativeTo(null);
        }
    };

    Action howToPlayAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            new HowToPlay(locale);
        }
    };

    private void refresh(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("Resource", locale);
        setTitle(resourceBundle.getString("gameName"));
        fileMenu.setText(resourceBundle.getString("file"));
        newGameAction.putValue(Action.NAME, resourceBundle.getString("newGame"));
        scoresAction.putValue(Action.NAME, resourceBundle.getString("scores"));
        exitAction.putValue(Action.NAME, resourceBundle.getString("exit"));
        editMenu.setText(resourceBundle.getString("edit"));
        languageMenu.setText(resourceBundle.getString("language"));
        language1Action.putValue(Action.NAME, resourceBundle.getString("language.english"));
        language2Action.putValue(Action.NAME, resourceBundle.getString("language.polish"));
        wygladMenu.setText(resourceBundle.getString("look"));
        look1Action.putValue(Action.NAME, resourceBundle.getString("look.1"));
        look2Action.putValue(Action.NAME, resourceBundle.getString("look.2"));
        look3Action.putValue(Action.NAME, resourceBundle.getString("look.3"));
        helpMenu.setText(resourceBundle.getString("help"));
        howToPlayAction.putValue(Action.NAME, resourceBundle.getString("howToPlay"));
    }

    public synchronized void addLanguageListener(FrameListener l) {
        myListeners.add(l);
    }

    public synchronized void removeLanguageListener(FrameListener l) {
        myListeners.remove(l);
    }

    private synchronized void fireLanguageEvent() {
        ChangeLanguageEvent languageEvent = new ChangeLanguageEvent(this, locale);
        Iterator listeners = myListeners.iterator();
        while (listeners.hasNext()) {
            ((FrameListener) listeners.next()).changeLanguage(languageEvent);
        }
    }
}
