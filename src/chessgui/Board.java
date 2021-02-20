package chessgui;

import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import java.util.*;
import javax.imageio.*;
import java.io.*;

public class Board extends JPanel implements MouseListener{

    private int boardSize = 520;
    private int fieldSize = boardSize / 8;
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
    boolean isPositionReversed = false;
    boolean isWhiteMoves = true;

    // Constructor
    public Board(ChessFrame frame) {
        addMouseListener(this);
        this.frame = frame;

        // Initialize variables 
        colors.put("boardLight", new Color(189, 174, 160));
        colors.put("boardDark", new Color(75, 66, 56));

        // Load pieces images
        loadPiecesImages();

        // Init position
        initPosition();
    }

    // Position initialization
    public void initPosition() {
        whitePieces.add(new Bishop(2, 7, true));
        whitePieces.add(new Rook(0, 7, true)); 

        blackPieces.add(new Bishop(2, 0, false)); 
        blackPieces.add(new Rook(0, 0, false)); 
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
        for (Piece piece : whitePieces) {
            g.drawImage(whitePiecesImages.get(piece.getType()), getXCoord(piece.getX()), getYCoord(piece.getY()), null);
            System.out.println(piece.isWhite()); // prints true for both white pieces
        }

        for (Piece piece : whitePieces) {
            g.drawImage(blackPiecesImages.get(piece.getType()), getXCoord(piece.getX()), getYCoord(piece.getY()), null);
            System.out.println(piece.isWhite()); // prints true for both black pieces (should false, its black)
        }
    }

    public int getXCoord(int x) {
        return fieldSize * x;
    }

    public int getYCoord(int y) {
        return fieldSize * y;
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
    
    // Load images of pieces
    public void loadPiecesImages() {
        try {
            whitePiecesImages.put(Type.BISHOP, ImageIO.read(new File(pathToImages + "white_bishop.png")));
            whitePiecesImages.put(Type.KING, ImageIO.read(new File(pathToImages + "white_king.png")));
            whitePiecesImages.put(Type.KNIGHT, ImageIO.read(new File(pathToImages + "white_knight.png")));
            whitePiecesImages.put(Type.PAWN, ImageIO.read(new File(pathToImages + "white_pawn.png")));
            whitePiecesImages.put(Type.QUEEN, ImageIO.read(new File(pathToImages + "white_queen.png")));
            whitePiecesImages.put(Type.ROOK, ImageIO.read(new File(pathToImages + "white_rook.png")));

            blackPiecesImages.put(Type.BISHOP, ImageIO.read(new File(pathToImages + "black_bishop.png")));
            blackPiecesImages.put(Type.KING, ImageIO.read(new File(pathToImages + "black_king.png")));
            blackPiecesImages.put(Type.KNIGHT, ImageIO.read(new File(pathToImages + "black_knight.png")));
            blackPiecesImages.put(Type.PAWN, ImageIO.read(new File(pathToImages + "black_pawn.png")));
            blackPiecesImages.put(Type.QUEEN, ImageIO.read(new File(pathToImages + "black_queen.png")));
            blackPiecesImages.put(Type.ROOK, ImageIO.read(new File(pathToImages + "black_rook.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Mouse events handling 
    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e.getX());
        System.out.println(e.getY());
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override 
    public void mouseClicked(MouseEvent e) {
    } 

    @Override 
    public void mouseReleased(MouseEvent e) {
    }
    
}


