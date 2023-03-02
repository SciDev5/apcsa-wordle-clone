public class P {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK_BG = "\u001B[40m";
    public static final String YELLOW = "\u001B[33m";
    public static final String LIGHT_YELLOW_BG = "\u001B[103m";
    public static final String CYAN = "\u001B[36m";
    public static final String LIGHT_CYAN_BG = "\u001B[106m";
    public static final String WHITE = "\u001B[37m";
    public static final String BLACK = "\u001B[30m";
    public static final String GREY_BG = "\u001B[100m";
    public static final String RED = "\u001B[31m";
    public static final String LIGHT_RED_BG = "\u001B[101m";

    public static void p(String ...data) {
        for (String z : data)
            System.out.print(z);
    }
    public static void ln(String ...data) {
        P.p(data);
        System.out.println();
    }

}
