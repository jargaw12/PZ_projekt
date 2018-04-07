package panels.game;

import configuration.MyConfiguration;
import panels.myColor.MyColor;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Player {
    private MyColor myColor;
    private String user;
    private String name;
    private char sex;
    private boolean active;
    private int pieceToPlace = 9;
    private boolean win = false;
    private int score=0;
    MyConfiguration configuration= new MyConfiguration();
    private ArrayList<Piece> pieces = new ArrayList<>();

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean newActive) {
        boolean oldActive = active;
        active = newActive;
        score++;
        mPcs.firePropertyChange("active", oldActive, active);
    }

    private PropertyChangeSupport mPcs = new PropertyChangeSupport(this);

    public int getPieceToPlace() {
        return pieceToPlace;
    }

    public void setPieceToPlace(int newDoRozlozenia) {
        //int oldDoRozlozenia= pieceToPlace;
        pieceToPlace = newDoRozlozenia;
        //mPcs.firePropertyChange("pieceToPlace",oldDoRozlozenia,pieceToPlace);
    }

    public Player() {
        //pieceToPlace = 9;
    }

    public Player(MyColor myColor, String name, char sex) {
        setMyColor(myColor);
        setName(name);
        this.sex = sex;
        user = "icons/player_"+ sex + "_100_" + myColor.getName() + ".png";

    }

    public int getScore() {
        return score;
    }

    public boolean isPionkiUstawione() {
        return pieceToPlace == 0;
    }

    public void
    addPropertyChangeListener(PropertyChangeListener listener) {
        mPcs.addPropertyChangeListener(listener);
    }

    public void
    removePropertyChangeListener(PropertyChangeListener listener) {
        mPcs.removePropertyChangeListener(listener);
    }

//    int wolnyPionek() {
//        for (panels.game.Piece p : pieces)
//            if (p == null) return pieces.indexOf(p);
//        return 0;
//    }

    public Piece findPiece(Point p) {
        for (Piece r : pieces) {
            if (r.contains(p))
                return r;
        }
        return null;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public MyColor getMyColor() {
        return myColor;
    }

    private void setMyColor(MyColor myColor) {
        this.myColor = myColor;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public void setPieces(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }
}
