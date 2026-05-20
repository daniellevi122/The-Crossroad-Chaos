package org.example;

import java.awt.*;
import java.util.Random;

public class CarManager {
    private Car[] cars = new Car[10];
    private long lastSpawnTime = 0;
    private long spawnInterval = 4000;
    private int score;
    private GamePanel gp;

    public CarManager(GamePanel gp) {
        this.gp = gp;
    }

    public int getScore() {
        return score;
    }

    public void update() {
        long currentTime = System.currentTimeMillis(); // השעה עכשיו
        // אם עברו מספר שניות מהפעם האחרונה
        if (currentTime - lastSpawnTime >= spawnInterval) {
            spawnCar(); // צור מכונית חדשה
            lastSpawnTime = currentTime; // תעדכן את זמן היצירה האחרון
            if (spawnInterval > 1200) {
                spawnInterval -= 300;
            }
        }

        for (int i = 0; i < cars.length; i++) {
            if (cars[i] != null) {
                if (cars[i].getX() < -100 || cars[i].getX() > 865 ||
                        cars[i].getY() < -100 || cars[i].getY() > 865) {
                    this.score +=50;
                    cars[i] = null;
                }
            }

        }
    }


    private void spawnCar() {
        Random random = new Random();
        int waveSize = random.nextInt(2, 5);
        int spawnedThisWave = 0;

        for (int i = 0; i < cars.length; i++) {
            // מחפשים מקום פנוי במערך למכונית החדשה
            if (cars[i] == null && spawnedThisWave < waveSize) {

                int randomDirection = random.nextInt(4);

                boolean locationOccupied = false;

                // אנחנו עוברים על כל המכוניות שכבר קיימות במערך
                for (int j = 0; j < cars.length; j++) {
                    if (cars[j] != null) {
                        // אם למכונית קיימת יש את אותו כיוון כמו זה שהגרלנו עכשיו
                        if (cars[j].getDirection() == randomDirection) {
                            // ובנוסף היא עדיין קרובה מאוד לנקודת ההתחלה (פחות מ-150 פיקסלים)
                            if (isAtStart(cars[j])) {
                                locationOccupied = true; // המקום תפוס!
                            }
                        }
                    }
                }

                // רק אם המקום פנוי, ניצור את המכונית
                if (!locationOccupied) {
                    cars[i] = new Car(randomDirection, 1.5);
                    new Thread(cars[i]).start();
                    spawnedThisWave++;
                }
            }
        }
    }

    // פונקציית עזר קטנה לבדוק אם רכב נמצא ב"אזור הלידה" שלו
    private boolean isAtStart(Car c) {
        int distFromStart = 150;
        if (c.getDirection() == 0 && c.getX() < distFromStart) return true;
        if (c.getDirection() == 1 && c.getX() > 800 - distFromStart) return true;
        if (c.getDirection() == 2 && c.getY() < distFromStart) return true;
        if (c.getDirection() == 3 && c.getY() > 800 - distFromStart) return true;
        return false;
    }

    //     * מצייר את כל המכוניות שקיימות כרגע במערך.
    public void draw(Graphics g) {
        for (Car c : cars) {
            if (c != null) { // מציירים רק אם יש מכונית בתא הזה
                if (c.getImage() != null) {
                    g.drawImage(c.getImage(), c.getX(), c.getY(), c.getWidth(), c.getHeight(), null);
                } else {
                    g.setColor(Color.red);
                    g.fillRect(c.getX(), c.getY(), 30, 30);
                }
                // בתוך הלולאה שמציירת מכוניות
//                Rectangle r = c.getBounds();
//                g.setColor(Color.GREEN);
//                g.drawRect(r.x, r.y, r.width, r.height); // מצייר מסגרת ירוקה מסביב ל-Hitbox
            }
        }
    }

    // מאפשר ל-GamePanel לגשת למכוניות (בשביל זיהוי לחיצה של עכבר)
    public Car[] getCars() {
        return cars;
    }

    public boolean checkCollisions() {
        // עוברים על כל המכוניות במערך
        for (int i = 0; i < cars.length; i++) {
            for (int j = i + 1; j < cars.length; j++) {
                // בודקים ששני התאים לא ריקים ושהם לא אותו רכב בדיוק
                if (cars[i] != null && cars[j] != null) {

                    // הפקודה intersects בודקת אם המלבנים חופפים (התנגשות!)
                    if (cars[i].getBounds().intersects(cars[j].getBounds())) {
                        System.out.println("בום! התנגשות!");
                        gp.updateHighScore();
                        return true; // נמצאה התנגשות
                    }
                }
            }
        }
        return false; // אין התנגשויות
    }

    public void reset() {
        for (int i = 0; i < cars.length; i++) {
            cars[i] = null;
        }
        score=0;
        lastSpawnTime = 0;
    }
}