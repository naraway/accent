/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.tenant;

public enum TenantType {
    //
    Station(true),       // #

    Square(true),        // #:#
    Pavilion(true),      // #:#:#
    Cineroom(true),      // #:#:#:#
    CineroomChart(true), // #:#:#:#
    Backstage(true),     // #:#:#:#_#
    Stage(true),         // #:#:#:#-#

    Denizen(false),      // #@#:#
    Citizen(false),      // #@#:#:#
    Audience(false),     // #@#:#:#:#
    Actor(false);        // #@#:#:#:#-#

    private final boolean space;

    TenantType(boolean space) {
        //
        this.space = space;
    }

    public boolean isSpace() {
        //
        return this.space;
    }
}