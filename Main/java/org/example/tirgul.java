package org.example;

import org.example.User;

public class tirgul {
    public static void main(String[] args) {
        // 1. יצירת האובייקטים
        User myUser1 = new User();
        User myUser2 = new User();
        User myUser3 = new User();
        User myUser4 = new User();

        // 2. הפעלת כל אחד בנתיב ריצה (Thread) נפרד
        new Thread(() -> myUser1.sendMessage()).start();
        new Thread(() -> myUser2.sendMessage()).start();
        new Thread(() -> myUser3.sendMessage()).start();
        new Thread(() -> myUser4.sendMessage()).start();

        System.out.println("--- הצ'אט הקבוצתי התחיל! ---");
    }
}