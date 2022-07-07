/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.key.tenant;

public enum TenantType {
    //
    Station,        // 1
    Square,         // 1:1
    Denizen,        // 1@1:1
    Pavilion,       // 1:1:1
    Citizen,        // 1@1:1:1
    Cineroom,       // 1:1:1:1
    Audience,       // 1@1:1:1:1
    Stage,          // 1:1:1:1-1
    Actor;          // 1@1:1:1:1-1

    // NOTE: regular expresion 을 이용한 validation

    public boolean isSpace() {
        //
        switch (this) {
            case Station:
                return true;
            case Square:
                return true;
            case Pavilion:
                return true;
            case Cineroom:
                return true;
            case Stage:
                return true;
            case Denizen:
                return false;
            case Citizen:
                return false;
            case Audience:
                return false;
            case Actor:
                return false;
        }

        return false;
    }
}
