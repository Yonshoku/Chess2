package pieces; 
 
import chessgui.Board;

public class Queen extends Piece{

    public Queen(boolean isWhite) {
        super(isWhite, Type.QUEEN, 9); 
    }

    @Override
    public boolean isMoveCorrect(int fromX, int fromY, int toX, int toY) {
        Bishop b = new Bishop(isWhite());
        Rook r = new Rook(isWhite());

        return r.isMoveCorrect(fromX, fromY, toX, toY) || b.isMoveCorrect(fromX, fromY, toX, toY);
    }
}

