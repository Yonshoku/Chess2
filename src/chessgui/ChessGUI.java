package chessgui;

public class ChessGUI {
    public static void main(String[] args) {
        ChessFrame chessFrame = new ChessFrame(); 
        Board board = new Board();

        chessFrame.add(board);
        chessFrame.setVisible(true);

        // Initial draw
        board.draw();
    } 
}
