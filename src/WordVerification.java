import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class WordVerification {
    public static final String[] words;

    static {
        try (FileReader fr = new FileReader("./wordList.txt"); BufferedReader br = new BufferedReader(fr)) {
            words = br.readLine().split(",");
        } catch (IOException e){
            throw new RuntimeException("wordList.txt not found");
        }
    }

    static boolean isInWords(String candidate) {
        for (String word : words) {
            if (word.equals(candidate))
                return true;
        }
        return false;
    }
    static boolean isValid(String candidate, Wordle wordle) {
        if (candidate.length() != wordle.wordLen)
            return false;
        return isInWords(candidate);
    }
}
