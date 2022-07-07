/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.trail.dynamic;

import io.naraway.accent.util.json.JsonSerializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Sort implements JsonSerializable {
    //
    private String sortingField;
    private SortDirection sortDirection;

    private Sort(String sortingField, SortDirection sortDirection) {
        // SortCriteria , Order
        this.sortingField = sortingField;
        this.sortDirection = sortDirection;
    }

    public static Sort newAscending(String fieldName) {
        //
        return new Sort(fieldName, SortDirection.ASCENDING);
    }

    public static Sort newDescending(String fieldName) {
        //
        return new Sort(fieldName, SortDirection.DESCENDING);
    }

    public boolean ascendingSort() {
        //
        return SortDirection.ASCENDING.equals(this.sortDirection);
    }

    public enum SortDirection {
        //
        ASCENDING,
        DESCENDING
    }
}