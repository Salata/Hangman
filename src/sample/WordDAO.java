package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordDAO {
    private static List<String> words = Arrays.asList("hello", "world", "elephant", "bar");
    private static String startScreenLabel = "HANGMAN";
    private static int result = 0;

    public static List<String> getWords() {
        return words;
    }

    public static String getStartScreenLabel() {
        return startScreenLabel;
    }

    public static void setStartScreenLabel(String startScreenLabel) {
        WordDAO.startScreenLabel = startScreenLabel;
    }


    public static int getResult() {
        return result;
    }

    public static void setResult(int result) {
        WordDAO.result = result;
    }
}
