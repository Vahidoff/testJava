package ru.job4j;


public abstract class Betta implements Alpha {

    public static final char chs = 'B';
    public final char ch = 'E';
    public final String betta = "betta";

    @Override
    public String methodFortyFirst(long l) {
        String result = "Start first";
        for (int index = 0; index < 20; index++) {
            result = result.concat( ":" + l++);
        }
        return result;
    }

    @Override
    public String methodSeventyFourth(double d) {
        StringBuilder result = new StringBuilder("Start fourth");
        for (int index = 0; index < 20; index++) {
            result.append(":").append(d++);
        }
        return result.toString();
    }

    protected String methodEightyFifth(String c) {
        StringBuilder result = new StringBuilder("Start fifth ");
        for (int index = 0; index < 20; index++) {
            result.append(c);
        }
        return result.toString();

    }
}
