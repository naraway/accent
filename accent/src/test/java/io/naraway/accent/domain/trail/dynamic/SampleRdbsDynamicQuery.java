/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.trail.dynamic;

import io.naraway.accent.domain.trail.DynamicQueryRequest;
import io.naraway.accent.domain.type.Offset;
import io.naraway.accent.util.query.RdbQueryBuilder;
import io.naraway.accent.util.query.RdbQueryRequest;

import javax.persistence.TypedQuery;
import java.util.List;

public class SampleRdbsDynamicQuery extends DynamicQueryRequest<List<SampleRdbsDynamicQuery.Audience>> {
    //
    public void execute(RdbQueryRequest<AudienceJpo> request) {
        //
        request.addQueryStringAndClass(genSqlString(), AudienceJpo.class);
        Offset offset = getOffset();
        TypedQuery<AudienceJpo> query = RdbQueryBuilder.build(request, offset);
        query.setFirstResult(offset.getOffset());
        query.setMaxResults(offset.getLimit());

        List<AudienceJpo> audienceJpos = query.getResultList();
        setResponse(AudienceJpo.toDomains(audienceJpos));

        if (getOffset().isTotalCountRequested()) {
            TypedQuery<Long> countQuery = RdbQueryBuilder.buildForCount(request);
            long totalCount = countQuery.getSingleResult();

            Offset countableOffset = getOffset();
            countableOffset.setTotalCount(totalCount);
            setOffset(countableOffset);
        }
    }

    /*
    From here is the sample inner classes.
     */
    static class AudienceJpo {
        //
        public AudienceJpo(Audience audience) {
        }

        public static List<Audience> toDomains(List<AudienceJpo> audienceJpos) {
            return null;
        }
    }

    class Audience {
        //
        public Audience(String id) {
        }

        public SampleDocsDynamicQuery.Audience fromJson(String json) {
            return null;
        }
    }
}
