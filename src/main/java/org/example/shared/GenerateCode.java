package org.example.shared;

import java.util.Random;

public class GenerateCode {
    public static String generate(int end) {
        String candidates = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        Random random = new Random();
        StringBuilder result = new StringBuilder(end);
        for (int i=0; i<end; i++) {
            int index = random.nextInt(61);
            result.append(candidates.charAt(index));
        }
        return result.toString();
    }
}
