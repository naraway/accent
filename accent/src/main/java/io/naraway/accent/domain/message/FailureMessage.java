/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.message;

import io.naraway.accent.util.json.JsonSerializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FailureMessage implements JsonSerializable {
    //
    private String exceptionName;
    private String exceptionMessage;
    private String exceptionCode;

    public FailureMessage(Throwable throwable) {
        //
        this.exceptionName = throwable.getClass().getSimpleName();
        this.exceptionMessage = throwable.getMessage();
    }

    public FailureMessage(String exceptionName, String exceptionMessage) {
        //
        this.exceptionName = exceptionName;
        this.exceptionMessage = exceptionMessage;
    }
}