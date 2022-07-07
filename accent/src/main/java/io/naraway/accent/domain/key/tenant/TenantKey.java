/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.key.tenant;

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
    public static final String HIERARCHY_DELIMITER = ":";
    public static final String MEMBER_DELIMITER = "@";

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
        if (tenantType.isSpace()) {
            if (id.contains(MEMBER_DELIMITER)) {
                return false;
            }
        } else {
            if (!id.contains(MEMBER_DELIMITER)) {
                return false;
            }
        }

        List<String> spaceKeys = parseSpaceKey();
        int spaceKeyCount = spaceKeys.size();
        boolean result = false;
        switch (tenantType) {
            case Square:
                result = spaceKeyCount == 2;
                break;
            case Denizen:
                result = spaceKeyCount == 2;
                break;
            case Pavilion:
                result = spaceKeyCount == 3;
                break;
            case Citizen:
                result = spaceKeyCount == 3;
                break;
            case Cineroom:
                result = spaceKeyCount == 4;
                break;
            case Audience:
                result = spaceKeyCount == 4;
                break;
        }

        return result;
    }

    protected static String buildSpaceKey(String parentSpaceKey, long childSpaceSequence) {
        //
        StringBuilder builder = new StringBuilder();

        builder.append(parentSpaceKey);
        builder.append(HIERARCHY_DELIMITER);
        builder.append(Numeral36.newInstance().getStr36(childSpaceSequence));

        return builder.toString();
    }

    protected static String buildMemberKey(DenizenKey denizenKey, String spaceKey) {
        //
        StringBuilder builder = new StringBuilder();

        builder.append(denizenKey.genSequence36());
        builder.append(MEMBER_DELIMITER);
        builder.append(spaceKey);

        return builder.toString();
    }

    protected static String buildMemberKey(String sequence36, String spaceKey) {
        //
        StringBuilder builder = new StringBuilder();
        builder.append(sequence36);
        builder.append(MEMBER_DELIMITER);
        builder.append(spaceKey);

        return builder.toString();
    }

    protected String parseToParentSpaceKey() {
        //
        return id.substring(0, id.lastIndexOf(HIERARCHY_DELIMITER));
    }

    protected String parseToSpaceKey() {
        //
        if (!id.contains(MEMBER_DELIMITER)) {
            return id;
        } else {
            return id.substring(id.lastIndexOf(MEMBER_DELIMITER) + 1);
        }
    }

    public static String parseToSequence36(String keyWithSequence) {
        //
        if (!keyWithSequence.contains("@")) {
            throw new IllegalArgumentException("Invalid member key");
        }

        return keyWithSequence.substring(0, keyWithSequence.indexOf(MEMBER_DELIMITER));
    }

    protected String parseToSequence36() {
        //
        return parseToSequence36(id);
    }

    protected List<String> parseSpaceKey() {
        //
        String spaceKeyString = parseToSpaceKey();
        StringTokenizer tokenizer = new StringTokenizer(spaceKeyString, HIERARCHY_DELIMITER);
        List<String> spaceKeys = new ArrayList<>(tokenizer.countTokens());
        while (tokenizer.hasMoreTokens()) {
            spaceKeys.add(tokenizer.nextToken());
        }

        return spaceKeys;
    }
}
