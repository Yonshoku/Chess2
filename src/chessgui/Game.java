package chessgui;

import pieces.*;

public class Game {
    private static Game game = new Game();

    Position position;
    Board board;
    MovesLog movesLog;

    Piece pieceToMove;
    boolean isMoveMade = false;

    public static final Game getInstance() {
        return game;
    }

    private Game() {
        // Init game
        position = Position.getInstance();
        position.initLayout();

        board = Board.getInstance();
        board.repaint();

        movesLog = movesLog.getInstance();
    } 

    public void processMove(int fromX, int fromY, int toX, int toY) {
        pieceToMove = position.getPiece(fromX, fromY);
        
        // The right side is making move
        if(pieceToMove.isWhite() != position.isWhiteTurn())
            return;

        // Process pawn behaviour
        if (pieceToMove.getType() == Type.PAWN) {
            Pawn pawn = (Pawn)pieceToMove;

            if (pawn.isMoveCorrect(fromX, fromY, toX, toY)) {
                if (pawn.doesCapturePassantPawn(fromX, fromY, toX, toY)) {
                    movesLog.add(new Move(pieceToMove, fromX, fromY, position.getPiece(toX, fromY), toX, toY, MoveType.PASSANT)); 
                    movesLog.incrementMovesNum();

                    position.deletePiece(fromX, fromY);
                    position.addPiece(pieceToMove, toX, toY);
                    position.deletePiece(toX, fromY);
                } else if (pawn.doesReachedLastLine(toY)) {
                    PawnTransformOverlay.getInstance().showOverlay(pawn.isWhite());
                    makeMove(new Move(pieceToMove, fromX, fromY, position.getPiece(toX, toY), toX, toY, MoveType.REGULAR)); 
                } else {
                    makeMove(new Move(pieceToMove, fromX, fromY, position.getPiece(toX, toY), toX, toY, MoveType.REGULAR)); 
                }

                pawn.setHasMoved(true); 
                isMoveMade = true;
            }

        } else if(pieceToMove.isMoveCorrect(fromX, fromY, toX, toY)) {
            makeMove(new Move(pieceToMove, fromX, fromY, position.getPiece(toX, toY), toX, toY, MoveType.REGULAR)); 

            isMoveMade = true;
        }

        if (isMoveMade) {
            position.changeTurn();
            board.repaint();
            isMoveMade = false;
        }
    }

    public void makeMove(Move move) {
        movesLog.add(move); 
        movesLog.incrementMovesNum();

        position.deletePiece(move.getFromX(), move.getFromY()); 
        position.deletePiece(move.getToX(), move.getToY());
        position.addPiece(move.getMovedPiece(), move.getToX(), move.getToY());
    }

    public void undoMove() {
        Move moveToUndo = movesLog.getLastMove();

        if (moveToUndo.getType() == MoveType.REGULAR) {
            position.deletePiece(moveToUndo.getToX(), moveToUndo.getToY());
            position.addPiece(moveToUndo.getCapturedPiece(), moveToUndo.getToX(), moveToUndo.getToY());
            position.addPiece(moveToUndo.getMovedPiece(), moveToUndo.getFromX(), moveToUndo.getFromY());
        }

        movesLog.deleteLastMove();
        movesLog.decrementMovesNum();
    }

    public void transformPawn(Piece p) {
        movesLog.add(new Move(p))
    }
}
