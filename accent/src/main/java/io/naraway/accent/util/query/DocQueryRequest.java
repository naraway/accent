/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.query;

import io.naraway.accent.domain.trail.dynamic.QueryParams;
import lombok.Getter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.List;

@Getter
public class DocQueryRequest<T> {
    //
    protected String queryString;
    protected QueryParams queryParams;
    protected Class<T> clazz;
    private String collectionName;
    private final MongoTemplate mongoTemplate;

    public DocQueryRequest(MongoTemplate mongoTemplate) {
        //
        this.mongoTemplate = mongoTemplate;
    }

    @SuppressWarnings({"java:S1123", "java:S1133"})
    @Deprecated
    public void addQueryStringAndClass(String queryString, Class<T> clazz) {
        //
        this.queryString = queryString;
        this.clazz = clazz;
        this.collectionName = Arrays.stream(this.clazz.getAnnotations())
                .filter(Document.class::isInstance).findFirst()
                .map(annotation -> ((Document) annotation).collection())
                .orElseThrow(() -> new IllegalStateException(String.format("@Document not defined in %s", clazz.getName())));
    }

    public void addQueryParamsAndClass(QueryParams queryParams, Class<T> clazz) {
        //
        this.queryParams = queryParams;
        this.clazz = clazz;
        this.collectionName = Arrays.stream(this.clazz.getAnnotations())
                .filter(Document.class::isInstance).findFirst()
                .map(annotation -> ((Document) annotation).collection())
                .orElseThrow(() -> new IllegalStateException(String.format("@Document not defined in %s", clazz.getName())));
    }

    @SuppressWarnings({"java:S1123", "java:S1133"})
    @Deprecated
    public void addCollectionName(String collectionName) {
        //
        this.collectionName = collectionName;
    }

    public T findOne(Query query) {
        //

        return mongoTemplate.findOne(query, getClazz(), collectionName);
    }

    public long count(String collectionName) {
        //
        return mongoTemplate.count(new Query(), collectionName);
    }

    public List<T> findAll(Query query) {
        //
        return mongoTemplate.find(query, getClazz(), collectionName);
    }

    public long count(Query query) {
        //
        return mongoTemplate.count(query, getClazz(), collectionName);
    }

    public List<T> findAll(Class<T> clazz, String collectionName) {
        //
        return mongoTemplate.findAll(clazz, collectionName);
    }
}
