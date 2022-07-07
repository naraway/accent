package io.naraway.accent.util.query;

import io.naraway.accent.SampleApp;
import io.naraway.accent.domain.key.stage.ActorKey;
import io.naraway.accent.domain.key.tenant.TenantType;
import io.naraway.accent.domain.trail.dynamic.*;
import io.naraway.accent.domain.type.CodeName;
import io.naraway.accent.util.json.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@SpringBootTest(classes = {SampleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
class DocQueryBuilderTest {
    //
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final SampleDocDynamicQuery.FooDoc doc = new SampleDocDynamicQuery.FooDoc(
            UUID.randomUUID().toString(),
            "FOO",
            new CodeName("foo", "FOO"),
            Map.of("str", "str_val", "num", 3, "bool", true),
            ActorKey.sample()
    );

    @BeforeAll
    public void initData() {
        //
        mongoTemplate.insert(doc, "foo");
    }

    @AfterAll
    public void clearData() {
        //
        mongoTemplate.remove(doc, "foo");
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class QueryConditionCase {
        //
        String name;
        QueryParams queryParams;
        public static QueryConditionCase of(String name, QueryParams queryParams) {
            //
            return new QueryConditionCase(name, queryParams);
        }
    }

    public List<QueryConditionCase> queryConditions() {
        //
        return Arrays.asList(
                QueryConditionCase.of("Equal Simple Field", QueryParams.newInstance(new QueryParam("name", Operator.Equal, "FOO", Connector.End))),
                QueryConditionCase.of("Equal Nested Field", QueryParams.newInstance(new QueryParam("codeName.code", Operator.Equal, "foo", Connector.End))),
                QueryConditionCase.of("Equal Map Nested Field(string)", QueryParams.newInstance(new QueryParam("map.str", Operator.Equal, "str_val", Connector.End))),
                QueryConditionCase.of("Equal Map Nested Field(number)", QueryParams.newInstance(new QueryParam("map.num", Operator.Equal, "3", Connector.End))),
                QueryConditionCase.of("Equal Map Nested Field(boolean)", QueryParams.newInstance(new QueryParam("map.bool", Operator.Equal, "true", Connector.End))),
                QueryConditionCase.of("Equal Object Nested Field", QueryParams.newInstance(new QueryParam("object._id", Operator.Equal, ActorKey.sample().getId(), Connector.End))),
                QueryConditionCase.of("Not Equal", QueryParams.newInstance(new QueryParam("map.str", Operator.NotEqual, "@", Connector.End))),
                QueryConditionCase.of("In", QueryParams.newInstance(new QueryParam("map.num", Operator.In, JsonUtil.toJson(Arrays.asList(3, 4)), Connector.End))),
                QueryConditionCase.of("In String Array", QueryParams.newInstance(new QueryParam("map.str", Operator.In, JsonUtil.toJson(Arrays.asList("str_val", "str_val1")), Connector.End))),
                QueryConditionCase.of("Not In", QueryParams.newInstance(new QueryParam("map.num", Operator.NotIn, JsonUtil.toJson(Arrays.asList(2, 4)), Connector.End))),
                QueryConditionCase.of("Like", QueryParams.newInstance(new QueryParam("object._id", Operator.Like, "@", Connector.End))),
                QueryConditionCase.of("Less Than", QueryParams.newInstance(new QueryParam("map.num", Operator.LessThan, "4", Connector.End))),
                QueryConditionCase.of("Less Than Or Equal", QueryParams.newInstance(new QueryParam("map.num", Operator.LessThanOrEqual, "3.0", Connector.End))),
                QueryConditionCase.of("Less Than Or Equal String", QueryParams.newInstance(new QueryParam("map.str", Operator.LessThanOrEqual, "str_val_added", Connector.End))),
                QueryConditionCase.of("Or", QueryParams.newInstance()
                        .add(new QueryParam("object._id", Operator.Like, "_", Connector.Or))                      // false condition
                        .add(new QueryParam("object.type", Operator.Equal, TenantType.Actor.name(), Connector.End))),    // true condition
                QueryConditionCase.of("And", QueryParams.newInstance()
                                .add(new QueryParam("object._id", Operator.Like, "_", Connector.Or))                      // false condition
                                .add(new QueryParam("object._id", Operator.Like, "@", Connector.And))                     // true condition
                                .add(new QueryParam("object.type", Operator.Equal, TenantType.Actor.name(), Connector.End)))     // true condition
        );
    }

    @ParameterizedTest
    @MethodSource({"queryConditions"})
    void queryTest(QueryConditionCase queryConditionCase) {
        //
        DocQueryRequest<SampleDocDynamicQuery.FooDoc> request = new DocQueryRequest<>(mongoTemplate);
        request.addQueryParamsAndClass(queryConditionCase.getQueryParams(), SampleDocDynamicQuery.FooDoc.class);
        //
        Query query = DocQueryBuilder.build(request);
        //
        SampleDocDynamicQuery.FooDoc result = request.findOne(query);

        Assertions.assertNotNull(result, String.format("Fail case : [%s]", queryConditionCase.getName()));
    }
}
