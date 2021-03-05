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

    private boolean isOverlayShowed = false;
    private boolean isWhite;
    private int overlayWidth = boardSize;
    private int overlayHeight = boardSize / 5;
    private int overlayMarginY = (boardSize - overlayHeight) / 2;
    private int overlayImageSize = Math.round(overlayHeight * 0.9f);
    private int overlayMarginX = (overlayWidth - overlayImageSize * 4) / 2;

    private int activeFieldX = -1, activeFieldY = -1;

    public static final Board getInstance() {
        return board;
    }

    private Board() {
        // Initialize colors 
        colors.put("boardLight", new Color(189, 174, 160));
        colors.put("boardDark", new Color(75, 66, 56));
        colors.put("overlayDark", new Color(0, 0, 0));
        colors.put("overlayLight", new Color(255, 255, 255));
        colors.put("notationLight", new Color(215, 215, 215));
        colors.put("notationDark", new Color(10, 10, 10));
        colors.put("activeField", new Color(156, 219, 146));

        // Load pieces images
        loadPiecesImages();
    }

    // Main painting method
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        paintBoard(g);
        paintNotation(g);
        paintActiveField(g);
        paintPieces(g, Position.getInstance().getLayout());

        if (isOverlayShowed) 
            paintOverlay(g);
    }

    // Size of the board
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(boardSize, boardSize);
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

    public void paintOverlay(Graphics g) {
        if (isWhite) {
            g.setColor(colors.get("overlayDark"));
        } else {
            g.setColor(colors.get("overlayLight"));
        }

        g.fillRect(0, overlayMarginY, overlayWidth, overlayHeight);
        
        int marginY = overlayMarginY + (overlayHeight - overlayImageSize) / 2;

        if (isWhite) {
            g.drawImage(resizeImage(whitePiecesImages.get(Type.BISHOP), overlayImageSize, overlayImageSize), overlayMarginX, marginY, null);
            g.drawImage(resizeImage(whitePiecesImages.get(Type.KNIGHT), overlayImageSize, overlayImageSize), overlayMarginX + overlayImageSize, marginY, null);
            g.drawImage(resizeImage(whitePiecesImages.get(Type.ROOK), overlayImageSize, overlayImageSize), overlayMarginX + overlayImageSize * 2, marginY, null);
            g.drawImage(resizeImage(whitePiecesImages.get(Type.QUEEN), overlayImageSize, overlayImageSize), overlayMarginX + overlayImageSize * 3, marginY, null);
        } else {
            g.drawImage(resizeImage(blackPiecesImages.get(Type.BISHOP), overlayImageSize, overlayImageSize), overlayMarginX, marginY, null);
            g.drawImage(resizeImage(blackPiecesImages.get(Type.KNIGHT), overlayImageSize, overlayImageSize), overlayMarginX + overlayImageSize, marginY, null);
            g.drawImage(resizeImage(blackPiecesImages.get(Type.ROOK), overlayImageSize, overlayImageSize), overlayMarginX + overlayImageSize * 2, marginY, null);
            g.drawImage(resizeImage(blackPiecesImages.get(Type.QUEEN), overlayImageSize, overlayImageSize), overlayMarginX + overlayImageSize * 3, marginY, null);
        }
    }

    public Piece getOverlayPiece(int x, int y) {
        if (y > overlayMarginY && y < overlayMarginY + overlayHeight) {

            if (x > overlayMarginX && x < overlayMarginX + overlayImageSize) {
                return new Bishop(isWhite);
            } else if (x > overlayMarginX + overlayImageSize && x < overlayMarginX + overlayImageSize * 2) {
                return new Knight(isWhite);
            } else if (x > overlayMarginX + overlayImageSize * 2 && x < overlayMarginX + overlayImageSize * 3) {
                return new Rook (isWhite);
            } else if (x > overlayMarginX + overlayImageSize * 3 && x < overlayMarginX + overlayImageSize * 4) {
                return new Queen(isWhite);
            }
                 
        }

        return null;
    }

    public void paintNotation(Graphics g) {
        int fontSize = 16;
        int marginX = 4;
        int marginY = fontSize + marginX - 4;

        Font font = new Font("SansSerif", Font.BOLD, fontSize);
        g.setFont(font);

        for (int i = 0; i < ROWS; i++) {
            if ((i + 2) % 2 == 0) {
                g.setColor(colors.get("notationDark"));
            } else {
                g.setColor(colors.get("notationLight"));
            }

            g.drawString(String.valueOf(i + 1), marginX, fieldSize * i + marginY);     
        }
        
        marginY = (fieldSize * ROWS) - fontSize + 8;
        marginX = fieldSize - fontSize + 4;

        for (int i = 0; i < COLS; i++) {
            if ((i + 2) % 2 == 0) {
                g.setColor(colors.get("notationLight"));
            } else {
                g.setColor(colors.get("notationDark"));
            }

            g.drawString(String.valueOf((char)(97 + i)), marginX + fieldSize * i, marginY);     
        }
    }

    public void paintActiveField(Graphics g) {
        if (activeFieldX > 0) { 
            g.setColor(colors.get("activeField"));
            g.fillRect(fieldSize * activeFieldX, fieldSize * activeFieldY, fieldSize, fieldSize); 
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

    public void setOverlayShowed(boolean isOverlayShowed) {
        this.isOverlayShowed = isOverlayShowed;
    }

    public boolean isOverlayShowed() {
        return isOverlayShowed;
    }

    public void setOverlayColor(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public void setActiveField(int x, int y) {
        this.activeFieldX = x;
        this.activeFieldY = y;
    }

}


