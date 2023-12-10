package org.example;

import java.util.Random;

public class RandomLocation {
    static String[] options = {"Atlanta", "Gotham"};
    static Random randomDestination = new Random();
    public static String getLocation(){
        return options[randomDestination.nextInt(options.length)];
    }
}
