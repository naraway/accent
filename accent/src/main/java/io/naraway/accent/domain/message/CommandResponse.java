/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.message;

import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import io.naraway.accent.util.uuid.TinyUUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommandResponse implements JsonSerializable {
    //
    private List<String> entityIds;
    private boolean requestFailed;
    private FailureMessage failureMessage;

    public CommandResponse(String entityId) {
        //
        List<String> newEntityIds = new ArrayList<>();
        newEntityIds.add(entityId);
        this.entityIds = newEntityIds;
    }

    public CommandResponse(List<String> entityIds) {
        //
        this.entityIds = entityIds;
    }

    public CommandResponse(FailureMessage failureMessage) {
        //
        if (failureMessage != null) {
            this.failureMessage = failureMessage;
            this.requestFailed = true;
        }
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static CommandResponse fromJson(String json) {
        //
        return JsonUtil.fromJson(json, CommandResponse.class);
    }

    public static CommandResponse sample() {
        //
        return new CommandResponse(TinyUUID.random());
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
    }
}