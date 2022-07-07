/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.trail.dynamic;

import io.naraway.accent.domain.trail.DynamicQueryRequest;
import io.naraway.accent.util.query.RdbQueryBuilder;
import io.naraway.accent.util.query.RdbQueryRequest;

import javax.persistence.TypedQuery;
import java.util.List;

public class SampleRdbDynamicQuery extends DynamicQueryRequest<SampleRdbDynamicQuery.Audience> {
    //
    public void execute(RdbQueryRequest<AudienceJpo> request) {
        //
        request.addQueryStringAndClass(genSqlString(), AudienceJpo.class);
        TypedQuery<AudienceJpo> query = RdbQueryBuilder.build(request);

        AudienceJpo audienceJpo = query.getSingleResult();
        setResponse(audienceJpo.toDomain());
    }

    /*
    From here is the sample inner classes.
     */
    static class AudienceJpo {
        public AudienceJpo(Audience audience) {
        }

        public Audience toDomain() {
            return null;
        }

        public static List<Audience> toDomains(List<AudienceJpo> audienceJpos) {
            return null;
        }
    }

    class Audience {
        public Audience(String id) {
        }

        public SampleDocsDynamicQuery.Audience fromJson(String json) {
            return null;
        }
    }
}
