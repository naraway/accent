package io.naraway.accent.domain.notice;

import io.naraway.accent.domain.key.stage.ActorKey;
import io.naraway.accent.domain.key.tenant.TenantKey;
import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeRecipient implements JsonSerializable {
    //
    private String stageId;
    private List<String> actor36Sequences;

    @Override
    public String toString() {
        // '
        return toJson();
    }

    public static NoticeRecipient fromJson(String json) {
        //
        return JsonUtil.fromJson(json, NoticeRecipient.class);
    }

    public static NoticeRecipient sample() {
        //
        String actor36Sequence = TenantKey.parseToSequence36(ActorKey.sample().getId());

        return new NoticeRecipient(
                ActorKey.sample().genStageId(),
                Arrays.asList(actor36Sequence)
        );
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
        System.out.println(ActorKey.sample().toPrettyJson());
    }
}