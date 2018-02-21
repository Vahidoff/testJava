package ru.job4j;

import org.junit.Test;

public class GammaTest {

    @Test
    public void gammaMethods() {
        final String c = "h";
        String s = "a1";

        Gamma gamma = new Gamma();
        Betta betta = new Gamma();
        Betta bettaSecond = new Betta() {
            @Override
            protected String methodEightyFifth(String c) {
                return super.methodEightyFifth(c);
            }
        };

        System.out.println(gamma.methodFortyFirst(0));
        System.out.println(bettaSecond.methodFortyFirst(0));
        System.out.println(gamma.methodSeventyFourth(0));
        System.out.println(gamma.methodEightyFifth(c));
        System.out.println(betta.methodEightyFifth(c));

        System.out.println(bettaSecond.methodEightyFifth(c));
        System.out.println(gamma.methodSuper(c));
    }

    @Test
    public void gammaConst() {
        Betta bettaSecond = new Betta() {
            @Override
            protected String methodEightyFifth(String c) {
                return super.methodEightyFifth(c);
            }
        };
        Gamma gamma = new Gamma();
        Betta betta = new Gamma();

        System.out.println(gamma.ch);
        System.out.println(betta.ch);

        System.out.println(gamma.chs);
        System.out.println(betta.chs);

        System.out.println(gamma.betta);
        System.out.println(gamma.gamma);
        System.out.println(betta.betta);
//        System.out.println(bettaSecond.gamma);        //недоступно
//        System.out.println(betta.gamma);      //недоступно
    }

    @Test
    public void adduction() {
        Gamma gamma = new Gamma();
        Betta betta;
        betta = gamma;         //аналогично  Betta betta = new Gamma();

        System.out.println(betta.betta);
//        System.out.println(betta.methodSuper("S"));   //недоступно
//        System.out.println(betta.gamma);      //недоступно
        System.out.println(gamma.methodSuper("S"));
    }

    @Test
    public void adductionTwo() {
        Gamma gamma = new Gamma();
        Gamma gammaSecond = new Gamma();
        /**
         * зкземпляр анонимного суперкласса класса нельзя приводить к классу потомку
         */
//        Gamma gammaThird = (Gamma) new Betta() {
//            @Override
//            public String methodFortyFirst(long l) {
//                String result = "Start first";
//                for (int index = 0; index < 20; index++) {
//                    result = result.concat(":" + l++);
//                }
//                return result;
//            }
//
//        };

        Betta betta = new Gamma();
        Betta bettaSecond = new Betta() {
            @Override
            protected String methodEightyFifth(String c) {
                return super.methodEightyFifth(c);
            }
        };

//        gamma = bettaSecond;  //недоступно
//        gammaSecond = (Gamma) bettaSecond; // опасно ClassCastException
//        gamma = betta;        //недоступно
        gamma = (Gamma) betta;
        bettaSecond = gammaSecond;   //аналогично  Betta bettaSecond = new Gamma();

        System.out.println(gamma.betta);
        System.out.println(gamma.gamma);
        System.out.println(gammaSecond.betta);
        System.out.println(gammaSecond.gamma);

        System.out.println(bettaSecond.betta);
//        System.out.println(bettaSecond.gamma);        //недоступно
    }

    @Test
    public void alphaTest() {
        Alpha alpha = new Betta() {
            @Override
            protected String methodEightyFifth(String c) {
                return super.methodEightyFifth(c);
            }
        };
        System.out.println(alpha.alphaString);
//        alpha.alphaString = "betta";    // нельзя т.к. final
//        Alpha.alphaString = "betta";
//        System.out.println(alpha.ch);   //недоступно
//        System.out.println(alpha.betta);   //недоступно
//        System.out.println(alpha.chs);   //недоступно

        System.out.println(alpha.methodFortyFirst(5));
        System.out.println(alpha.methodSeventyFourth(5));
//        System.out.println(alpha.methodEightyFifth("a"));   //недоступно

    }

    @Test
    public void alphaTestSecond() {
        Alpha alpha = new Alpha() {
            @Override
            public String methodFortyFirst(long l) {
                return this.alphaString + l;
            }

            @Override
            public String methodSeventyFourth(double sh) {
                return this.alphaString + sh;
            }
        };
//        Gamma gamma = alpha;  //недоступно
//        Betta betta = alpha;   //недоступно
//        Betta betta = (Betta) alpha;   //java.lang.ClassCastException:
//        Gamma gamma = (Gamma) alpha; // java.lang.ClassCastException:
        System.out.println(alpha.alphaString);
        System.out.println(alpha.methodFortyFirst(5));
        System.out.println(alpha.methodSeventyFourth(5));

//        System.out.println(alpha.betta);  //недоступно
    }
}
