package ru.job4j;

public class Gamma extends Betta {

    public static final char chs = 'G';
    public final char ch = 'M';
    public final String gamma = "gamma";

    protected String methodEightyFifth(String c) {
        StringBuilder result = new StringBuilder("Start String");
        for (int index = 0; index < 20; index++) {
            result.append(":").append(c);
        }
        return result.toString();
    }

    protected String methodSuper(String s) {
        return super.methodEightyFifth(s);
    }
}
