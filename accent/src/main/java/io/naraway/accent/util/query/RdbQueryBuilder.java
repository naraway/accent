/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.query;

import com.github.tennaito.rsql.jpa.JpaPredicateVisitor;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLOperators;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import io.naraway.accent.domain.type.Offset;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Set;

public class RdbQueryBuilder {
    //
    private RdbQueryBuilder() {
        //
    }

    public static <T> TypedQuery<T> build(RdbQueryRequest request) {
        //
        CriteriaQuery<T> criteriaQuery = buildCriteriaQuery(request);

        return request.getEntityManager().createQuery(criteriaQuery);
    }

    public static <T> TypedQuery<T> build(RdbQueryRequest<T> request, Offset offset) {
        //
        CriteriaQuery<T> criteriaQuery = buildCriteriaQuery(request, offset);

        return request.getEntityManager().createQuery(criteriaQuery);
    }

    public static <T> TypedQuery<Long> buildForCount(RdbQueryRequest<T> request) {
        //
        CriteriaQuery<Long> criteriaQuery = buildCountCriteriaQuery(request);

        return request.getEntityManager().createQuery(criteriaQuery);
    }

    private static <T> CriteriaQuery<T> buildCriteriaQuery(RdbQueryRequest<T> request) {
        //
        CriteriaBuilder builder = request.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(request.getClazz());

        processWhereClause(request, criteria);

        return criteria;
    }

    private static <T> CriteriaQuery<T> buildCriteriaQuery(RdbQueryRequest<T> request, Offset offset) {
        //
        CriteriaBuilder builder = request.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(request.getClazz());

        Root<T> root = processWhereClause(request, criteria);

        if (offset.ascendingSort()) {
            criteria.orderBy(builder.asc(root.get(offset.getSortingField())));
        } else {
            criteria.orderBy(builder.desc(root.get(offset.getSortingField())));
        }

        return criteria;
    }

    private static <T> CriteriaQuery<Long> buildCountCriteriaQuery(RdbQueryRequest<T> request) {
        //
        CriteriaBuilder builder = request.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<T> root = processWhereClause(request, criteria);

        criteria.select(builder.count(root));

        return criteria;
    }

    private static <T> Root<T> processWhereClause(RdbQueryRequest<T> request, CriteriaQuery<?> criteria) {
        //
        Root<T> root = criteria.from(request.getClazz());

        RSQLVisitor<Predicate, EntityManager> visitor = new JpaPredicateVisitor<>().defineRoot(root);

        Set<ComparisonOperator> operators = RSQLOperators.defaultOperators();
        operators.add(new ComparisonOperator("=like=", false));

        Node rootNode = new RSQLParser(operators).parse(request.getQueryString());

        Predicate predicate = rootNode.accept(visitor, request.getEntityManager());

        criteria.where(predicate);
        return root;
    }

    @SuppressWarnings({"java:S1123", "java:S1133"})
    @Deprecated(since = "3.0.0", forRemoval = true)
    public static <T> TypedQuery<Long> buildCountQuery(RdbQueryRequest<T> request) {
        //
        return buildForCount(request);
    }

    @SuppressWarnings({"java:S1123", "java:S1133"})
    @Deprecated(since = "3.0.0", forRemoval = true)
    public static <T> TypedQuery<T> buildWithPageable(RdbQueryRequest request, Offset offset) {
        //
        return build(request, offset);
    }
}
