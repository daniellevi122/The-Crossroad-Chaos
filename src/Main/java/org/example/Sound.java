package org.example;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    private Clip clip;
    URL soundURL[] = new URL[10];
    private long pausePosition = 0; // לשמור את הזמן שהשיר התנגן של המשחק במצב 1

    public Sound() {
        // כאן אנחנו מגדירים את הנתיבים לקבצים שלך
        // שים לב: השמות צריכים להיות בדיוק כמו הקבצים בתיקיית ה-resources
        soundURL[0] = getClass().getResource("/airship.wav");      // מוזיקת התחלה מצב התחלתי 0
        soundURL[1] = getClass().getResource("/Zodik - Path Zion.wav");    // משחק
        soundURL[2] = getClass().getResource("/nasa-go.wav");    // מסך הפסד
        soundURL[3] = getClass().getResource("/459556__wangzhuokun__181106_crowd_cheer_applaud_hall (1).wav");    // מסך ניצחון
    }

    public void playMusic(int i) {
        this.setFile(i);
        this.play();
        this.loop();
    }

    public void setFile(int i) {
        try {
            // פותח את הקובץ לפי המספר שנתנו (i)
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            System.out.println("שגיאה בטעינת הקובץ מספר: " + i);
        }
    }

    public void play() {
        clip.start(); // משמיע פעם אחת
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY); // משמיע בלולאה אינסופית
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            pausePosition = clip.getMicrosecondPosition();
            clip.stop(); // עוצר את הסאונד
        }
    }
    public void resumeMusic() {
        if (clip != null && !clip.isRunning()) {
            clip.setMicrosecondPosition(pausePosition); // 1. מחזירים את הנגן לנקודה ששמרנו
            clip.start(); // 2. מפעילים מחדש פעם אחת
            clip.loop(Clip.LOOP_CONTINUOUSLY); // 3. מחזירים את הלופ האינסופי של המשחק
        }
    }
}
