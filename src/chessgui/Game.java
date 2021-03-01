package chessgui;

import pieces.*;

public class Game {
    private static Game game;

    Position position;
    Board board;
    MovesLog movesLog;

    Piece pieceToMove;
    boolean isMoveMade = false;

    public static final Game getGame() {
        if (game == null) {
            game = new Game();
        }

        return game;
    }

    public Game() {
        // Init game
        position = Position.getPosition();
        position.initLayout();

        board = Board.getBoard();
        board.repaint();

        movesLog = movesLog.getMovesLog();
    } 

    public void processMove(int fromX, int fromY, int toX, int toY) {
        pieceToMove = position.getPiece(fromX, fromY);
        
        // The rights side is making move
        if(pieceToMove.isWhite() != position.isWhiteTurn())
            return;

        // Process pawn behaviour
        if (pieceToMove.getType() == Type.PAWN) {
            Pawn pawn = (Pawn)pieceToMove;

            if (pawn.isMoveCorrect(fromX, fromY, toX, toY)) {
                if (pawn.doesCapturePassantPawn(fromX, fromY, toX, toY)) {
                    position.deletePiece(fromX, fromY);
                    position.addPiece(pieceToMove, toX, toY);
                    position.deletePiece(toX, fromY);
                } else {
                    makeMove(fromX, fromY, toX, toY);
                }

                isMoveMade = true;
            }

        } else if(pieceToMove.isMoveCorrect(fromX, fromY, toX, toY)) {
            makeMove(fromX, fromY, toX, toY);

            isMoveMade = true;
        }

        if (isMoveMade) {
            // Write moves which was made
            movesLog.add(new Move(pieceToMove, fromX, fromY, position.getPiece(toX, toY), toX, toY)); 
            movesLog.plusOneMovesMade();

            position.changeTurn();
            board.repaint();
            isMoveMade = false;
        }
    }

    public void makeMove(int fromX, int fromY, int toX, int toY) {
        position.deletePiece(fromX, fromY); 
        position.deletePiece(toX, toY);
        position.addPiece(pieceToMove, toX, toY);
    }
}
