package pieces; 
 
import chessgui.Board;

public class Queen extends Piece{

    public Queen(int x, int y, boolean isWhite, Board board) {
        super(x, y, isWhite, Type.QUEEN, 9, board); 
    }

    @Override
    public boolean isMoveCorrect(int destX, int destY) {
        Bishop b = new Bishop(getX(), getY(), isWhite(), board);
        Rook r = new Rook(getX(), getY(), isWhite(), board);

        return r.isMoveCorrect(destX, destY) || b.isMoveCorrect(destX, destY);
    }
}

