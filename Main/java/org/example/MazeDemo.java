import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MazeDemo extends JPanel {
    private int playerX = 50, playerY = 50;
    private final int PLAYER_SIZE = 20;
    private boolean gameOver = false;

    // רשימה של אויבים
    private ArrayList<Enemy> enemies = new ArrayList<>();

    public MazeDemo() {
        JFrame frame = new JFrame("The Maze Runner - התנגשויות ואויבים");
        frame.add(this);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // יצירת אויבים והפעלתם ב-Threads נפרדים
        enemies.add(new Enemy(200, 200));
        enemies.add(new Enemy(400, 100));

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameOver) return; // אם הפסדנו, אי אפשר לזוז

                int key = e.getKeyCode();
                if (key == KeyEvent.VK_UP) playerY -= 10;
                if (key == KeyEvent.VK_DOWN) playerY += 10;
                if (key == KeyEvent.VK_LEFT) playerX -= 10;
                if (key == KeyEvent.VK_RIGHT) playerX += 10;

                checkCollisions(); // בדיקת התנגשות אחרי כל תנועה
                repaint();
            }
        });
    }

    private void checkCollisions() {
        for (Enemy e : enemies) {
            // חישוב מרחק בסיסי (במקום Rectangle.intersects בשביל הפשטות כרגע)
            if (Math.abs(playerX - e.x) < 20 && Math.abs(playerY - e.y) < 20) {
                gameOver = true;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER!", 150, 300);
            return;
        }

        // ציור השחקן
        g.setColor(Color.BLUE);
        g.fillRect(playerX, playerY, PLAYER_SIZE, PLAYER_SIZE);

        // ציור האויבים
        g.setColor(Color.RED);
        for (Enemy e : enemies) {
            g.fillOval(e.x, e.y, 20, 20);
        }
    }

    // מחלקה לאויב שזז ב-Thread נפרד (OOP בסיסי)
    class Enemy implements Runnable {
        int x, y;
        int direction = 1;

        public Enemy(int x, int y) {
            this.x = x;
            this.y = y;
            new Thread(this).start(); // הפעלת ה-Thread של האויב
        }

        @Override
        public void run() {
            while (!gameOver) {
                y += direction * 5; // תנועה למעלה ולמטה
                if (y > 500 || y < 50) direction *= -1; // שינוי כיוון בקירות

                checkCollisions(); // האויב בודק אם הוא פגע בשחקן בזמן התנועה
                repaint();

                try {
                    Thread.sleep(50); // מהירות התנועה
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }

    public static void main(String[] args) {
        new MazeDemo();
    }
}