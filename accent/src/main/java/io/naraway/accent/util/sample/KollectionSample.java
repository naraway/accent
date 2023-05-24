/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.sample;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KollectionSample {
    //
    private static final String KOLLECTION_ID = "nomad";
    private static final String KOLLECTION_NAME = "NOMAD Blue";

    public static String getId() {
        //
        return KOLLECTION_ID;
    }

    public static String getName() {
        //
        return KOLLECTION_NAME;
    }

    public static void main(String[] args) {
        //
        System.out.println("ID  : " + getId());
        System.out.println("Name: " + getName());
    }
}
