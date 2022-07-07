/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.numeral36;

public class UsidGen {
    //
    private UsidGen() {
        //
    }

    public static String getStr36(String prefix, String separator, long number, int formatLength) {
        //
        number = (number < 0 ? -number : number);

        Numeral36 numeral36 = Numeral36.newInstance();

        return String.format("%s%s%s",
                prefix, separator,
                numeral36.getStr36(number, formatLength));
    }

    public static String getStr36(String prefix, long number, int formatLength) {
        //
        number = (number < 0 ? -number : number);

        Numeral36 numeral36 = Numeral36.newInstance();
        return String.format("%s-%s", prefix, numeral36.getStr36(number, formatLength));
    }

    public static String getStr36(String prefix, long number) {
        //
        number = (number < 0 ? -number : number);

        Numeral36 numeral36 = Numeral36.newInstance();
        return String.format("%s-%s", prefix, numeral36.getStr36(number));
    }

    public static String getStr36(long number, int formatLength) {
        //
        number = (number < 0 ? -number : number);

        Numeral36 numeral36 = Numeral36.newInstance();
        return numeral36.getStr36(number, formatLength);
    }

    public static String getStr36(long number) {
        //
        number = (number < 0 ? -number : number);

        Numeral36 numeral36 = Numeral36.newInstance();
        return numeral36.getStr36(number);
    }
}
