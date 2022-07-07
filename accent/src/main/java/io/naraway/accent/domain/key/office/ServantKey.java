/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.key.office;

import io.naraway.accent.util.json.JsonSerializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public abstract class ServantKey implements JsonSerializable {
    //
    public static final String SERVANT_DELIMITER = "#";

    private String id;
    private ServantType type;

    protected ServantKey(String id, ServantType type) {
        //
        this.id = id;
        this.type = type;
    }

    protected ServantKey(ServantKey servantKey) {
        //
        this.id = servantKey.getId();
        this.type = servantKey.getType();
    }

    protected String parseToOfficeKey() {
        //
        if (!id.contains(SERVANT_DELIMITER)) {
            return id;
        } else {
            return id.substring(id.lastIndexOf(SERVANT_DELIMITER) + 1);
        }
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    @Override
    public int hashCode() {
        //
        return Objects.hash(id);
    }

    public boolean equals(Object target) {
        //
        if (this == target) {
            return true;
        } else if (target != null && this.getClass() == target.getClass()) {
            ServantKey key = (ServantKey) target;
            return Objects.equals(this.id, key.getId());
        } else {
            return false;
        }
    }
}
