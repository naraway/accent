/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.sample;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DramaSample {
    //
    private static final String DRAMA_ID = "smalltalk";
    private static final String DRAMA_NAME = "Small Talk";

    public static String getId() {
        //
        return DRAMA_ID;
    }

    public static String getName() {
        //
        return DRAMA_NAME;
    }

    public static void main(String[] args) {
        //
        System.out.println("ID  : " + getId());
        System.out.println("Name: " + getName());
    }
}
