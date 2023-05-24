/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.entity.entity;

import io.naraway.accent.domain.entity.CreationDataObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("java:S2160")
public class FooCdo extends CreationDataObject {
    //
    private String name;
    private String password;
    private String socialNumber;
    private boolean enabled;
    private LocalDateTime dateTime;

    public static FooCdo sample() {
        //
        return new FooCdo(
                "name",
                "password",
                "1111111111",
                false,
                LocalDateTime.now());
    }
}
