package site.ani4h.api.utils;


import java.util.Random;

public class RandomSequenceGenerator {
    public static String Chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String RandomString(int length){
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(Chars.charAt(rand.nextInt(Chars.length())));
        }
        return sb.toString();
    }
}
