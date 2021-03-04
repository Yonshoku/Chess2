package chessgui;

import pieces.*;

import javax.swing.*;
import javax.swing.JComponent.*;
import java.awt.*;

public class PawnTransformOverlay extends JPanel{
    private static PawnTransformOverlay pto = new PawnTransformOverlay();
    
    private Board board = Board.getInstance();
    private int width = board.getBoardSize();
    private int height = board.getBoardSize() / 5;

    private boolean isWhite;
    private int imagesNum = 4;
    private int marginX = 0; 
    private int imageSize = Math.round(height * 0.9f);
    private int marginY = Math.round(height * 0.05f); 

    public static final PawnTransformOverlay getInstance() {
        return pto;
    } 

    private PawnTransformOverlay() {
        super(new BorderLayout());
        addMouseListener(new PawnTransformOverlayMouseHandler()); 

        setAlignmentX(0.5f);
        setAlignmentY(0.5f); 
        setVisible(false);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(width, height);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(width, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintPieces(g);
    }

    public void showOverlay(boolean isWhite) {
        this.isWhite = isWhite;
        if(isWhite)
            setBackground(Color.black);
        else
            setBackground(Color.white);
         
        repaint();
        setVisible(true);
    }

    public void paintPieces(Graphics g) {
        if (imageSize > width) {
            imageSize = width / imagesNum;
            marginY = 0;
        } else {
            marginX = (width - imageSize * imagesNum) / 2;
        }
        
        g.drawImage(board.resizeImage(board.getImageOfPiece(Type.BISHOP, isWhite), imageSize, imageSize), marginX, marginY, null);
        g.drawImage(board.resizeImage(board.getImageOfPiece(Type.KNIGHT, isWhite), imageSize, imageSize), marginX + imageSize, marginY, null);
        g.drawImage(board.resizeImage(board.getImageOfPiece(Type.ROOK, isWhite), imageSize, imageSize), marginX + imageSize * 2, marginY, null);
        g.drawImage(board.resizeImage(board.getImageOfPiece(Type.QUEEN, isWhite), imageSize, imageSize), marginX + imageSize * 3, marginY, null);
    }

    public Piece getPiece(int x, int y) {
        if (y > marginY && y < marginY + imageSize) {
            if (x > marginX && x < marginX + imageSize) {
                return new Bishop(isWhite);
            } else if (x > marginX + imageSize && x < marginX + imageSize * 2) {
                return new Knight(isWhite);
            } else if (x > marginX + imageSize * 2 && x < marginX + imageSize * 3) {
                return new Rook(isWhite);
            } else if (x > marginX + imageSize * 3 && x < marginX + imageSize * 4) {
                return new Queen(isWhite);
            }
        }

        return null;
    }

}

