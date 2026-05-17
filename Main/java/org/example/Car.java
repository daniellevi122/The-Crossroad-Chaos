package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Car implements Runnable {
    private double x;
    private int width;
    private int height;
    private double y;
    private double speed;
    private boolean moving;
    private BufferedImage image;
    // 0 = משמאל לימין, 1 = מימין לשמאל, 2 = מלמעלה למטה, 3 = מלמטה למעלה
    private int direction;
    private boolean isAlive = true;
    private boolean wasMovingBeforePause = true;

    public void setWasMovingBeforePause(boolean state) {
        this.wasMovingBeforePause = state;
    }
    public boolean isWasMovingBeforePause() {
        return this.wasMovingBeforePause;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public Car(int direction, double speed) {
        this.direction = direction;
        this.speed = speed;
        this.moving = true;

        if (direction == 0 || direction == 1) { // אופקי (ימינה/שמאלה)
            this.width = 40;
            this.height = 60;
        } else { // אנכי (למעלה/למטה)
            this.width = 60;
            this.height = 40;
        }

        switch (direction) {
            case 0: // משמאל לימינה
                this.x = -60;
                this.y = 379;
                loadImage("right");
                break;
            case 1: // מימין שמאלה
                this.x = 860;
                this.y = 332;
                loadImage("left");
                break;
            case 2: //  למעלה ללמטה
                this.x = 345;
                this.y = -60;
                loadImage("down");
                break;
            case 3: // מלמטה למעלה
                this.x = 397;
                this.y = 850;
                loadImage("up");
                break;
        }
    }

    //אם לא אכתובOverride אז המחשב יצעק עלי במקרה שאכתוב ron במקום run כי אני דורס חוזה
    @Override
    public void run() {
        while (this.isAlive) {
            if (moving) {
                double moveX = this.speed;
                switch (direction) {
                    case 0:
                        x += moveX;
                        break; //  ימינה
                    case 1:
                        x -= moveX;
                        break; //  שמאלה
                    case 2:
                        y += moveX;
                        break; // יורד
                    case 3:
                        y -= moveX;
                        break; // עולה
                }
            }
            try {
                Thread.sleep(20);
            } catch (Exception e) {
            }
        }
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving() {
        this.moving = !this.moving;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    private String getCarImage(String direction) {
        Random random = new Random();
        int randomNum = random.nextInt(4);
        if (direction.equals("up")) {
            if (randomNum == 0) {
                return "/pink car-up.png";
            }
            if (randomNum == 1) {
                return "/white car-up.png";
            }
            if (randomNum == 2) {
                return "/red raceCar-up.png";
            }
            if (randomNum == 3) {
                return "/cabriolet pink-up.png";
            }

        }
        if (direction.equals("down")) {
            if (randomNum == 0) {
                return "/pink car-down.png";
            }
            if (randomNum == 1) {
                return "/white car-down.png";
            }
            if (randomNum == 2) {
                return "/red raceCar-down.png";
            }
            if (randomNum == 3) {
                return "/cabriolet pink-down.png";
            }
        }
        if (direction.equals("right")) {
            if (randomNum == 0) {
                return "/pink car-right.png";
            }
            if (randomNum == 1) {
                return "/white car-right.png";
            }
            if (randomNum == 2) {
                return "/red raceCar-right.png";
            }
            if (randomNum == 3) {
                return "/cabriolet pink-right.png";
            }
        }
        if (direction.equals("left")) {
            if (randomNum == 0) {
                return "/pink car-left.png";
            }
            if (randomNum == 1) {
                return "/white car-left.png";
            }
            if (randomNum == 2) {
                return "/red raceCar-left.png";
            }
            if (randomNum == 3) {
                return "/cabriolet pink-left.png";
            }
        }
        return "";
    }


    private void loadImage(String direction) {
        String path = getCarImage(direction);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("שגיאה בטעינת התמונה: " + path);
        }
    }

    public Rectangle getBounds() {
        int horizontalMargin = 2;   // שוליים אופקיים
        int verticalMargin = 15;  //שוליים אנכיים

        if (direction == 0 || direction == 1) {
            return new Rectangle(
                    (int) x + horizontalMargin,
                    (int) y + verticalMargin,
                    this.width - (horizontalMargin * 2),
                    this.height - (verticalMargin * 2)
            );
        } else {
            return new Rectangle(
                    (int) x + verticalMargin,
                    (int) y + horizontalMargin,
                    this.width - (verticalMargin * 2),
                    this.height - (horizontalMargin * 2)
            );
        }

    }


    public int getX() {
        return (int) this.x;
    }

    public int getY() {
        return (int) this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getDirection() {
        return this.direction;
    }
}