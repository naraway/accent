/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.key.stage;

import io.naraway.accent.util.json.JsonSerializable;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public abstract class SpaceKey implements JsonSerializable {
    //
    public static final String MEMBER_DELIMITER = "@";

    private String id;
    private SpaceType type;

    protected SpaceKey() {
        //
    }

    protected SpaceKey(String id, SpaceType type) {
        //
        this.id = id;
        this.type = type;
    }

    protected SpaceKey(SpaceKey spaceKey) {
        //
        this.id = spaceKey.getId();
        this.type = spaceKey.getType();
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
        } else if (target != null && this.getClass() == target.getClass()) {
            SpaceKey key = (SpaceKey) target;
            return Objects.equals(this.id, key.getId());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        //
        return Objects.hash(id);
    }
}
