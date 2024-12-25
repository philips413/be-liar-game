package psnl.liar.utility;

import java.util.Random;

public class RandomCharactor {

    private static final int LENGTH = 8;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[]{}|;:,.<>?/";

    public static String creator() {

        Random random = new Random();
        StringBuilder sb = new StringBuilder(LENGTH);

        // 문자열 길이만큼 랜덤 문자 선택
        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }

        return sb.toString();
    }
}
