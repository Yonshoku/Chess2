package chessgui;

import pieces.*;

import java.util.*;

public class Position {
    private static Position position;

    int ROWS = Board.getBoard().getRows();
    int COLS = Board.getBoard().getCols();
    Piece[][] layout = new Piece[ROWS][COLS];
    boolean isLayoutReversed = new Random().nextInt(2) == 1 ? true : false;

    boolean isWhiteTurn = true;
    Pawn passantPawn;

    public static final Position getPosition() {
        if (position == null) {
            position = new Position();
        } 

        return position;
    }

    public void initLayout() {
        layout[0][0] = new Rook(false); 
        layout[1][0] = new Knight(false); 
        layout[2][0] = new Bishop(false); 
        layout[3][0] = new Queen(false); 
        layout[4][0] = new King(false); 
        layout[5][0] = new Bishop(false); 
        layout[6][0] = new Knight(false); 
        layout[7][0] = new Rook(false); 

        layout[0][1] = new Pawn(false);
        layout[1][1] = new Pawn(false);
        layout[2][1] = new Pawn(false);
        layout[3][1] = new Pawn(false);
        layout[4][1] = new Pawn(false);
        layout[5][1] = new Pawn(false);
        layout[6][1] = new Pawn(false);
        layout[7][1] = new Pawn(false);

        layout[0][6] = new Pawn(true);
        layout[1][6] = new Pawn(true);
        layout[2][6] = new Pawn(true);
        layout[3][6] = new Pawn(true);
        layout[4][6] = new Pawn(true);
        layout[5][6] = new Pawn(true);
        layout[6][6] = new Pawn(true);
        layout[7][6] = new Pawn(true);

        layout[0][7] = new Rook(true);
        layout[1][7] = new Knight(true);
        layout[2][7] = new Bishop(true);
        layout[3][7] = new Queen(true);
        layout[4][7] = new King(true);
        layout[5][7] = new Bishop(true);
        layout[6][7] = new Knight(true);
        layout[7][7] = new Rook(true);

        // Fill empty fields
        for (int i = 0; i < 8; i++) {
            for (int j = 2; j < 6; j++) {
                layout[i][j] = null;
            }
        }

        if (isLayoutReversed) { 
            reverseLayout();
        }
    }

    public void reverseLayout() {
        Piece p;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS / 2; j++) {
                p = layout[i][j];
                layout[i][j] = layout[i][Math.abs(COLS - j - 1)];
                layout[i][Math.abs(COLS - j - 1)] = p;
            }
        }
    }

    public boolean isLayoutReversed() {
        return isLayoutReversed;
    }

    public Piece[][] getLayout() {
        return layout;
    }

    public Piece getPiece(int x, int y) {
        try {
            return layout[x][y];
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Piece deletePiece(int x, int y) {
        try {
            Piece pieceToDelete = layout[x][y];

            layout[x][y] = null;
            return pieceToDelete;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addPiece(Piece p, int x, int y) {
        try {
            layout[x][y] = p;
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean isFieldAttackedByEnemy(Piece p, int x, int y) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (layout[i][j] != null) {
                    if ((i != x && j != y) && p.isWhite() != layout[i][j].isWhite()
                            && layout[i][j].isMoveCorrect(i, j, x, y)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void changeTurn() {
        isWhiteTurn = !isWhiteTurn;
    }

    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }
}
