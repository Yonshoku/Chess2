package chessgui;
 
import pieces.*;

import java.awt.event.*;

public class BoardMouseHandler extends MouseAdapter {
    Board board = Board.getInstance();
    Position position = Position.getInstance();

    Piece pieceToMove, pieceToCapture;
    boolean isMoveStarted = false;
    int fromX, fromY, toX, toY;

    @Override
    public void mousePressed(MouseEvent e) {
        if (!isMoveStarted) {
            pieceToMove = position.getPiece(board.getX(e.getX()), board.getY(e.getY()));

            if (pieceToMove != null) {
                fromX = board.getX(e.getX());
                fromY = board.getY(e.getY()); 
                isMoveStarted = true;
            } 

        } else {
            pieceToCapture = position.getPiece(board.getX(e.getX()), board.getY(e.getY()));

            // Piece clicked twice
            if (board.getX(e.getX()) == fromX && board.getY(e.getY()) == fromY)
                return;

            // Clicked piece of same color
            if (pieceToCapture != null && pieceToMove.isWhite() == pieceToCapture.isWhite()) {
                fromX = board.getX(e.getX());
                fromY = board.getY(e.getY());
                return;
            }

            toX = board.getX(e.getX());
            toY = board.getY(e.getY());

            Game.getInstance().processMove(fromX, fromY, toX, toY);    
            isMoveStarted = false;
        }
    }

}
