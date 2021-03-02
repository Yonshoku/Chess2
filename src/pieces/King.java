package pieces; 
 
import chessgui.*;

public class King extends Piece{
    private boolean hasMoved = false;

    public King(boolean isWhite) {
        super(isWhite, Type.KING, 0); 
    }

    @Override 
    public boolean isMoveCorrect(int fromX, int fromY, int toX, int toY) {
        if (Math.abs(fromX - toX) <= 1 && Math.abs(fromY - toY) <= 1
                && !doesCaptureOwnPiece(fromX, fromY, toX, toY)
                && !isKingMovesToAttackedField(fromX, fromY, toX, toY))
            return true;

        return false;
    }

    public boolean isKingMovesToAttackedField(int fromX, int fromY, int toX, int toY) {
        boolean isMoves = false;

        Game.getGame().makeMove(new Move(this, fromX, fromY, position.getPiece(toX, toY), toX, toY, MoveType.REGULAR));
        if (position.isFieldAttackedByEnemy(this, toX, toY))
            isMoves = true;
        Game.getGame().undoMove(); 

        return isMoves;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
