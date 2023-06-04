package chessgui;

import local.Client;

import javax.swing.*;
import javax.swing.JFrame.*;
import java.awt.*;
import java.util.Random;

public class ChessFrame{
    private static ChessFrame chessFrame = new ChessFrame();

    private JFrame frame; 
    private String title = "Chess";
    private int frameWidth = 520;
    private int frameHeight = 520;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Init frame
                ChessFrame.getInstance();
                ChessFrame.getInstance().fillFrame();

                // Init game 
                Game.getInstance();
                Board.getInstance();
                Board.getInstance().addMouseListener(new BoardMouseHandler());

                try {
                    new Client().run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    public static final ChessFrame getInstance() {
        return chessFrame;
    }

    private ChessFrame() {
        frame = new JFrame(title); 
        frame.setResizable(false);
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);        
    }

    public void fillFrame() {
        frame.add(Board.getInstance(), BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);

    }

    public int getWidth() {
        return frameWidth; 
    }

    public int getHeight() {
        return frameHeight;
    }

}
