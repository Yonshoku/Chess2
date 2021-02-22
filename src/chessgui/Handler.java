package chessgui;
 
import pieces.*;

import java.awt.event.*;

// ToDo
// paintNotation
// King attacked rule
public class Handler extends MouseAdapter {
    Board board;
    Piece activePiece;
    boolean isMoveStarted = false;
    int destX, destY;

    public Handler(Board board) {
        this.board = board;     
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Move wasn't started, clicked piece becomes active
        if (!isMoveStarted) {
            activePiece = board.getPiece(board.getX(e.getX()), board.getY(e.getY())); 
            System.out.println(activePiece);

            if (activePiece != null) {
                // Mark clicked field as active
                // Piece on this field going to be moved
                board.setActivePieceX(activePiece.getX());
                board.setActivePieceY(activePiece.getY());

                isMoveStarted = true;
            }

        // Move started
        } else {
            destX = board.getX(e.getX());
            destY = board.getY(e.getY());

            // If same piece clicked twice nothing happens, active piece stays same
            if (activePiece.getX() == destX && activePiece.getY() == destY)
                return;

            // If move correct
            if (activePiece.isWhite() == board.isWhiteMoves && activePiece.isMoveCorrect(destX, destY)) {
                // Capture enemy's piece if it is there
                board.deletePiece(destX, destY);

                // Move active piece
                activePiece.setX(destX);
                activePiece.setY(destY);

                board.isWhiteMoves = !board.isWhiteMoves;
            }

            isMoveStarted = false;
            board.repaint();
        }

    }

}
