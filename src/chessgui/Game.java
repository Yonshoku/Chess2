package chessgui;

import pieces.*;

// To do
// Checks
// Mate
// Draw

public class Game {
    private static Game game = new Game();

    Position position;
    Board board;
    MovesLog movesLog;

    Piece pieceToMove;
    boolean isMoveMade = false;
    int fromX, fromY, toX, toY;

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

    public static void processMove(int fromX, int fromY, int toX, int toY) {
        pieceToMove = position.getPiece(fromX, fromY);
        
        // The right side is making move
        if(pieceToMove.isWhite() != position.isWhiteTurn()) {
            System.out.println("wrong side move");
            return;
        }


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
                    this.fromX = fromX;
                    this.fromY = fromY;
                    this.toX = toX;
                    this.toY = toY;
                    board.setOverlayShowed(true);
                    board.setOverlayColor(pawn.isWhite());
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

        if (isMoveMade) 
            finishMove(); 
    }

    public void finishMove() { 
        position.changeTurn();
        board.repaint();
        isMoveMade = false;
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
        } else if (moveToUndo.getType() == MoveType.TRANSFORMING) {
            position.deletePiece(moveToUndo.getToX(), moveToUndo.getToY());
            position.addPiece(moveToUndo.getCapturedPiece(), moveToUndo.getToX(), moveToUndo.getToY());
            position.addPiece(new Pawn(moveToUndo.getMovedPiece().isWhite()), moveToUndo.getFromX(), moveToUndo.getFromY());
        }

        movesLog.deleteLastMove();
        movesLog.decrementMovesNum();
    }

    public void transformPawn(Piece piece) {
        if (piece != null) {
            makeMove(new Move(piece, fromX, fromY, position.getPiece(toX, toY), toX, toY, MoveType.TRANSFORMING));
            finishMove();
        }
        
        board.setOverlayShowed(false);
        finishMove();
    }
}
