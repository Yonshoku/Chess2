package chessgui;
 
import pieces.*;

import java.awt.event.*;

// ToDo
// paintNotation
// King attacked rule
// Passant pawns
// Piece clicked after piece should work
// Color active field, last move
public class Handler extends MouseAdapter {
    Board board;
    Piece p; 
    int destX, destY;

    public Handler(Board board) {
        this.board = board;     
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Moves was not started or clicked finish field contains piece of
        // same color as active piece. Clicked piece becomes new active piece

        if (board.getActivePiece() == null) {
            p = board.getPiece(board.getX(e.getX()), board.getY(e.getY()));
            board.setActivePiece(p);

            System.out.println(board.getActivePiece());
        } else {
            destX = board.getX(e.getX());
            destY = board.getY(e.getY());

            // If same piece clicked twice nothing happens, active piece stays same
            if (board.getActivePiece().getX() == destX 
                    && board.getActivePiece().getY() == destY)
                return;

            // If clicked piece of same color but active piece already exists
            // Clicked piece becomes active piece
            p = board.getPiece(destX, destY);
            if (p != null && p.isWhite() == board.getActivePiece().isWhite()) {
                board.setActivePiece(p);
                return;
            }

            // If move correct
            if (board.getActivePiece().isWhite() == board.isWhiteMoves 
                    && board.getActivePiece().isMoveCorrect(destX, destY)) {

                // Capture enemy's piece if it is there
                board.deletePiece(destX, destY);

                // Move active piece
                board.getActivePiece().setX(destX);
                board.getActivePiece().setY(destY);

                board.changeTurn();
            }

            board.setActivePiece(null);
            board.repaint();
        }

    }

}
