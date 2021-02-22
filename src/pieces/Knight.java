package pieces; 
 
import chessgui.Board;

public class Knight extends Piece{
    int[][] possibleMoves = {
        {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}
    };

    public Knight(int x, int y, boolean isWhite, Board board) {
        super(x, y, isWhite, Type.KNIGHT, 3, board);  
    }
    
    @Override
    public boolean isMoveCorrect(int destX, int destY) {

        if (isTakesSameColorPiece(destX, destY))
            return false;

        for (int i = 0; i < possibleMoves.length; i++) {
            if (destX == getX() + possibleMoves[i][0] && destY == getY() + possibleMoves[i][1])
                return true;
        }

        return false;
    }
}
