package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {
    private int gameState = 0;
    private boolean isPaused = false;
    private final int screenWidth = GameWindow.screen_Width;
    private final int screenHeight = GameWindow.screen_Height;
    Thread gameThread;
    private BufferedImage backgroundImage;
    private Image imageGameOver;
    private Image titleGif;
    CarManager carM = new CarManager(this);
    MouseHandler mouseH = new MouseHandler(this);
    KeyHandler keyHandler = new KeyHandler(this);
    int biggestScore;
    int currentScore;
    private Image winGif;
    private Image loseGif;
    Sound music = new Sound();
    Sound startMusic = new Sound();
    Sound loseMusic = new Sound();
    Sound winMusic = new Sound();
    private boolean isNewRecord=false;


    public GamePanel() {
        this.addMouseListener(mouseH);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.setSize(screenWidth, screenHeight);
        this.setBackground(Color.black);
        startMusic.playMusic(0);
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/map3.png"));
        } catch (Exception e) {
            System.out.println("לא הצלחתי למצוא את המפה! תוודא שהשם נכון");
        }
        try {
            titleGif = new ImageIcon(getClass().getResource("/giphy5).gif")).getImage();
        } catch (Exception e) {
            System.out.println("לא הצלחתי למצוא את ה-GIF!");
        }
        //מצב משחק הפסד
        winGif = new ImageIcon(getClass().getResource("/Happy Dance GIF.gif")).getImage();
        loseGif = new ImageIcon(getClass().getResource("/giphy (3).gif")).getImage();

    }


    public void drawTitleScreen(Graphics g) {
        g.fillRect(0, 0, screenWidth, screenHeight);
        g.setColor(Color.red);
        if (titleGif != null) {
            g.drawImage(titleGif, 0, 0, screenWidth, screenHeight, this);
        }
        //כפתור הוראות
        instructionButton(g);
        g.setFont(new Font("Impact", Font.BOLD, 60));
        g.setColor(Color.BLACK);
        g.drawString("The Crossroad Chaos", 143, 203); // הצל
        g.setColor(Color.cyan);
        g.drawString("The Crossroad Chaos", 140, 200); // הכותרת

        g.setColor(Color.white);
        g.setFont(new Font("Monospaced", Font.BOLD, 25));
        g.drawString("הוראות: לחץ על המכוניות כדי לעצור אותן", 100, 350);
        g.drawString("לחץ על הרווח כדי להתחיל", 230, 450);
        g.setColor(Color.YELLOW);
        g.drawString("והימנע מהתנגשות!!!", 260, 400);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // מנקה את המסך (צובע הכל בירוק של הדשא)
        if (this.gameState == 0) {
            drawTitleScreen(g);
        } else if (this.gameState == 1 || this.gameState == 3) {
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, null);
            }
            if (gameState == 3) {
                g.setColor(new Color(0, 0, 0, 200));
                g.fillRect(0, 0, screenWidth, screenHeight);
                g.setFont(new Font("Impact", Font.BOLD, 100));
                g.setColor(Color.WHITE);
                g.drawString("PAUSED", 240, 350);
                g.setFont(new Font("Arial", Font.BOLD, 30));
                g.setColor(Color.YELLOW);
                g.drawString("לחץ על העכבר כדי להמשיך", 230, 450);

            }
            if (gameState == 1) {
                drawPauseButton(g);
            }
            carM.draw(g);
            g.setColor(Color.red);
            g.setFont(new Font("Impact", Font.BOLD, 30));
            g.drawString("score:" + carM.getScore(), 20, 50);


        } else if (gameState == 2) {
            if (winGif != null && loseGif != null) {
                if (isNewRecord && biggestScore > 0) {
                    g.drawImage(winGif, 0, 0, screenWidth - 10, screenHeight - 30, this);
                    float hue = (System.currentTimeMillis() % 1500) / 1500f; // מתחלף חלק כל שנייה וחצי
                    Color rainbowColor = Color.getHSBColor(hue, 1.0f, 1.0f);
                    g.setFont(new Font("Impact", Font.ITALIC, 50));
                    // צל שחור קטן
                    g.setColor(Color.BLACK);
                    g.drawString("NEW HIGH SCORE!!!", 193, 203);
                    g.setColor(rainbowColor);
                    g.drawString("NEW HIGH SCORE!!!", 190, 200);


                    // מלבן שחור
                    g.setColor(new Color(0, 0, 0, 160));
                    g.fillRect(160, 280, 480, 80);

                    g.setFont(new Font("Verdana", Font.BOLD, 35));
                    g.setColor(Color.YELLOW);
                    g.drawString("BEST SCORE: " + this.currentScore, 210, 335);

                    g.setFont(new Font("Verdana", Font.BOLD, 25));
                    g.setColor(Color.WHITE);
                    g.drawString("Congratulations, you set a new record!", 140, 430);

                } else {
                    g.drawImage(loseGif, 0, 0, screenWidth, screenHeight, this);

                    // 2. כותרת הפסד גדולה וקשוחה באדום חזק עם צל
                    g.setFont(new Font("Impact", Font.BOLD, 70));
                    g.setColor(Color.BLACK);
                    g.drawString("GAME OVER", 243, 243); // צל
                    g.setColor(new Color(220, 0, 0)); // אדום עמוק
                    g.drawString("GAME OVER", 240, 240);

                    // 3. מלבן רקע כהה קטן כדי שהתוצאות של הלוזר ייקראו פיקס מעל ה-GIF
                    g.setColor(new Color(0, 0, 0, 180));
                    g.fillRect(180, 310, 440, 140);

                    g.setFont(new Font("Verdana", Font.BOLD, 26));
                    g.setColor(Color.ORANGE);
                    g.drawString("BEST SCORE: " + this.biggestScore, 220, 360);

                    g.setFont(new Font("Verdana", Font.BOLD, 25));
                    g.setColor(Color.WHITE);
                    g.drawString("Your Score: " + this.currentScore, 220, 415);
                }
            }
            g.setFont(new Font("Arial", Font.BOLD, 26));
            g.setColor(new Color(50, 255, 50));
            g.drawString("Press SPACE to play again", 240, 720);
        }
    }

    public void updateHighScore() {
        this.currentScore = carM.getScore();
        if (this.currentScore > this.biggestScore) {
            this.biggestScore = this.currentScore;
            isNewRecord=true;
            winMusic.setFile(3);
            winMusic.play();
        }else {
            isNewRecord=false;
            loseMusic.setFile(2);
            loseMusic.play();
        }
    }


    private void drawPauseButton(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(700, 20, 80, 40);
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        String text = isPaused ? "המשך" : "עצור";
        g.drawString(text, 720, 45);
    }


    public void checkPauseClick(int mouseX, int mouseY) {
        if (mouseX >= 700 && mouseX <= 780 && mouseY >= 20 && mouseY <= 60) {

            if (gameState == 1) {
                isPaused = true;
                gameState = 3;    // עובר למסך עצירה

                // עוצרים את כל המכוניות, אבל זוכרים מי באמת נסעה!
                for (Car c : carM.getCars()) {
                    if (c != null) {
                        // שומרים את המצב הנוכחי של המכונית (האם היא נסעה או הייתה עצורה ביד)
                        c.setWasMovingBeforePause(c.isMoving());

                        // אם היא נסעה, נעצור אותה עכשיו בשביל ה-Pause
                        if (c.isMoving()) {
                            c.setMoving();
                        }
                    }
                }
            } else if (gameState == 3) {
                isPaused = false;
                gameState = 1;
                for (Car c : carM.getCars()) {
                    if (c != null) {
                        // אם המכונית הייתה אמורה לנסוע ועכשיו היא עצורה -> נפעיל אותה
                        if (c.isWasMovingBeforePause() && !c.isMoving()) {
                            c.setMoving();
                        }
                    }
                }
            }
        }
    }


    @Override
    public void run() {
        while (gameThread != null) {
            if (gameState == 1 && !isPaused) {

                carM.update();
                if (carM.checkCollisions()) {
                    gameState = 2;
                    music.stopMusic();
                }
            }
            // פקודה שאומרת ל-Java לקרוא ל-paintComponent
            repaint();

            try {
                // מרעננים את המסך כל 16 מילי-שניות (שזה בערך 60 פעמים בשנייה)
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this); // הפאנל אומר: "אני רוצה לרוץ כתהליכון"
        gameThread.start();            // זה מפעיל את מתודת ה-run למטה
    }

    private void instructionButton(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(300, 500, 200, 50); // מיקום הכפתור (X=300, Y=500)
        g.setColor(Color.BLACK);
        g.drawRect(300, 500, 200, 50); // מסגרת לכפתור
        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.drawString("הוראות", 360, 535); // טקסט בתוך הכפתור
    }

    public void checkInstructionsClick(int mouseX, int mouseY) {
        if (gameState == 0) {
            // בודקים אם הקואורדינטות של העכבר נמצאות בתוך המלבן שציירנו (300, 500, 200, 50)
            if (mouseX >= 300 && mouseX <= 500 && mouseY >= 500 && mouseY <= 550) {
                new InstructionsWindow((JFrame) this.getTopLevelAncestor());
            }
        }
    }


    public void checkCarClick(int mouseX, int mouseY) {
        Car[] currentCars = carM.getCars();
        // לוקחים את המערך מהמנהל ועוברים עליו
        if (gameState == 1 || gameState == 3) {
            for (int i = 0; i < currentCars.length; i++) {
                Car c = currentCars[i];
                if (c != null) { // בודקים אם יש מכונית בתא הזה
                    if (mouseX >= c.getX() && mouseX <= c.getX() + c.getWidth() &&
                            mouseY >= c.getY() && mouseY <= c.getY() + c.getHeight()) {

                        c.setMoving(); // עוצר או מפעיל את המכונית שנלחצה
                        System.out.println("פגעת במכונית!");
                        break; // מצאנו פגיעה אחת, אפשר לעצור את הלולאה
                    }
                }
            }
        }
    }

    public int getGameState() {
        return this.gameState;
    }

    public void setGameState(int x) {
        this.gameState = x;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isNewRecord() {
        return isNewRecord;
    }

    public void setNewRecord(boolean newRecord) {
        isNewRecord = newRecord;
    }
}
