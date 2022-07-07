/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.trail;

import io.naraway.accent.domain.key.stage.ActorKey;
import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrailInfo implements JsonSerializable {
    //
    private String trailId;                 // uuid
    private String messageId;               // callee message id, uuid
    private String service;                 // callee service name. ex, io.naradrama.depot
    private String message;                 // callee DomainMessage name. ex, LeaveEvent
    private String parentMessageId;         // caller(parent) message id, uuid
    private String parentService;           // caller(parent) service name. ex, io.naradrama.timecard
    private String parentMessage;           // caller DomainMessage name. ex, LeaveCommand
    private String userId;                  // actorId or stationPlayerId, etc.
    private TrailMessageType messageType;   // PayloadType payloadType
    private long requestTime;
    private long waitingTime;

    public TrailInfo(String userId) {
        //
        this.trailId = UUID.randomUUID().toString();
        this.userId = userId;
        this.requestTime = System.currentTimeMillis();
    }

    public TrailInfo(TrailInfo parentTrailInfo,
                     String service,
                     TrailMessage message) {
        //
        this.trailId = parentTrailInfo.getTrailId();
        this.service = service;
        this.message = message.getClass().getSimpleName();
        this.messageId = message.getId();
        this.parentMessageId = parentTrailInfo.getMessageId();
        this.parentService = parentTrailInfo.getService();
        this.parentMessage = parentTrailInfo.getMessage();
        this.userId = parentTrailInfo.getUserId();
        this.requestTime = System.currentTimeMillis();
        this.messageType = message.getMessageType();
    }

    public TrailInfo(TrailInfo trailInfo) {
        //
        this(trailInfo, null, null);
    }

    public TrailInfo(String service, TrailMessage trailMessage) {
        //
        this.trailId = UUID.randomUUID().toString();
        this.service = service;
        this.message = trailMessage.getClass().getSimpleName();
        this.messageId = trailMessage.getId();
        this.requestTime = System.currentTimeMillis();
        this.messageType = trailMessage.getMessageType();
    }

    public static TrailInfo newTrailInfo() {
        //
        TrailInfo trailInfo = new TrailInfo();
        trailInfo.setTrailId(UUID.randomUUID().toString());
        trailInfo.setRequestTime(System.currentTimeMillis());

        return trailInfo;
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static TrailInfo fromJson(String json) {
        //
        return JsonUtil.fromJson(json, TrailInfo.class);
    }

    public TrailInfo copy() {
        //
        TrailInfo trailInfo = new TrailInfo();
        BeanUtils.copyProperties(this, trailInfo);
        trailInfo.setRequestTime(System.currentTimeMillis());

        return trailInfo;
    }

    public void calculateWaitingTime() {
        //
        this.waitingTime = (System.currentTimeMillis() - requestTime);
    }

    public static TrailInfo sample() {
        //
        TrailInfo sample = new TrailInfo(ActorKey.sample().getId());
        sample.setService("io.naradrama.timecard");
        sample.setMessage("LeaveCommand");

        return sample;
    }

    @SuppressWarnings("java:S106")
    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
    }
}