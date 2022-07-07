package io.naraway.accent.domain.notice;

import io.naraway.accent.domain.key.stage.ActorKey;
import io.naraway.accent.domain.type.NameValueList;
import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Notice implements JsonSerializable {
    //
    private NoticeTarget target;
    private String appName;
    private String stageId;
    private String actorId;
    private String message;
    private long time;
    private NameValueList infoFields;
    private List<NoticeRecipient> noticeRecipients;

    public Notice(NoticeTarget target,
                  String appName,
                  String actorId,
                  String message) {
        //
        this.target = target;
        this.appName = appName;
        this.stageId = ActorKey.fromId(actorId).genStageId();
        this.actorId = actorId;
        this.message = message;
        this.time = System.currentTimeMillis();
        this.noticeRecipients = new ArrayList<>();
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static Notice fromJson(String json) {
        //
        return JsonUtil.fromJson(json, Notice.class);
    }

    public static Notice sample() {
        //
        Notice sample = new Notice(
                NoticeTarget.Actor,
                "io.naraway.smalltalk",
                ActorKey.sample().getId(),
                "A new talk is registered"
        );
        sample.setInfoFields(NameValueList.newInstance("TalkCount", "30"));
        sample.setNoticeRecipients(Arrays.asList(NoticeRecipient.sample()));

        return sample;
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
    }
}