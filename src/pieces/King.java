package pieces; 
 
 import chessgui.Board;

public class King extends Piece{

    public King(int x, int y, boolean isWhite, Board board) {
        super(x, y, isWhite, Type.KING, 0, board); 
    }

}
