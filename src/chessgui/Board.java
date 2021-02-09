package chessgui;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import java.util.HashMap;

public class Board extends Canvas implements MouseListener{
    private int boardSize = 520;
    private int fieldSize;
    private int fieldX;
    private int fieldY;
    private int COLS = 8;
    private int ROWS = 8;

    private HashMap<String, Color> colors = new HashMap<String, Color>();

    private BufferStrategy bs;
    private Graphics2D g2d;
    private Graphics g;

    public Board() {
        // Set canvas settings
        this.setPreferredSize(new Dimension(520, 520));
        this.setMinimumSize(new Dimension(520, 520));
        this.setMaximumSize(new Dimension(520, 520));
        
        this.addMouseListener(this);

        // Initialize colors
        colors.put("boardLight", new Color(225, 222, 207));
        colors.put("boardDark", new Color(102, 102, 101));

        // Initialize other variables
        fieldSize = boardSize / 8; 
    }

    public void draw() {
        if (this.getBufferStrategy() == null) this.createBufferStrategy(2);

        bs = this.getBufferStrategy();
        g = bs.getDrawGraphics();
        g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);
        g2d.fillRect(10, 10, 100, 100); 
        System.out.println(g2d);

        drawBoard();

        bs.show();
    }

    public void drawBoard() {

        // Fill light fields
        g2d.setColor(colors.get("boardLight"));
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j+=2) {
                fieldX = j * fieldSize + (i % 2);
                fieldY = i * fieldSize;
                g2d.fillRect(fieldX, fieldY, fieldSize, fieldSize);
            }
        }

        // Fill dark fields
        g2d.setColor(colors.get("boardDark"));
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j+=2) {
                fieldX = j * fieldSize + ((i + 1) % 2);
                fieldY = i * fieldSize;
                g2d.fillRect(fieldX, fieldY, fieldSize, fieldSize);
            }
        }
    }


    // MOUSE EVENTS
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


