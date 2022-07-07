/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.query;

import com.github.rutledgepaulv.qbuilders.builders.GeneralQueryBuilder;
import com.github.rutledgepaulv.qbuilders.conditions.Condition;
import com.github.rutledgepaulv.qbuilders.visitors.MongoVisitor;
import com.github.rutledgepaulv.rqe.pipes.QueryConversionPipeline;
import io.naraway.accent.domain.trail.dynamic.QueryParams;
import io.naraway.accent.domain.type.Offset;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.nonNull;

public class DocQueryBuilder {
    //
    private static final QueryConversionPipeline pipeline = QueryConversionPipeline.defaultPipeline();

    private DocQueryBuilder() {
        //
    }

    @SuppressWarnings({"java:S1123", "java:S1133"})
    @Deprecated
    private static Criteria buildCriteria(String queryString, Class<?> clazz) {
        //
        Condition<GeneralQueryBuilder> condition = pipeline.apply(queryString, clazz);
        return condition.query(new MongoVisitor());
    }

    private static Criteria buildCriteria(QueryParams queryParams) {
        //
        return queryParams.toMongoCriteria();
    }

    @SuppressWarnings("java:S1874")
    public static Query build(DocQueryRequest request) {
        //
        AtomicReference<Criteria> criteria = new AtomicReference<>();
        Optional.ofNullable(request.getQueryParams())
                .ifPresentOrElse(
                        queryParams -> criteria.set(buildCriteria(queryParams)),
                        () -> criteria.set(buildCriteria(isEmpty(request.getQueryString()), request.getClazz()))
                );

        return new Query(criteria.get());
    }

    public static Query build(DocQueryRequest request, Offset offset) {
        //
        Query query = build(request);
        query = fetchOffset(query, offset);
        query = fetchSort(query, offset);
        return query;
    }

    private static Query fetchOffset(Query query, Offset offset) {
        //
        if (nonNull(offset) && offset.getOffset() >= 0 && offset.getLimit() != 0) {
            return query.with(PageRequest.of(offset.page(), offset.getLimit()));
        }
        return query;
    }

    private static Query fetchSort(Query query, Offset offset) {
        //
        if (nonNull(offset) && nonNull(offset.getSortingField()) && !offset.getSortingField().isEmpty()) {
            Sort.Direction direction = offset.ascendingSort() ? Sort.Direction.ASC : Sort.Direction.DESC;
            return query.with(Sort.by(direction, offset.getSortingField()));
        }
        return query;
    }

    private static String isEmpty(String queryString) {
        //
        if (queryString == null || queryString.isEmpty()) {
            return "id!=*";
        }
        return queryString;
    }
}
