package pieces; 
 
import chessgui.Board;

public class Bishop extends Piece{

    public Bishop(int x, int y, boolean isWhite, Board board) {
        super(x, y, isWhite, Type.BISHOP, 3, board);    
    }

    @Override
    public boolean isMoveCorrect(int destX, int destY) {
        // Check if it takes same color piece
        if (isTakesSameColorPiece(destX, destY))
            return false;

        // "Main' diagonal, from left top to right bottom
        // (0,1) and (4,5) -> (0 - 4) / (1 - 5) = (4 - 0) / (5 - 1) = 1
        int fromX, fromY;
        int fieldsBetween = Math.abs(getX() - destX) - 1;

        if ((getX() - destX) / (getY() - destY) == 1) {
            
            // Check if there are pieces between 
            fromX = Math.min(getX(), destX);
            fromY = Math.min(getY(), destY);

            for (int i = 1; i <= fieldsBetween; i++) {
                if (board.getPiece(fromX + i, fromY + i) != null) {
                    return false;
                }
            }

            return true;
        } 

        // "Secondary" diagonal, from right top to left bottom
        // (7,1) and (2,6) -> (7 - 2) / (1 - 6) = (2 - 7) / (6 - 1) = -1
        int m;

        if ((getX() - destX) / (getY() - destY) == -1) {
            
            // Check if there are pieces between
            m = getX() > destX ? -1 : 1;

            for (int i = 1; i <= fieldsBetween; i++) {
                if (board.getPiece(getX() + i * m, getY() + i * -m) != null)
                    return false;
            }

            return true;
        } 

        // Move isn't diagonal
        return false;
    }

}
