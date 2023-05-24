/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.message;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class CommandRequest implements DomainMessage {
    //
    private CommandResponse response;

    public CommandResponse getResponse() {
        //
        if (this.response == null) {
            this.response = new CommandResponse();
        }

        return this.response;
    }

    @JsonSetter
    public void setResponse(CommandResponse response) {
        //
        this.response = response;
    }

    public void setResponse(String entityId) {
        //
        this.response = new CommandResponse(entityId);
    }

    public void setResponse(List<String> entityIds) {
        //
        this.response = new CommandResponse(entityIds);
    }

    public void setResponse(FailureMessage failureMessage) {
        //
        this.response = new CommandResponse(failureMessage);
    }
}