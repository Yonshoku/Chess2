package chessgui;
 
import pieces.*;

import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class BoardMouseHandler extends MouseAdapter {
    Board board = Board.getInstance();
    Position position = Position.getInstance();

    Piece pieceToMove, pieceToCapture;
    boolean isMoveStarted = false;
    int fromX, fromY, toX, toY;

    private java.util.Timer timer = new Timer();

    private int touches = 0;

    @Override
    public void mousePressed(MouseEvent e) {
        touches += 1;

        if (board.isOverlayShowed()) {
            Game.getInstance().transformPawn(board.getOverlayPiece(e.getX(), e.getY()));
            return;
        } 

        if (!isMoveStarted) {
            pieceToMove = position.getPiece(board.getX(e.getX()), board.getY(e.getY()));

            if (pieceToMove != null) {
                fromX = board.getX(e.getX());
                fromY = board.getY(e.getY()); 
                isMoveStarted = true;
                board.setActiveField(fromX, fromY);
            } else {
                board.setActiveField(-1, -1);
            }

            board.repaint();

        } else {

            pieceToCapture = position.getPiece(board.getX(e.getX()), board.getY(e.getY()));

            // Piece clicked twice
            if (board.getX(e.getX()) == fromX && board.getY(e.getY()) == fromY)
                return;

            // Clicked piece of same color
            if (pieceToCapture != null && pieceToMove.isWhite() == pieceToCapture.isWhite()) {
                fromX = board.getX(e.getX());
                fromY = board.getY(e.getY());

                board.setActiveField(fromX, fromY);
                board.repaint();
                return;
            }

            toX = board.getX(e.getX());
            toY = board.getY(e.getY());

            board.setActiveField(-1, -1);
            board.repaint(); 

            Game.getInstance().processMove(fromX, fromY, toX, toY);    
            isMoveStarted = false;
        }
    }
}
