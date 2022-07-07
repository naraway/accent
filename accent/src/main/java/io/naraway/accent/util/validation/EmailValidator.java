/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    //
    @SuppressWarnings("java:S5998")
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private EmailValidator() {
        //
    }

    public static boolean validate(final String email) {
        //
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValid(String email) {
        //
        return validate(email);
    }
}
