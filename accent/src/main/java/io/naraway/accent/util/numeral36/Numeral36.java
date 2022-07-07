/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.numeral36;

public class Numeral36 {
    //
    private static final int DECIMAL_36 = 36;

    private Numeral36() {
        //
    }

    public static Numeral36 newInstance() {
        //
        return new Numeral36();
    }

    public String getNextStr36(int int36) {
        //
        return getStr36(++int36);
    }

    public String getNextStr36(String str36) {
        //
        if (str36.length() == 1 && str36.equals("z")) {
            throw new IllegalStateException("Overflow, z is the last char in one byte value -> " + str36);
        }

        long longValue = this.getLong36(str36);
        longValue++;
        return getStr36(longValue);
    }

    public long getLong36(String str36) {
        //
        if (str36 == null || str36.equals("")) {
            throw new IllegalArgumentException("Minus digit is not valid -> " + str36);
        }

        long resultInt = 0;
        int currentIndex = 0;
        int remainCount;
        char currentChar;
        int intValue;

        while (currentIndex < str36.length()) {
            //
            currentChar = str36.charAt(currentIndex);
            if (currentChar < 'a') {
                intValue = (currentChar - '0');
            } else {
                intValue = (currentChar + 10 - 'a');
            }

            currentIndex++;
            remainCount = str36.length() - currentIndex;

            if (remainCount == 0) {
                resultInt += intValue;
            } else {
                resultInt += (intValue * (Math.pow(36, remainCount)));
            }
        }

        return resultInt;
    }

    public String getStr36(long int36, int formatLength) {
        //
        return format(getStr36(int36), formatLength);
    }

    public String getStr36(long int36) {
        //
        StringBuilder resultStr = new StringBuilder();

        if (int36 < 0) {
            throw new IllegalArgumentException("Minus digit is not valid -> " + int36);
        }

        if (int36 < 10) {
            resultStr.append((char) (int36 + '0'));
        } else {

            long remain;
            char charDigit;

            while (int36 > 0) {
                remain = int36 % DECIMAL_36;
                if (remain < 10) {
                    charDigit = (char) (remain + '0');
                } else {
                    charDigit = (char) (remain - 10 + 'a');
                }
                resultStr.insert(0, charDigit);
                int36 /= DECIMAL_36;
            }
        }
        return resultStr.toString();
    }

    private String format(String str36, int formatLength) {
        //
        int fillCount = formatLength - str36.length();
        if (fillCount < 0) {
            throw new IllegalArgumentException("Formatted length is smaller than str36 length:" + str36);
        }

        if (fillCount == 0) {
            return str36;
        }

        StringBuilder strBuilder = new StringBuilder(formatLength);
        for (int i = 0; i < fillCount; i++) {
            strBuilder.append("0");
        }

        strBuilder.append(str36);

        return strBuilder.toString();
    }

    public static void main(String[] args) {
        //
        Numeral36 numeral36 = new Numeral36();
        System.out.println(numeral36.getStr36(12));
        System.out.println(numeral36.getStr36(1201010));
    }
}
