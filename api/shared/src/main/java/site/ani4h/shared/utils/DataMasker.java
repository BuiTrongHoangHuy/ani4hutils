package site.ani4h.shared.utils;

public class DataMasker {
    public static String mask(int input ) {
        return Integer.toBinaryString(input);
    }
    public static int unmask(String input) {
        return 1;
    }
}
