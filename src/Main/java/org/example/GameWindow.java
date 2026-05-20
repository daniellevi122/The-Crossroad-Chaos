package org.example;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public static final int screen_Width = 800;
    public static final int screen_Height = 800;
    public GameWindow(){
        this.setTitle("The Crossroad Chaos");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        //יצירת הפאנל והוספתו
        GamePanel gamePanel = new GamePanel();
        this.add(gamePanel);
        this.pack();
        this.setSize(screen_Width,screen_Height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        gamePanel.startGameThread();
    }
}
