package pieces; 
 
import chessgui.Board;

public class Pawn extends Piece{

    public Pawn(int x, int y, boolean isWhite, Board board) {
        super(x, y, isWhite, Type.PAWN, 1, board); 
    }

}
