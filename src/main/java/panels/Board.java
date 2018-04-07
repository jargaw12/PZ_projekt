package panels;

import events.ChangeLanguageEvent;
import events.ChangeModeEvent;
import events.WinEvent;
import events.listeners.BoardListener;
import events.listeners.FrameListener;
import events.listeners.WinListener;
import configuration.MyConfiguration;
import panels.game.Field;
import panels.game.Piece;
import panels.game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

public class Board extends JComponent implements FrameListener {
    private MyConfiguration configuration;
    private ArrayList<Field> fields = new ArrayList<>();
    ResourceBundle resourceBundle;
    private Piece activePiece;
    private Player player1;
    private Player player2;
    private Player activePlayer;
    private Player inactivePlayer;
    private boolean toRemove = false;
    private int countOfPieces;
    private BoardListener boardListener;
    private ImageIcon background;
    private Locale locale;
    private ImageIcon gif;
    private Frame frame;
    private ArrayList myLiteners;
    private boolean p[][] =
            {
                    {true, false, false, true, false, false, true},
                    {false, true, false, true, false, true, false},
                    {false, false, true, true, true, false, false},
                    {true, true, true, false, true, true, true},
                    {false, false, true, true, true, false, false},
                    {false, true, false, true, false, true, false},
                    {true, false, false, true, false, false, true}
            };

    public Board(Player player1, Player player2, Frame frame, MyConfiguration config, Locale locale) {
        this.player1 = player1;
        this.player2 = player2;
        this.locale = locale;
        resourceBundle = ResourceBundle.getBundle("Resource", locale);
        configuration = config;
        setPreferredSize(new Dimension(Integer.parseInt(configuration.getValue("boardwidth")), Integer.parseInt(configuration.getValue("boardheight"))));
        createFields();
        frame.addLanguageListener(this);
        addMouseListener(new MouseHandler());
        this.frame = frame;
        worker.execute();
        myLiteners = new ArrayList();
        gif = new ImageIcon(getClass().getClassLoader().getResource("icons/j1.gif"));
    }

    SwingWorker worker = new SwingWorker() {
        @Override
        protected Object doInBackground() throws Exception {
            background = new ImageIcon(getClass().getClassLoader().getResource("icons/board.jpg"));
            return null;
        }

        @Override
        protected void done() {
            repaint();
        }
    };

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (background != null) {
            g.drawImage(background.getImage(), 0, 0, Integer.parseInt(configuration.getValue("boardwidth")), Integer.parseInt(configuration.getValue("boardheight")), null);
        }
        for (Piece r : player1.getPieces()) {
            if (r != null) {
                g.setColor(r.getMyColor());
                g.fillOval(r.x, r.y, r.width, r.height);
            }
        }

        for (Piece r : player2.getPieces()) {
            if (r != null) {
                g.setColor(r.getMyColor());
                g.fillOval(r.x, r.y, r.width, r.height);
            }
        }

