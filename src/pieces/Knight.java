package pieces; 
 
import chessgui.Board;

public class Knight extends Piece{
    public Knight(int x, int y, boolean isWhite, Board board) {
        super(x, y, isWhite, Type.KNIGHT, 3, board);  
    }
}
