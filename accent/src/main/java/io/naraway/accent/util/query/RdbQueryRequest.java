/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.query;

import lombok.Getter;

import javax.persistence.EntityManager;

@Getter
public class RdbQueryRequest<T> {
    //
    private String queryString;
    private Class<T> clazz;
    private final EntityManager entityManager;

    public RdbQueryRequest(EntityManager entityManager) {
        //
        this.entityManager = entityManager;
    }

    public void addQueryStringAndClass(String queryString, Class<T> clazz) {
        //
        this.queryString = queryString;
        this.clazz = clazz;
    }
}
