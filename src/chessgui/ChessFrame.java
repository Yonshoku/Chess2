package chessgui;

import javax.swing.JFrame;
import java.awt.*;
import javax.swing.JButton;

public class ChessFrame extends JFrame{

    private Board board;
    
    private String title = "Chess";
    private int frameWidth = 520;
    private int frameHeight = 520;

    // Constructor
    public ChessFrame () {
        initFrame();
    }

    private void initFrame() {
        // Init a frame
        setTitle(title);
        setSize(frameWidth, frameHeight);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addComponent(Component c) {
        add(new JButton("OK"));
        pack();
    }

}
