/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.trail.dynamic;

import io.naraway.accent.util.json.JsonSerializable;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
public class Sorts implements JsonSerializable {
    //
    @SuppressWarnings("java:S1700")
    private List<Sort> sorts;

    public Sorts() {
        //
        this.sorts = new ArrayList<>();
    }

    private Sorts(Sort sort) {
        //
        this();
        sorts.add(sort);
    }

    private Sorts(List<Sort> sorts) {
        //
        this();
        sorts.addAll(new ArrayList<>(sorts));
    }

    public Sorts(Sort... sorts) {
        //
        this();
        for (Sort sort : sorts) {
            this.sorts.add(sort);
        }
    }

    public static Sorts newInstance() {
        //
        return new Sorts();
    }

    public static Sorts newInstance(Sort sort) {
        //
        return new Sorts(sort);
    }

    public static Sorts newInstance(List<Sort> sorts) {
        //
        return new Sorts(sorts);
    }

    public static Sorts newInstance(Sort... sorts) {
        //
        return new Sorts(sorts);
    }

    @SuppressWarnings("java:S2326")
    public static <T> Function<Sort, org.springframework.data.domain.Sort.Order> createOrder() {
        //
        return (Sort sort) ->
                Sort.SortDirection.ASCENDING == sort.getSortDirection()
                        ? org.springframework.data.domain.Sort.Order.asc(sort.getSortingField())
                        : org.springframework.data.domain.Sort.Order.desc(sort.getSortingField());

    }
}