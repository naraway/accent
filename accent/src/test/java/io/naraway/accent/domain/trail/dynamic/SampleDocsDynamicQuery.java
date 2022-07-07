/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.trail.dynamic;

import io.naraway.accent.domain.trail.DynamicQueryRequest;
import io.naraway.accent.domain.type.Offset;
import io.naraway.accent.util.query.DocQueryBuilder;
import io.naraway.accent.util.query.DocQueryRequest;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class SampleDocsDynamicQuery extends DynamicQueryRequest<List<SampleDocsDynamicQuery.Audience>> {
    //
    public void execute(DocQueryRequest<AudienceDoc> request) {
        //
        request.addQueryStringAndClass(genSqlString(), AudienceDoc.class);
        request.addCollectionName("AUDIENCE");
        Query query = DocQueryBuilder.build(request, getOffset());

        List<AudienceDoc> audienceDocs = request.findAll(query);
        setResponse(AudienceDoc.toDomains(audienceDocs));

        if (getOffset().isTotalCountRequested()) {
            long count = request.count(query);
            Offset offset = getOffset();
            offset.setTotalCount(count);
        }
    }

    /*
    From here is the sample inner classes.
     */
    @Document(collection = "AUDIENCE")
    static class AudienceDoc {
        //
        public Audience toDomain() {
            //
            return null;
        }

        public static List<Audience> toDomains(List<AudienceDoc> audienceDocs) {
            //
            return null;
        }
    }

    class Audience {
        //
        public Audience(String id) {
            //
        }

        public Audience fromJson(String json) {
            //
            return null;
        }
    }
}
