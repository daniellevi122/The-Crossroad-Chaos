package org.example;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {
    private GamePanel gp;

    public MouseHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // המיקום של הלחיצה
        int x = e.getX();
        int y = e.getY();

        if (gp.getGameState() == 0) {
            gp.checkInstructionsClick(x, y);
        }
        if (gp.getGameState() == 3) {
            gp.checkPauseClick(740, 40); // שולח לחיצה "כאילו" על הכפתור כדי להפעיל את הלוגיקה שכתבנו
            gp.music.resumeMusic();

        } else {
            if (gp.getGameState() == 1) {
                gp.checkCarClick(x, y);
                gp.checkPauseClick(x, y);
                if (gp.getGameState() == 3) {
                    gp.music.stopMusic();
                }
            }

        }

    }
}