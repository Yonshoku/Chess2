package pieces; 
 
import chessgui.Board;

public class Queen extends Piece{
    public Queen(int x, int y, boolean isWhite, Board board) {
        super(x, y, isWhite, Type.QUEEN, 9, board); 
    }
}
