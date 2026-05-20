package org.example;

import javax.swing.*;
import java.awt.*;

public class InstructionsWindow {

    public InstructionsWindow(JFrame topLevelAncestor) {
        // 1. יצירת החלון - JDialog נועל את המסך שמתחתיו
        JDialog dialog = new JDialog(topLevelAncestor, "הוראות", true);
        dialog.setSize(400, 400);
        dialog.setLayout(null);
        dialog.getContentPane().setBackground(Color.gray);
        dialog.setLocationRelativeTo(topLevelAncestor);
        dialog.setResizable(false);

        // 2. כותרת (JLabel)
        JLabel title = new JLabel("הוראות המשחק");
        title.setForeground(Color.black);
        title.setBounds(100, 20, 200, 40);
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setHorizontalAlignment(SwingConstants.CENTER); // מרכוז הטקסט בתוך עצמו
        dialog.add(title);

        // 3. אזור טקסט (JTextArea) - להוראות עצמן
        JTextArea area = new JTextArea();
        area.setText(
                "• המשימה:\n" +
                        "נהל את הצומת ומנע תאונות קטלניות!\n\n" +
                        "• שליטה ומכוניות:\n" +
                        "לחץ על מכונית כדי לעצור אותה,\n" +
                        "לחץ עליה שוב כדי להזניק אותה מחדש.\n\n" +
                        "• ניקוד וחוקים:\n" +
                        "כל רכב שעובר בשלום = 50 נקודות.\n" +
                        "התנגשות אחת נגמר המשחק ואנשים ימותו!"
        );
        // הגדרת מיקום ידני לטקסט
        area.setBounds(30, 80, 340, 260);
        area.setFont(new Font("Arial", Font.PLAIN, 18));
        area.setBackground(Color.gray);
        area.setEditable(false); // שהמשתמש לא יוכל למחוק את ההוראות
        area.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        dialog.add(area);

        // הצגת החלון (הוא יעצור הכל עד שיסגרו אותו)
        dialog.setVisible(true);
    }
}