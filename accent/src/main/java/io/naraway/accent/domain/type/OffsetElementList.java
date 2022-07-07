/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OffsetElementList<T> implements Iterable<T>, Serializable {
    //
    @SuppressWarnings("java:S1948")
    private List<T> results;
    private long totalCount;

    protected OffsetElementList() {
        //
        this.results = new ArrayList<>();
    }

    public OffsetElementList(long totalCount) {
        //
        this();
        this.totalCount = totalCount;
    }

    public OffsetElementList(List<T> results, long totalCount) {
        //
        this.results = results;
        this.totalCount = totalCount;
    }

    @Override
    public Iterator<T> iterator() {
        //
        return results.iterator();
    }

    public int size() {
        //
        return (results != null) ? results.size() : 0;
    }

    public T get(int index) {
        //
        return (results != null) ? results.get(index) : null;
    }

    public void add(T result) {
        //
        results.add(result);
    }

    public boolean isEmpty() {
        //
        return (results == null || results.isEmpty());
    }

    public List<T> getResults() {
        //
        return results;
    }

    public void setResults(List<T> results) {
        //
        this.results = results;
    }

    public long getTotalCount() {
        //
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        //
        this.totalCount = totalCount;
    }
}
