package pieces; 
 
import chessgui.Board;

public class Bishop extends Piece{

    public Bishop(int x, int y, boolean isWhite, Board board) {
        super(x, y, isWhite, Type.BISHOP, 3, board);    
    }

    @Override
    public boolean isMoveCorrect(int destX, int destY) {
        int startX = board.getActivePieceX();
        int startY = board.getActivePieceY();

        // Check if it takes same color piece
        if (isTakesSameColorPiece(destX, destY))
            return false;

        // "Main' diagonal, from left top to right bottom
        // (0,1) and (4,5) -> (0 - 4) / (1 - 5) = (4 - 0) / (5 - 1) = 1
        int fromX, fromY;
        int fieldsBetween = Math.abs(startX - destX) - 1;

        if ((startX - destX) / (startY - destY) == 1) {
            
            // Check if there are pieces between 
            fromX = Math.min(startX, destX);
            fromY = Math.min(startY, destY);

            for (int i = 1; i <= fieldsBetween; i++) {
                if (board.getPiece(fromX + i, fromY + i) != null) {
                    System.out.println((fromX + i) + " " + (fromY + i));
                    return false;
                }
            }

            return true;
        } 

        // "Secondary" diagonal, from right top to left bottom
        // (7,1) and (2,6) -> (7 - 2) / (1 - 6) = (2 - 7) / (6 - 1) = -1
        int m;

        if ((startX - destX) / (startY - destY) == -1) {
            
            // Check if there are pieces between
            m = startX > destX ? -1 : 1;

            for (int i = 1; i <= fieldsBetween; i++) {
                if (board.getPiece(startX + i * m, startY + i * -m) != null)
                    return false;
            }

            return true;
        } 

        // Move isn't diagonal
        return false;
    }

}
