package chessgui;

import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import java.util.*;
import javax.imageio.*;
import java.io.*;

public class Board extends JPanel {

    private int boardSize = 520;
    private int fieldSize = boardSize / 8;
    private int pieceSize = Math.round(fieldSize * 0.8f);
    private int COLS = 8;
    private int ROWS = 8;
    private ChessFrame frame;

    private HashMap<String, Color> colors = new HashMap<String, Color>();
    private Map<Type, BufferedImage> whitePiecesImages = new HashMap<Type, BufferedImage>(); 
    private Map<Type, BufferedImage> blackPiecesImages = new HashMap<Type, BufferedImage>(); 
    private java.util.List<Piece> whitePieces = new ArrayList<Piece>();
    private java.util.List<Piece> blackPieces = new ArrayList<Piece>();

    private String pathToImages = "bin" + File.separator + "images" + File.separator;

    // Black are at the bottom and white at the top
    boolean isPositionReversed = new Random().nextInt(2) > 0 ? true : false; 
    boolean isWhiteMoves = true;
    Piece activePiece = null;

    // Constructor
    public Board(ChessFrame frame) {
        addMouseListener(new Handler(this));
        this.frame = frame;

        // Initialize variables 
        colors.put("boardLight", new Color(189, 174, 160));
        colors.put("boardDark", new Color(75, 66, 56));

        // Load pieces images
        loadPiecesImages();

        // Init position
        initPosition();
        if (isPositionReversed) 
            reversePosition();
    }

    // Position initialization
    public void initPosition() {
        whitePieces.add(new Rook(0, 7, true, this));
        whitePieces.add(new Knight(1, 7, true, this));
        whitePieces.add(new Bishop(2, 7, true, this));
        whitePieces.add(new Queen(3, 7, true, this));
        whitePieces.add(new King(4, 7, true, this));
        whitePieces.add(new Bishop(5, 7, true, this));
        whitePieces.add(new Knight(6, 7, true, this));
        whitePieces.add(new Rook(7, 7, true, this)); 

        whitePieces.add(new Pawn(0, 6, true, this));
        whitePieces.add(new Pawn(1, 6, true, this));
        whitePieces.add(new Pawn(2, 6, true, this));
        whitePieces.add(new Pawn(3, 6, true, this));
        whitePieces.add(new Pawn(4, 6, true, this));
        whitePieces.add(new Pawn(5, 6, true, this));
        whitePieces.add(new Pawn(6, 6, true, this));
        whitePieces.add(new Pawn(7, 6, true, this));

        blackPieces.add(new Rook(0, 0, false, this)); 
        blackPieces.add(new Knight(1, 0, false, this)); 
        blackPieces.add(new Bishop(2, 0, false, this)); 
        blackPieces.add(new Queen(3, 0, false, this)); 
        blackPieces.add(new King(4, 0, false, this)); 
        blackPieces.add(new Bishop(5, 0, false, this)); 
        blackPieces.add(new Knight(6, 0, false, this)); 
        blackPieces.add(new Rook(7, 0, false, this)); 

        blackPieces.add(new Pawn(0, 1, false, this)); 
        blackPieces.add(new Pawn(1, 1, false, this)); 
        blackPieces.add(new Pawn(2, 1, false, this)); 
        blackPieces.add(new Pawn(3, 1, false, this)); 
        blackPieces.add(new Pawn(4, 1, false, this)); 
        blackPieces.add(new Pawn(5, 1, false, this)); 
        blackPieces.add(new Pawn(6, 1, false, this)); 
        blackPieces.add(new Pawn(7, 1, false, this)); 
    }

    public void reversePosition() {
        for (Piece p : whitePieces) {
            p.setX(COLS - 1 - p.getX());
            p.setY(ROWS - 1 - p.getY());
        }

        for (Piece p : blackPieces) {
            p.setX(COLS - 1 - p.getX());
            p.setY(ROWS - 1 - p.getY());
        }
    }
   

    // Main painting method
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        paintBoard(g);
        paintPieces(g);
    }

    // Size of the board
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(frame.getWidth(), frame.getHeight());
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
    
    public void paintPieces(Graphics g) {
        // align pieces center of field
        int pieceMargin = (fieldSize - pieceSize) / 2;

        for (Piece piece : whitePieces) {
            g.drawImage(whitePiecesImages.get(piece.getType()), getXCoord(piece.getX()) + pieceMargin, getYCoord(piece.getY()) + pieceMargin, null);
        }

        for (Piece piece : blackPieces) {
            g.drawImage(blackPiecesImages.get(piece.getType()), getXCoord(piece.getX()) + pieceMargin, getYCoord(piece.getY()) + pieceMargin, null);
        }
    }

    public int getXCoord(int x) {
        return fieldSize * x;
    }

    public int getYCoord(int y) {
        return fieldSize * y;
    } 

    public int getX(int xCoord) {
        return xCoord / fieldSize;
    }

    public int getY(int yCoord) {
        return yCoord / fieldSize; 
    }

    public Piece getPiece(int x, int y) {
        for (Piece p : whitePieces) {
            if (p.getX() == x && p.getY() == y) 
                return p;
        }

        for (Piece p : blackPieces) {
            if (p.getX() == x && p.getY() == y) 
                return p;
        }

        return null;
    }

    public void deletePiece(int x, int y) {
        Iterator<Piece> it = whitePieces.iterator();
        Piece p;
        while (it.hasNext()) {
            p = it.next();
            if (p.getX() == x && p.getY() == y)
                it.remove();
        }

        it = blackPieces.iterator();
        while (it.hasNext()) {
            p = it.next();
            if (p.getX() == x && p.getY() == y)
                it.remove();
        }
    }

    public Piece getActivePiece() {
        return activePiece;
    }

    public void setActivePiece(Piece activePiece) {
        this.activePiece = activePiece;
    }

    public boolean isPositionReversed() {
        return isPositionReversed;
    }

    public void changeTurn() {
        isWhiteMoves = !isWhiteMoves;
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
    
}


