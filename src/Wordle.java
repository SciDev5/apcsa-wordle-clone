import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wordle {
    public static char[] letters = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };
    public static char[][] keyboardShape = {
            {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'},
            {'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l'},
            {'z', 'x', 'c', 'v', 'b', 'n', 'm'}
    };

    private final ArrayList<Character> knownNotIn = new ArrayList<>();
    private final ArrayList<Character> knownIn = new ArrayList<>();
    private final ArrayList<Character> knownExactly = new ArrayList<>();
    private final ArrayList<String> attemptResults = new ArrayList<>();
    private boolean found = false;

    final String word;
    final int wordLen;

    public Wordle(String word) {
        if (!WordVerification.isInWords(word))
            throw new RuntimeException("unrecognized word '"+word+"'");
        wordLen = word.length();
        this.word = word;
    }


    void updateWord(String wordToCheck) {
        if (wordToCheck.length() != wordLen)
            throw new RuntimeException("Length Mismatch!");

        Map<Character, Integer> charHasCount = new HashMap<>();
        boolean[] exactMatches = new boolean[wordLen];
        for (int i = 0; i < wordLen; i++) {
            char wordChar = word.charAt(i);
            exactMatches[i] =
                    wordToCheck.charAt(i) == wordChar;
            if (!exactMatches[i])
                charHasCount.put(
                        wordChar,
                        1 + charHasCount.getOrDefault(wordChar, 0)
                );
        }
        boolean[] somewhereMatches = new boolean[wordLen];
        for (int i = 0; i < wordLen; i++) {
            if (exactMatches[i]) continue;
            char checkChar = wordToCheck.charAt(i);
            int n = charHasCount.getOrDefault(checkChar, 0);
            if (n > 0) {
                somewhereMatches[i] = true;
                charHasCount.put(checkChar, n - 1);
            } else {
                somewhereMatches[i] = false;
            }
        }
        boolean[] notMatches = new boolean[wordLen];
        for (int i = 0; i < wordLen; i++) {
            notMatches[i] = !(exactMatches[i] || somewhereMatches[i]);
        }

        StringBuilder resultText = new StringBuilder();
        found = true;
        for (int i = 0; i < wordLen; i++) {
            char chr = wordToCheck.charAt(i);
            if (somewhereMatches[i]) {
                knownIn.add(chr);
            }
            if (exactMatches[i]) {
                knownExactly.add(chr);
                knownIn.remove((Character) chr);
            } else {
                found = false;
            }
            if (notMatches[i]) {
                knownNotIn.add(chr);
            }

            resultText.append(genLetter(chr, notMatches[i], somewhereMatches[i], exactMatches[i]));
        }
        attemptResults.add(resultText.toString());
    }
    public boolean getFound() {
        return found;
    }

    public void print() {
        P.ln();
        for (String result : attemptResults)
            P.ln("  ╟┈┈  ", result);
        P.ln();
        P.ln(genKeyboard());
    }

    private String genKeyboard() {
        StringBuilder str = new StringBuilder();
        int i = 0;
        for (char[] row : keyboardShape) {
            for (char letter : row)
                str.append(genLetterKeyboard(letter)).append(" ");
            str.append('\n');
        }
        return str.toString();
    }

    private String genLetter(char p, boolean knownNotIn, boolean knownIn, boolean knownExactly) {
        String color = P.RESET;
        if (knownNotIn)
            color = P.WHITE + P.GREY_BG;
        if (knownIn)
            color = P.YELLOW + P.LIGHT_YELLOW_BG;
        if (knownExactly)
            color = P.CYAN + P.LIGHT_CYAN_BG;

        return color + " " + p + " " + P.RESET;
    }

    private String genLetterKeyboard(char p) {
        return genLetter(
                p,
                knownNotIn.contains(p),
                knownIn.contains(p),
                knownExactly.contains(p)
        );
    }
}
