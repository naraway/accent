/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.tenant;

import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.numeral36.Numeral36;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

@Getter
@Setter
@NoArgsConstructor
public abstract class TenantKey implements JsonSerializable {
    //
    public static final String MEMBER_DELIMITER = "@";
    public static final String HIERARCHY_DELIMITER = ":";
    public static final String SPACE_DELIMITER = "-"; // stage
    public static final String GROUP_DELIMITER = "_"; // stage

    private String id;
    private TenantType type;

    protected TenantKey(String id, TenantType type) {
        //
        this.id = id;
        this.type = type;
        if (!isValidFor(type)) {
            throw new IllegalArgumentException("Invalid key: " + id + " for: " + type.name());
        }
    }

    protected TenantKey(TenantKey tenantKey) {
        //
        this.id = tenantKey.getId();
        this.type = tenantKey.getType();
    }

    public boolean equals(Object target) {
        //
        if (this == target) {
            return true;
        } else if (target != null && this.getClass() == target.getClass()) {
            TenantKey key = (TenantKey) target;
            return Objects.equals(this.id, key.getId());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    @SuppressWarnings("java:S131")
    public boolean isValidFor(TenantType tenantType) {
        //
        if (id == null || id.trim().length() == 0) {
            return false;
        }

        if (id.contains(" ")) {
            return false;
        }

        if (tenantType.isSpace()) {
            if (id.contains(MEMBER_DELIMITER)) {
                return false;
            }
        } else {
            if (!id.contains(MEMBER_DELIMITER)) {
                return false;
            }
        }

        List<String> spaceKeys = parseSpaceKeys();
        int spaceKeyCount = spaceKeys.size();
        boolean result = false;
        switch (tenantType) {
            case Station:
                result = spaceKeyCount == 1;
                break;
            case Square:
            case Denizen:
                result = spaceKeyCount == 2;
                break;
            case Pavilion:
            case Citizen:
                result = spaceKeyCount == 3;
                break;
            case Cineroom:
            case CineroomChart:
            case Audience:
                result = spaceKeyCount == 4;
                break;
            case Stage:
            case Actor:
                result = spaceKeyCount == 4 && spaceKeys.get(spaceKeyCount - 1).contains(SPACE_DELIMITER);
                break;
            case Backstage:
                result = spaceKeyCount == 4 && spaceKeys.get(spaceKeyCount - 1).contains(GROUP_DELIMITER);
                break;
        }

        return result;
    }

    protected static String buildSpaceKey(String parentSpaceKey, long childSpaceSequence) {
        //
        return parentSpaceKey +
                HIERARCHY_DELIMITER +
                Numeral36.newInstance().getStr36(childSpaceSequence);
    }

    protected static String buildStageSpaceKey(String parentSpaceKey, long childSpaceSequence) {
        //
        return parentSpaceKey +
                SPACE_DELIMITER +
                Numeral36.newInstance().getStr36(childSpaceSequence);
    }

    protected static String buildBackstageKey(String parentSpaceKey, long childSpaceSequence) {
        //
        return parentSpaceKey +
                GROUP_DELIMITER +
                Numeral36.newInstance().getStr36(childSpaceSequence);
    }

    protected static String buildMemberKey(AudienceKey audienceKey, String spaceKey) {
        //
        return audienceKey.genSequence36() +
                MEMBER_DELIMITER +
                spaceKey;
    }

    protected static String buildMemberKey(CitizenKey citizenKey, String spaceKey) {
        //
        return citizenKey.genSequence36() +
                MEMBER_DELIMITER +
                spaceKey;
    }

    protected static String buildMemberKey(DenizenKey denizenKey, String spaceKey) {
        //
        return denizenKey.genSequence36() +
                MEMBER_DELIMITER +
                spaceKey;
    }

    protected static String buildMemberKey(String sequence36, String spaceKey) {
        //
        return sequence36 +
                MEMBER_DELIMITER +
                spaceKey;
    }

    protected String parseParentKey() {
        //
        return id.substring(0, id.lastIndexOf(HIERARCHY_DELIMITER));
    }

    protected String parseSpaceKey() {
        //
        if (!id.contains(MEMBER_DELIMITER)) {
            return id;
        } else {
            return id.substring(id.lastIndexOf(MEMBER_DELIMITER) + 1);
        }
    }

    protected String parseSequence36() {
        //
        return parseSequence36(id);
    }

    protected List<String> parseSpaceKeys() {
        //
        String spaceKeyString = parseSpaceKey();
        StringTokenizer tokenizer = new StringTokenizer(spaceKeyString, HIERARCHY_DELIMITER);
        List<String> spaceKeys = new ArrayList<>(tokenizer.countTokens());
        while (tokenizer.hasMoreTokens()) {
            spaceKeys.add(tokenizer.nextToken());
        }

        return spaceKeys;
    }

    public static String parseSequence36(String keyWithSequence) {
        //
        if (!keyWithSequence.contains("@")) {
            throw new IllegalArgumentException("Invalid member key");
        }

        return keyWithSequence.substring(0, keyWithSequence.indexOf(MEMBER_DELIMITER));
    }

    @SuppressWarnings("java:S3776")
    public static TenantType getTenantType(String key) {
        //
        if (key == null) {
            return null;
        }

        boolean isMemberKey = key.contains(MEMBER_DELIMITER);
        String spacePart = isMemberKey ? key.substring(key.indexOf(MEMBER_DELIMITER) + 1) : key;

        if (spacePart.matches("\\w+:\\w+:\\w+:\\w+_\\w+")) {
            return isMemberKey ? null : TenantType.Backstage;
        } else if (spacePart.matches("\\w+:\\w+:\\w+:\\w+-\\w+")) {
            return isMemberKey ? TenantType.Actor : TenantType.Stage;
        } else if (spacePart.matches("\\w+:\\w+:\\w+:\\w+")) {
            return isMemberKey ? TenantType.Audience : TenantType.Cineroom;
        } else if (spacePart.matches("\\w+:\\w+:\\w+")) {
            return isMemberKey ? TenantType.Citizen : TenantType.Pavilion;
        } else if (spacePart.matches("\\w+:\\w+")) {
            return isMemberKey ? TenantType.Denizen : TenantType.Square;
        } else if (spacePart.matches("\\w+")) {
            return isMemberKey ? null : TenantType.Station;
        }

        return null;
    }
}