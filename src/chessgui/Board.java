package chessgui;

import pieces.*;

import javax.swing.*;
import javax.swing.JComponent.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import java.util.*;
import javax.imageio.*;
import java.io.*;

public class Board extends JPanel {
    
    private static Board board = new Board();
    private int boardSize = ChessFrame.getInstance().getWidth();
    private int fieldSize = boardSize / 8;
    private int pieceSize = Math.round(fieldSize * 0.8f);
    private int COLS = 8;
    private int ROWS = 8;

    private HashMap<String, Color> colors = new HashMap<String, Color>();
    private Map<Type, BufferedImage> whitePiecesImages = new HashMap<Type, BufferedImage>(); 
    private Map<Type, BufferedImage> blackPiecesImages = new HashMap<Type, BufferedImage>(); 

    private String pathToImages = "bin" + File.separator + "images" + File.separator;

    public static final Board getInstance() {
        return board;
    }

    private Board() {
        // Initialize colors 
        colors.put("boardLight", new Color(189, 174, 160));
        colors.put("boardDark", new Color(75, 66, 56));

        // Load pieces images
        loadPiecesImages();
    }

    public void fillBoardContentPane() {
        setLayout(new OverlayLayout(this)); 
        add(PawnTransformOverlay.getInstance());
    }

    // Main painting method
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        paintBoard(g);
        paintPieces(g, Position.getInstance().getLayout());
    }

    // Size of the board
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(boardSize, boardSize);
    }

    public boolean setOptimizedDrawingEnabled() {
        return false;
    }

    // Paint methods
    public void paintBoard(Graphics g) {
        int fieldX, fieldY;

        // Color light fields
        g.setColor(colors.get("boardLight"));
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j+=2) {
                fieldX = j * fieldSize + (i % 2) * fieldSize;
                fieldY = i * fieldSize;
                g.fillRect(fieldX, fieldY, fieldSize, fieldSize);
            }
        }

        // Color dark fields
        g.setColor(colors.get("boardDark"));
        for(int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j+=2) {
                fieldX = j * fieldSize + ((i + 1) % 2) * fieldSize;
                fieldY = i * fieldSize;
                g.fillRect(fieldX, fieldY, fieldSize, fieldSize);
            }
        }

    }
    
    public void paintPieces(Graphics g, Piece[][] layout) {
        // Centralize pieces
        int marginX = (fieldSize - whitePiecesImages.get(Type.BISHOP).getWidth()) / 2;
        int marginY = (fieldSize - whitePiecesImages.get(Type.BISHOP).getHeight()) / 2;

        Piece p;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                p = Position.getInstance().getPiece(i, j);

                if (p != null) {
                    if (p.isWhite()) 
                        g.drawImage(whitePiecesImages.get(p.getType()),
                            i * fieldSize + marginX, j * fieldSize + marginY, null);
                    else  
                        g.drawImage(blackPiecesImages.get(p.getType()),
                            i * fieldSize + marginX, j * fieldSize + marginY, null);
                }
            }
        }

    }

    public int getX(int x) {
        return x / fieldSize;
    }
    
    public int getY(int y) {
        return y / fieldSize;
    }

    // Image handling methods 
    public BufferedImage loadImage(String path) {
        try {
            BufferedImage bi = ImageIO.read(new File(path));

            return bi;
        } catch (IOException e) {
            e.printStackTrace(); 
            System.out.println("Couldn't access: " + new File(path).getAbsolutePath());

            return null;
        }
    }
    
    // Load images of pieces and resize them
    public void loadPiecesImages() {
        try {
            whitePiecesImages.put(Type.BISHOP, resizeImage(ImageIO.read(new File(pathToImages + "white_bishop.png")), pieceSize, pieceSize));
            whitePiecesImages.put(Type.KING, resizeImage(ImageIO.read(new File(pathToImages + "white_king.png")), pieceSize, pieceSize));
            whitePiecesImages.put(Type.KNIGHT, resizeImage(ImageIO.read(new File(pathToImages + "white_knight.png")), pieceSize, pieceSize));
            whitePiecesImages.put(Type.PAWN, resizeImage(ImageIO.read(new File(pathToImages + "white_pawn.png")), pieceSize, pieceSize));
            whitePiecesImages.put(Type.QUEEN, resizeImage(ImageIO.read(new File(pathToImages + "white_queen.png")), pieceSize, pieceSize));
            whitePiecesImages.put(Type.ROOK, resizeImage(ImageIO.read(new File(pathToImages + "white_rook.png")), pieceSize, pieceSize));

            blackPiecesImages.put(Type.BISHOP, resizeImage(ImageIO.read(new File(pathToImages + "black_bishop.png")), pieceSize, pieceSize));
            blackPiecesImages.put(Type.KING, resizeImage(ImageIO.read(new File(pathToImages + "black_king.png")), pieceSize, pieceSize));
            blackPiecesImages.put(Type.KNIGHT, resizeImage(ImageIO.read(new File(pathToImages + "black_knight.png")), pieceSize, pieceSize));
            blackPiecesImages.put(Type.PAWN, resizeImage(ImageIO.read(new File(pathToImages + "black_pawn.png")), pieceSize, pieceSize));
            blackPiecesImages.put(Type.QUEEN, resizeImage(ImageIO.read(new File(pathToImages + "black_queen.png")), pieceSize, pieceSize));
            blackPiecesImages.put(Type.ROOK, resizeImage(ImageIO.read(new File(pathToImages + "black_rook.png")), pieceSize, pieceSize));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage resizeImage(Image image, int targetWidth, int targetHeight) {
        BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(image, 0, 0, targetWidth, targetHeight, null);

        return resized;
    }

    // Getters and setters
    public int getRows() {
        return ROWS;
    }

    public int getCols() {
        return COLS;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public BufferedImage getImageOfPiece(Type type, boolean isWhite) { 
        if (isWhite) 
            return whitePiecesImages.get(type);
        else
            return blackPiecesImages.get(type); 
    }
}


