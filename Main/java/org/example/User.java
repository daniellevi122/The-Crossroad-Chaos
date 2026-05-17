package org.example;

import com.github.javafaker.Faker;

import java.util.Random;

public class User {
    private String name;
    private boolean bot;
    private int massagesCont;

    public User() {
        Faker faker = new Faker();
        this.name = faker.name().fullName();
        this.bot = false;
        this.massagesCont = new Random().nextInt(10, 15);
    }

    public void sendMessage() {
        while (this.massagesCont > 0) {
            if (this.bot != false) {

            } else {
                System.out.print(this.name + ": ");
                if (this.massagesCont == 1) {
                    typeMessage("Gotta go,bye",this.name);
                } else {
                    Faker faker = new Faker();
                    typeMessage(faker.chuckNorris().fact(),this.name);
                }
            }
            this.massagesCont--;
        }
    }

    public synchronized void typeMessage(String sentence,String name) {
        System.out.print(name + ": ");
        for (int i = 0; i < sentence.length(); i++) {
            System.out.print(sentence.charAt(i));
            try {
                Thread.sleep(new Random().nextInt(10, 100));
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }
        System.out.println();
    }

}
