package chessgui;

import pieces.*;

import java.awt.event.*;


public class PawnTransformOverlayMouseHandler extends MouseAdapter {
    private Piece clickedPiece;

    @Override 
    public void mousePressed(MouseEvent e) {
        clickedPiece = PawnTransformOverlay.getInstance().getPiece(e.getX(), e.getY());

        if (clickedPiece != null) 
            Game.getInstance().transformPawn(clickedPiece); 
    }
}
