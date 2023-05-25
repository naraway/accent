/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.context;

import io.naraway.accent.domain.rolemap.DramaRole;
import io.naraway.accent.domain.tenant.ActorKey;
import io.naraway.accent.domain.tenant.TenantKey;
import io.naraway.accent.domain.tenant.TenantType;
import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import io.naraway.accent.util.sample.DramaSample;
import io.naraway.accent.util.sample.KollectionSample;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class StageRequest implements JsonSerializable {
    //
    private String username;
    private UserType userType;
    private String displayName;
    private String email;
    private boolean enabled;
    private String actorId;
    private String pavilionId;
    private String osid;
    private String usid;
    private String kollectionId;
    private String dramaId;
    private List<String> cineroomIds;
    private List<String> roles;
    private Map<String, Object> attributes;

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static StageRequest fromJson(String json) {
        //
        return JsonUtil.fromJson(json, StageRequest.class);
    }

    public String getCitizenId() {
        //
        if (TenantKey.getTenantType(actorId) != TenantType.Actor) {
            return null;
        }

        return ActorKey.fromId(actorId).genCitizenId();
    }

    public String getPavilionId() {
        //
        if (TenantKey.getTenantType(pavilionId) != TenantType.Pavilion) {
            return TenantKey.getTenantType(actorId) != TenantType.Actor
                    ? ActorKey.fromId(actorId).genPavilionId() : null;
        }

        return pavilionId;
    }

    public String getAudienceId() {
        //
        if (TenantKey.getTenantType(actorId) != TenantType.Actor) {
            return null;
        }

        return ActorKey.fromId(actorId).genAudienceId();
    }

    public String getCineroomId() {
        //
        if (TenantKey.getTenantType(actorId) != TenantType.Actor) {
            return null;
        }

        return ActorKey.fromId(actorId).genCineroomId();
    }

    public String getStageId() {
        //
        if (TenantKey.getTenantType(actorId) != TenantType.Actor) {
            return null;
        }

        return ActorKey.fromId(actorId).genStageId();
    }

    public boolean hasAuthority() {
        //
        if (userType == UserType.Internal || userType == UserType.Service) {
            return true;
        }

        if (actorId != null && actorId.trim().length() > 0 && cineroomIds != null && !cineroomIds.isEmpty()) {
            if (TenantKey.getTenantType(actorId) != TenantType.Actor) {
                return false;
            }

            String activeCineroomId = ActorKey.fromId(actorId).genCineroomKey().getId();
            return cineroomIds.contains(activeCineroomId);
        }

        return false;
    }

    public boolean hasRole(String role) {
        //
        if (userType == UserType.Internal) {
            return true;
        }

        return roles.contains(role);
    }

    public static StageRequest anonymous() {
        //
        return StageRequest.builder()
                .username("anonymous")
                .userType(UserType.None)
                .displayName("Anonymous")
                .enabled(true)
                .actorId("0@0:0:0:0-0")
                .pavilionId("0:0:0")
                .osid("0:0:0")
                .usid("anonymous")
                .cineroomIds(Collections.emptyList())
                .roles(Collections.emptyList())
                .attributes(Collections.emptyMap())
                .build();
    }

    public static StageRequest sample() {
        //
        ActorKey actorKey = ActorKey.sample();

        return StageRequest.builder()
                .username("nara.kim")
                .userType(UserType.Citizen)
                .displayName("Nara Kim")
                .email("nara.kim@naraway.io")
                .enabled(true)
                .actorId(actorKey.getId())
                .pavilionId(actorKey.genPavilionId())
                .osid("nara")
                .usid("nara.kim")
                .kollectionId(KollectionSample.getId())
                .dramaId(DramaSample.getId())
                .cineroomIds(Collections.singletonList(actorKey.genCineroomId()))
                .roles(Collections.singletonList(DramaRole.sample().getCode()))
                .attributes(Collections.emptyMap())
                .build();
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
    }
}