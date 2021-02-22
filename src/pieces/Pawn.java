package pieces; 
 
import chessgui.Board;

public class Pawn extends Piece{
    boolean hasMoved = false;
    boolean isBottomSide = (isWhite() != board.isPositionReversed());

    public Pawn(int x, int y, boolean isWhite, Board board) {
        super(x, y, isWhite, Type.PAWN, 1, board); 
    }

    @Override 
    public boolean isMoveCorrect(int destX, int destY) {

        if (isBottomSide) {
            // Move forward
            if (getY() - destY == 1 && getX() == destX
                    && board.getPiece(destX, destY) == null) {
                hasMoved = true;
                return true;
            }

            // Move forward from starting position by 2 fields
            if (getY() - destY == 2 && getX() == destX
                    && board.getPiece(destX, destY) == null
                    && board.getPiece(destX, destY + 1) == null
                    && !hasMoved) {
                hasMoved = true;
                return true;
            }

            // Capture enemy's piece
            if (getY() - destY == 1 && (getX() - destX == 1 || getX() - destX == -1)
                    && board.getPiece(destX, destY) != null
                    && !isTakesSameColorPiece(destX, destY)) {
                hasMoved = true;
                return true;
            }

        } else {
            // Move forward
            if (getY() - destY == -1 && getX() == destX
                    && board.getPiece(destX, destY) == null) {
                hasMoved = true;
                return true;
            }

            // Move forward from starting position by 2 fields
            if (getY() - destY == -2 && getX() == destX
                    && board.getPiece(destX, destY) == null
                    && board.getPiece(destX, destY - 1) == null 
                    && !hasMoved) {
                hasMoved = true;
                return true;
            }

            // Capture enemy's piece
            if (getY() - destY == -1 && (getX() - destX == 1 || getX() - destX == -1)
                    && board.getPiece(destX, destY) != null
                    && !isTakesSameColorPiece(destX, destY)) { 
                hasMoved = true;
                return true;
            }
        }

        return false;
    }

    public boolean hasMoved() {
        return hasMoved;
    }
    
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved; 
    }
}
