/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.security.bcrypt;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptPasswordEncoder {
    //
    private BCryptPasswordEncoder() {
        //
    }

    public static String encode(String password) {
        //
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean check(String password, String encodedPassword) {
        //
        return BCrypt.checkpw(password, encodedPassword);
    }
}
