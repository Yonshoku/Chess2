package chessgui;

import javax.swing.*;
import java.awt.*;


public class ChessFrame{
    
    private String title = "Chess";
    private int frameWidth = 520;
    private int frameHeight = 520;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ChessFrame();
            }
        });
    }

    public ChessFrame() {
        JFrame frame = new JFrame(title);
        frame.setResizable(false);
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setContentPane(new Board(this));
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
