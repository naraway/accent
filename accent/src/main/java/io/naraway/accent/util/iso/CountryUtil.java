/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.iso;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CountryUtil {
    //
    public static String[] requestIsoCountryCodes() {
        //
        return Locale.getISOCountries();
    }

    public static String[][] requestIsoCountryCodeNames() {
        //
        String[] countryCodes = requestIsoCountryCodes();
        String[][] codeNames = new String[countryCodes.length][2];

        int i = 0;
        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            codeNames[i][0] = locale.getCountry();
            codeNames[i][1] = locale.getDisplayCountry();
            i++;
        }

        return codeNames;
    }

    public static boolean validCode(String code) {
        //
        String[] countryCodes = requestIsoCountryCodes();
        for (String countryCode : countryCodes) {
            if (countryCode.equals(code)) {
                return true;
            }
        }

        return false;
    }

    public static String requestName(String code) {
        //
        if (!validCode(code)) {
            throw new IllegalArgumentException("Code: " + code);
        }

        Locale locale = new Locale("", code);

        return locale.getDisplayCountry();
    }

    public static void main(String[] args) {
        //
        System.out.println(validCode("KR"));
        System.out.println(validCode("kr"));
        System.out.println(validCode("yy"));
    }
}