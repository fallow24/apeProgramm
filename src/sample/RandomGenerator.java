package sample;

import java.util.Random;

public class RandomGenerator {
    private static String alphabet;
    private static int alphabetLength;
    private static Random random = new Random();
    private int temp;

    public RandomGenerator(String alphabet) {
        this.alphabet = alphabet;
        alphabetLength = alphabet.length();
    }

    public static String getAlphabet() {
        return alphabet;
    }

    public static void setAlphabet(String s) {
        alphabet = s;
        alphabetLength = alphabet.length();
    }

    public String generateChar() {
        this.temp = random.nextInt(alphabetLength);
        return Character.toString(alphabet.charAt(this.temp));
    }
}
