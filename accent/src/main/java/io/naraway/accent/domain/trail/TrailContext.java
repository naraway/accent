/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.trail;

public class TrailContext {
    //
    private static final ThreadLocal<TrailInfo> threadLocal = new ThreadLocal<>();

    private TrailContext() {
        //
    }

    public static void set(TrailInfo trailInfo) {
        //
        threadLocal.set(trailInfo);
    }

    public static TrailInfo get() {
        //
        return threadLocal.get();
    }

    public static void clear() {
        //
        threadLocal.remove();
    }
}