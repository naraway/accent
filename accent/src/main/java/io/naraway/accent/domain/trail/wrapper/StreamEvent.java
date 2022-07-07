/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.trail.wrapper;

import io.naraway.accent.domain.trail.AbstractQuery;
import io.naraway.accent.domain.trail.TrailMessage;
import io.naraway.accent.domain.trail.TrailMessageType;
import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class StreamEvent implements JsonSerializable {
    //
    private String id;                                   // Equals with Command, Event, QueryRequest id
    private String payloadType;
    private String payloadClass;
    private String payload;
    private String timestamp;

    public StreamEvent(TrailMessage trailMessage) {
        //
        this.id = trailMessage.getId();
        this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
        this.payloadType = trailMessage.getMessageType().name();
        this.payloadClass = trailMessage.getClass().getName();

        if (trailMessage.getMessageType().equals(TrailMessageType.DynamicQueryRequest) ||
                trailMessage.getMessageType().equals(TrailMessageType.QueryRequest)) {
            this.payload = ((AbstractQuery<?>) trailMessage).toJsonWithoutQueryResult();
        } else {
            this.payload = trailMessage.toJson();
        }
    }

    public static StreamEvent fromJson(String json) {
        //
        return JsonUtil.fromJson(json, StreamEvent.class);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    @Override
    public boolean equals(Object target) {
        //
        if (this == target) {
            return true;
        }

        if (target == null || getClass() != target.getClass()) {
            return false;
        }

        StreamEvent entity = (StreamEvent) target;

        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        //
        return Objects.hash(id);
    }

    public <T> boolean equalsPayloadName(Class<T> clazz) {
        //
        return this.payloadClass.equals(clazz.getName());
    }

    public <T> T payloadObject(Class<T> clazz) {
        //
        return JsonUtil.fromJson(payload, clazz);
    }

    public Object payloadObject() {
        //
        try {
            return JsonUtil.fromJson(payload, Class.forName(payloadClass));
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Can't make payload object --> " + payloadClass, e);
        }
    }
}