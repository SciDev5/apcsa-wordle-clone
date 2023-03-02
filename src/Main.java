import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Wordle w = new Wordle(WordVerification.words[(int)(Math.random()*WordVerification.words.length)]);
        Scanner s = new Scanner(System.in);

        P.ln();
        P.ln("░░░░░░ Welcome to Wordle ░░");
        P.ln();
        P.ln(P.GREY_BG,P.WHITE," YELLOW ",P.RESET, " means 'wrong letter'");
        P.ln(P.LIGHT_YELLOW_BG,P.YELLOW," YELLOW ",P.RESET, " means 'right letter, wrong position'");
        P.ln(P.LIGHT_CYAN_BG,P.CYAN," BLUE ",P.RESET, " means 'right letter, right position'");
        P.ln();
        P.ln("═".repeat(50));

        boolean won = false;
        for (int i = 0; i < 6; i++) {
            String guess;
            while (true) {
                P.ln("░ Enter Guess (", w.wordLen + "", " letters):");
                String guessCandidate = s.nextLine().trim();
                if (WordVerification.isValid(guessCandidate,w)) {
                    guess = guessCandidate;
                    break;
                } else {
                    P.ln("[!] Word '"+guessCandidate+"' not recognized;");
                }
            }
            w.updateWord(guess);
            P.ln("┄".repeat(50));
            w.print();
            P.ln("═".repeat(50));
            if (w.getFound()) {
                P.ln(" ",P.LIGHT_CYAN_BG,P.BLACK,"░░░░ YOU FOUND THE WORD! ░░░░",P.RESET);
                P.ln(" ░ ░ ░ ░  "+(i+1)+" guess(es)  ░ ░ ░ ░");
                return;
            }
        }
        P.ln(" ",P.LIGHT_RED_BG,P.BLACK,"░▓░▓░▓░▓  GAME OVER  ▓░▓░▓░▓░",P.RESET);
        P.ln(" ░░░░░░░ Word was '",w.word,"' ░░");
    }
}