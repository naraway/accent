/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.trail.dynamic;

import io.naraway.accent.domain.trail.DynamicQueryRequest;
import io.naraway.accent.domain.type.CodeName;
import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.query.DocQueryBuilder;
import io.naraway.accent.util.query.DocQueryRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;

import javax.persistence.Id;
import java.util.Map;
import java.util.Objects;

public class SampleDocDynamicQuery extends DynamicQueryRequest<SampleDocDynamicQuery.Foo> {
    //
    public void execute(DocQueryRequest<FooDoc> request) {
        //
        request.addQueryStringAndClass(genSqlString(), FooDoc.class);
        request.addCollectionName("foo");
        Query query = DocQueryBuilder.build(request);


        FooDoc audienceDoc = request.findOne(query);
        setResponse(audienceDoc.toDomain());
    }

    public void executeWithQueryParams(DocQueryRequest<FooDoc> request) {
        //
        request.addQueryParamsAndClass(getQueryParams(), FooDoc.class);
        Query query = DocQueryBuilder.build(request);


        FooDoc audienceDoc = request.findOne(query);
        setResponse(audienceDoc.toDomain());
    }

    /*
    From here is the sample inner classes.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Document(collection = "foo")
    public static class FooDoc implements JsonSerializable {
        //
        @Id
        private String id;
        private String name;
        private CodeName codeName;
        private Map<String, Object> map;
        private Object object;

        public Foo toDomain() {
            Foo domain = new Foo();
            BeanUtils.copyProperties(this, domain);
            return domain;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Foo implements JsonSerializable {
        //
        private String id;
        private String name;
        private CodeName codeName;
        private Map<String, Objects> map;
        private Object object;
    }
}
