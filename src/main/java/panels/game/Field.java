package panels.game;

import java.awt.*;

public class Field extends Rectangle {
    private Piece piece;
    public Field(int x, int y, int width, int height) {
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Field(Rectangle rectangle){
        super(rectangle);
    }
}
