/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.type;

import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Offset implements JsonSerializable {
    //
    @SuppressWarnings("java:S1700")
    private int offset;
    private int limit;
    private long totalCount;
    private boolean previous;
    private boolean next;
    private boolean totalCountRequested;
    private String sortingField;
    private SortDirection sortDirection;

    public Offset(int offset,
                  int limit) {
        //
        this.offset = offset;
        this.limit = limit > 0 ? limit : 10;
        this.sortDirection = SortDirection.ASCENDING;
        this.totalCountRequested = false;
        this.previous = false;
        this.next = false;
    }

    public Offset(int offset,
                  int limit,
                  SortDirection sortDirection,
                  String sortingField) {
        //
        this.offset = offset;
        this.limit = limit > 0 ? limit : 10;
        this.sortDirection = sortDirection;
        this.sortingField = sortingField;
        this.totalCountRequested = false;
        this.previous = false;
        this.next = false;
    }

    public static Offset newDefault() {
        //
        return new Offset(0, 10);
    }

    public static Offset newOne(int offset, int limit) {
        //
        return new Offset(offset, limit);
    }

    public static Offset newOne(int offset, int limit, SortDirection sortDirection, String sortingField) {
        //
        return new Offset(offset, limit, sortDirection, sortingField);
    }

    public boolean ascendingSort() {
        //
        return SortDirection.ASCENDING == this.sortDirection;
    }

    public boolean hasPrevious() {
        //
        return this.previous;
    }

    public boolean hasNext() {
        //
        return this.next;
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static Offset fromJson(String json) {
        //
        return JsonUtil.fromJson(json, Offset.class);
    }

    public int offset() {
        //
        return offset;
    }

    public int limit() {
        //
        return limit;
    }

    public int page() {
        //
        return (offset / limit);
    }

    public int sum() {
        //
        return offset + limit;
    }

    public enum SortDirection {
        //
        ASCENDING,
        DESCENDING
    }

    public static Offset sample() {
        //
        return new Offset(0, 20);
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
        System.out.println(sample().ascendingSort());
    }
}
