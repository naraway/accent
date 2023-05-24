/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.context;

public enum UserType {
    //
    Citizen("citizen"),
    Internal("internal"),
    Service("service"),
    None("none");

    private final String userType;

    UserType(String userType) {
        //
        this.userType = userType;
    }

    public String userType() {
        //
        return this.userType;
    }
}