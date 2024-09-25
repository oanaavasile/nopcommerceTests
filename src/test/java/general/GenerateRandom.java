package general;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.Month;
import java.util.Random;

public class GenerateRandom {
    public static String generateRandomString(int length) {

        return RandomStringUtils.randomAlphabetic(length);
    }

    public static int generateRandomNumber(int max) {
        Random rn = new Random();
        Integer number = rn.nextInt(max) + 1;
        return number;
    }

    public static String generateStringWithAllobedSplChars(int length, String allowdSplChrs) {
        String allowedChars = "abcdefghijklmnopqrstuvwxyz" +   //alphabets
                "1234567890" +   //numbers
                allowdSplChrs;
        return RandomStringUtils.random(length, allowedChars);
    }

    public static String generateEmail(int length) {
        String allowedChars = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder randomStr = new StringBuilder();
        Random rnd = new Random();
        while (randomStr.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * allowedChars.length());
            randomStr.append(allowedChars.charAt(index));
        }
        return randomStr.toString();
    }

    public static String randomMonth() {
        // Create an instance of Random class
        Random random = new Random();

        // Generate a random index between 0 and 11 (inclusive)
        int randomIndex = random.nextInt(Month.values().length);

        String monthName = (Month.values()[randomIndex]).toString().toLowerCase();
        monthName = Character.toUpperCase(monthName.charAt(0)) + monthName.substring(1);

        return monthName;

    }

    public static String randomYear(int minYear, int maxYear) {

        // Create an instance of Random class
        Random random = new Random();

        // Generate a random year within the specified range
        int randomYear = random.nextInt(maxYear - minYear + 1) + minYear;

        return String.valueOf(randomYear);
    }
}