        if (activePiece != null) {
            g.setColor(activePlayer.getMyColor().getBright());
            g.fillOval(activePiece.x, activePiece.y, activePiece.width, activePiece.height);
            if (activePlayer.getPieces().size() > 3) {
                for (Field p : fields) {
                    if (p.getPiece() == null && isAdjacent(findField(activePiece.getLocation()), p)) {
                        g.drawImage(gif.getImage(), p.x - 5, p.y - 5, p.width + 10, p.height + 10, this);
                    }
                }
            }
        }
    }

    private Field findField(Point p) {
        for (Field r : fields) {
            if (r.contains(p))
                return r;
        }
        return null;
    }

    private int getLayer(Field field) {
        return fields.indexOf(field) / 8;
    }

    private int getPositionInLayer(Field field) {
        return fields.indexOf(field) % 8;
    }

    private boolean isAdjacent(Field field1, Field field2) {
        int w1 = getLayer(field1);
        int p1 = getPositionInLayer(field1);
        int w2 = getLayer(field2);
        int p2 = getPositionInLayer(field2);
        if (w1 == w2) {
            if (p2 == (Math.floorMod((p1 - 1), 8)) || p2 == (Math.floorMod((p1 + 1), 8))) return true;
        } else if (p1 % 2 == 1 && p1 == p2 && (Math.abs(w1 - w2) == 1)) return true;
        return false;
    }

    private void selectPiece(Field field) {
        activePiece = activePlayer.findPiece(field.getLocation());
        if (activePiece != null) {
            if (activePlayer.getPieces().size() > 3) fireZmienTryb("emptyadjacent");
            else fireZmienTryb("empty");
        }
        repaint();
    }

    private void movePiece(Field field) {
        if (field.getPiece() == null) {
            if ((activePlayer.getPieces().size() <= 3
                    || activePlayer.getPieces().size() > 3 && isAdjacent(findField(activePiece.getLocation()), field))) {
                findField(activePiece.getLocation()).setPiece(null);
                activePiece.setLocation(field.getLocation());
                field.setPiece(activePiece);
                checkMill(field);
                if (!toRemove) changePlayer();
            }
        }
        repaint();
    }


    private void checkMill(Field p) {
        int i = fields.indexOf(p) / 8;
        int j = fields.indexOf(p) % 8;
        if (j % 2 == 0) {
            if (fields.get(i * 8 + Math.floorMod(j + 1, 8)).getPiece() != null && activePlayer.getPieces().contains(fields.get(i * 8 + Math.floorMod(j + 1, 8)).getPiece())) {
                if (fields.get(i * 8 + Math.floorMod(j + 2, 8)).getPiece() != null && activePlayer.getPieces().contains(fields.get(i * 8 + Math.floorMod(j + 2, 8)).getPiece())) {
                    p.getPiece().setMyColor(activePlayer.getMyColor().getBright());
                    fields.get(i * 8 + Math.floorMod(j + 1, 8)).getPiece().setMyColor(activePlayer.getMyColor().getBright());
                    fields.get(i * 8 + Math.floorMod(j + 2, 8)).getPiece().setMyColor(activePlayer.getMyColor().getBright());
                    fireZmienTryb("mill");
                    toRemove = true;
                }
            } else if (fields.get(i * 8 + Math.floorMod(j - 1, 8)).getPiece() != null && activePlayer.getPieces().contains(fields.get(i * 8 + Math.floorMod(j - 1, 8)).getPiece())) {
                if (fields.get(i * 8 + Math.floorMod(j - 2, 8)).getPiece() != null && activePlayer.getPieces().contains(fields.get(i * 8 + Math.floorMod(j - 2, 8)).getPiece())) {
                    p.getPiece().setMyColor(activePlayer.getMyColor().getBright());
                    fields.get(i * 8 + Math.floorMod(j - 1, 8)).getPiece().setMyColor(activePlayer.getMyColor().getBright());
                    fields.get(i * 8 + Math.floorMod(j - 2, 8)).getPiece().setMyColor(activePlayer.getMyColor().getBright());
                    fireZmienTryb("mill");
                    toRemove = true;
                }
            }
        } else {
            if (fields.get(i * 8 + Math.floorMod(j - 1, 8)).getPiece() != null && activePlayer.getPieces().contains(fields.get(i * 8 + Math.floorMod(j - 1, 8)).getPiece())) {
                if (fields.get(i * 8 + Math.floorMod(j + 1, 8)).getPiece() != null && activePlayer.getPieces().contains(fields.get(i * 8 + Math.floorMod(j + 1, 8)).getPiece())) {
                    p.getPiece().setMyColor(activePlayer.getMyColor().getBright());
                    fields.get(i * 8 + Math.floorMod(j - 1, 8)).getPiece().setMyColor(activePlayer.getMyColor().getBright());
                    fields.get(i * 8 + Math.floorMod(j + 1, 8)).getPiece().setMyColor(activePlayer.getMyColor().getBright());
                    fireZmienTryb("mill");
                    toRemove = true;
                }
            }
        }
        if (j % 2 == 1) {
            if (fields.get(Math.floorMod(fields.indexOf(p) + 8, 24)).getPiece() != null && activePlayer.getPieces().contains(fields.get(Math.floorMod(fields.indexOf(p) + 8, 24)))) {
                if (fields.get(Math.floorMod(fields.indexOf(p) - 8, 24)).getPiece() != null && activePlayer.getPieces().contains(fields.get(Math.floorMod(fields.indexOf(p) - 8, 24)))) {
                    p.getPiece().setMyColor(activePlayer.getMyColor().getBright());
                    fields.get(Math.floorMod(fields.indexOf(p) + 8, 24)).getPiece().setMyColor(activePlayer.getMyColor().getBright());
                    fields.get(Math.floorMod(fields.indexOf(p) - 8, 24)).getPiece().setMyColor(activePlayer.getMyColor().getBright());
                    fireZmienTryb("mill");
                    toRemove = true;
                }
            }
        }
    }

    private void backColors() {
        for (Piece p : activePlayer.getPieces()) {
            if (p != null) p.setMyColor(activePlayer.getMyColor().getDark());
        }
    }

    private void removePiece(Field p) {
        Piece pom;
        pom = p.getPiece();
        if (pom != null && inactivePlayer.getPieces().contains(pom)) {
            p.setPiece(null);
            inactivePlayer.getPieces().remove(pom);
            toRemove = false;
            backColors();
            changePlayer();
            repaint();
        }
    }

    private boolean haveMove() {
        for (Piece p : activePlayer.getPieces()) {
            int i = fields.indexOf(p) / 8;
            int j = fields.indexOf(p) % 8;
            if (fields.get(i * 8 + Math.floorMod(j + 1, 8)).getPiece() == null) return true;
            if (fields.get(i * 8 + Math.floorMod(j - 1, 8)).getPiece() == null) return true;
            if (j % 2 == 1) {
                if (i == 0) {
                    if (fields.get(fields.indexOf(p) + 8).getPiece() == null) return true;
                } else if (i == 1) {
                    if (fields.get(fields.indexOf(p) + 8).getPiece() == null) return true;
                    if (fields.get(fields.indexOf(p) - 8).getPiece() == null) return true;
                } else {
                    if (fields.get(fields.indexOf(p) - 8).getPiece() == null) return true;
                }
            }
        }
        return false;
    }

    private void changePlayer() {
        if (activePlayer == player1) {
            activePlayer = player2;
            inactivePlayer = player1;
        } else {
            activePlayer = player1;
            inactivePlayer = player2;
        }
        activePlayer.setActive(true);
        inactivePlayer.setActive(false);
        if (activePlayer.getPieceToPlace() > 0) fireZmienTryb("place");
        else fireZmienTryb("move");
    }

    private boolean isWin() {
        return activePlayer.getPieces().size() < 3 && activePlayer.getPieceToPlace() == 0;
    }

    private void placePiece(Player player, Field p) {
        Piece pom = null;
        if (p.getPiece() == null) {
            pom = new Piece(new Rectangle((int) p.getX(), (int) p.getY(), (int) p.getWidth(), (int) p.getHeight()));
            pom.setMyColor(activePlayer.getMyColor().getDark());
            player.getPieces().add(pom);
            player.setPieceToPlace(player.getPieceToPlace() - 1);
            p.setPiece(pom);
            checkMill(p);
            if (!toRemove) changePlayer();
        }
        repaint();
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void initGame() {
        myLiteners.clear();
        player1.getPieces().clear();
        player2.getPieces().clear();
        freeFieds();

        activePlayer = null;
        player1.setPieceToPlace(countOfPieces);
        player2.setPieceToPlace(countOfPieces);
        repaint();
        activePlayer = player1;
        activePlayer.setActive(true);
        inactivePlayer = player2;
        inactivePlayer.setActive(false);
    }

    public void createFields() {
        int margines = (int) (0.052 * Integer.parseInt(configuration.getValue("boardwidth")));
        int odleglosc = (Integer.parseInt(configuration.getValue("boardwidth")) - 2 * margines) / 6;
        int kwadrat = 3 * odleglosc / 5;
        int zacznijKolo = kwadrat / 2;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Field r;
                for (int j = 0; j < 3; j++) {
                    int k = 0;
                    for (int i = 0; i < p.length; i++) {
                        if (p[j][i]) {
                            r = new Field(margines + i * odleglosc - zacznijKolo, margines + j * odleglosc - zacznijKolo, kwadrat, kwadrat);
                            fields.add(r);

                            k++;
                        }
                    }
                    for (int i = 1 + j; i < p.length; i++) {
                        if (p[i][p.length - 1 - j]) {
                            r = new Field(margines + (p.length - 1 - j) * odleglosc - zacznijKolo, margines + i * odleglosc - zacznijKolo, kwadrat, kwadrat);
                            fields.add(r);
                            k++;
                        }
                    }
                    for (int i = p.length - 2 - j; i >= 0; i--) {
                        if (p[p.length - 1 - j][i]) {
                            r = new Field(margines + i * odleglosc - zacznijKolo, margines + (p.length - 1 - j) * odleglosc - zacznijKolo, kwadrat, kwadrat);
                            fields.add(r);
                            k++;
                        }
                    }
                    for (int i = p.length - 2 - j; i > 0 + j; i--) {
                        if (p[i][j]) {
                            r = new Field(margines + j * odleglosc - zacznijKolo, margines + i * odleglosc - zacznijKolo, kwadrat, kwadrat);
                            fields.add(r);
                            k++;
                        }
                    }

                }
            }
        });
    }

    void freeFieds() {
        for (Field f : fields) {
            f.setPiece(null);
        }
    }

    public void setCountOfPieces(int countOfPieces) {
        this.countOfPieces = countOfPieces;
    }

    public synchronized void addListener(WinListener l) {
        myLiteners.add(l);
    }

    public synchronized void removeListener(WinListener l) {
        myLiteners.remove(l);
    }

    private synchronized void fireWinEvent(Player player) {
        WinEvent winEvent = new WinEvent(this,player, locale);
        Iterator listeners = myLiteners.iterator();
        while (listeners.hasNext()) {
            ((WinListener) listeners.next()).win(winEvent);
        }
    }

    private synchronized void fireZmienTryb(String tryb) {
        ChangeModeEvent zmienTryb = new ChangeModeEvent(this, tryb);
        boardListener.zmienTryb(zmienTryb);
    }

    public void setBoardListener(BoardListener boardListener) {
        this.boardListener = boardListener;
    }

    @Override
    public void changeLanguage(ChangeLanguageEvent changeLanguageEvent) {

    }

    private class MouseHandler implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            Field field = findField(e.getPoint());
            if (field != null) {
                if (toRemove) {
                    removePiece(field);
                    if (isWin() || !haveMove()) {
                        inactivePlayer.setWin(true);
                        fireWinEvent(inactivePlayer);
                    }
                } else {
                    if (activePlayer.isPionkiUstawione()) {
                        if (activePiece != null) {
                            movePiece(field);
                            activePiece = null;
                        } else {
                            selectPiece(field);
                        }
                    } else {
                        placePiece(activePlayer, findField(e.getPoint()));
                    }
                }
            } else {
                if (activePlayer.getPieceToPlace() == 0) {
                    fireZmienTryb("move");
                }
                activePiece = null;
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}