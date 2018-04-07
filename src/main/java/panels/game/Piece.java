package panels.game;

import java.awt.*;

public class Piece extends Rectangle {
    private Color color;
    public Piece(int x, int y, int width, int height) {
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }

    public Color getMyColor() {
        return color;
    }

    public void setMyColor(Color color) {
        this.color = color;
    }

    public Piece(Rectangle r) {
        super(r);
    }
}
