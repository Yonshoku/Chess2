package chessgui;

import pieces.*;

public  class Move {
    private Piece movedPiece, capturedPiece;
    private int fromX, fromY, toX, toY;

    public Move(Piece movedPiece, int fromX, int fromY, Piece capturedPiece, int toX, int toY) {
        this.movedPiece = movedPiece;
        this.fromX = fromX;
        this.fromY = fromY;

        this.capturedPiece = capturedPiece;
        this.toX = toX;
        this.toY = toY; 
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public int getFromX() {
        return fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public int getToX() {
        return toX;
    }

    public int getToY() {
        return toY;
    }
}
