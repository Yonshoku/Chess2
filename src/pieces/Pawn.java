package pieces; 
 
import chessgui.*;

public class Pawn extends Piece{
    boolean hasMoved = false;
    boolean isBottomSide = (isWhite() != position.isLayoutReversed());
    int dirY = isBottomSide ? 1 : -1;

    public Pawn(boolean isWhite) {
        super(isWhite, Type.PAWN, 1); 
    }

    @Override 
    public boolean isMoveCorrect(int fromX, int fromY, int toX, int toY) {
        // Move forward
        if (fromY - toY == dirY && fromX == toX
                && position.getPiece(toX, toY) == null) 
            return true;

        // Move two fields forward
        if (fromY - toY == dirY * 2 && fromX == toX
                && position.getPiece(toX, toY) == null
                && position.getPiece(toX, toY + dirY) == null
                && !hasMoved)
            return true;

        // Capture enemy's piece
        if (fromY - toY == dirY && Math.abs(fromX - toX) == 1
                && position.getPiece(toX, toY) != null
                && !doesCaptureOwnPiece(fromX, fromY, toX, toY)) 
            return true;

        // Capture passant pawn
        if (doesCapturePassantPawn(fromX, fromY, toX, toY))
            return true; 

        return false;
    }

    public boolean hasMoved() {
        return hasMoved;
    }
    
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved; 
    }

    public boolean doesCapturePassantPawn(int fromX, int fromY, int toX, int toY){
        Move lastMove = MovesLog.getMovesLog().getLastMove();  

        if (lastMove == null) 
            return false;

        else if (lastMove.getMovedPiece().getType() == Type.PAWN 
                    && Math.abs(lastMove.getFromY() - lastMove.getToY()) == 2 // Passant pawn exists
                    && Math.abs(lastMove.getToX() - fromX) == 1
                    && lastMove.getToY() == fromY // Passant pawn stays to the left or right and on one line
                    && Math.abs(fromX - toX) == 1 && fromY - toY == dirY) // This pawn moves diagonally forward
            return true;

        return false;
    }

}
