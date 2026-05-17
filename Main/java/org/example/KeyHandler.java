package org.example;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_SPACE) {


            if (gp.getGameState() == 0) {

                gp.setGameState(1);
                gp.music.playMusic(1);
                gp.startMusic.stopMusic();

            } else if (gp.getGameState() == 2) {
                gp.winMusic.stopMusic();
                gp.loseMusic.stopMusic();
                gp.music.stopMusic();

                gp.carM.reset();
                gp.setGameState(1);
                gp.music.playMusic(1);

            }
        }

    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
