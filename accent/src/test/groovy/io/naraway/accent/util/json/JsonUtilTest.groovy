/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.json

import io.naraway.accent.domain.type.IdName
import io.naraway.accent.util.json.vo.Station
import spock.lang.Specification
import spock.lang.Subject

class JsonUtilTest extends Specification {
    //
    @Subject
    JsonUtil jsonUtil
    Station station

    //
    def setup() {
        def settings = []
        settings.add('A')
        settings.add('B')
        station = new Station('001', 'nextree', settings, new IdName('test', 'test'))
    }

    //
    def 'serialize json'() {
        when:
        def json = JsonUtil.toJson(station)

        then:
        println json
        json == '{"id":"001","name":"nextree","settings":["A","B"],"site":{"id":"test","name":"test"}}'
    }

    //
    def 'serialize pretty json'() {
        when:
        def json = JsonUtil.toPrettyJson(station)

        then:
        println json
        json == '''{
            |  "id" : "001",
            |  "name" : "nextree",
            |  "settings" : [ "A", "B" ],
            |  "site" : {
            |    "id" : "test",
            |    "name" : "test"
            |  }
            |}'''.stripMargin()
    }

    //
    def 'deserialize json'() {
        given:
        def json = '{"id":"001","name":"nextree","settings":["A","B"],"site":{"id":"test","name":"test"}}'

        when:
        def item = JsonUtil.fromJson(json, Station.class)

        then:
        item.name == 'nextree'
    }

    //
    def 'serialize json for list'() {
        given:
        def stations = [station, station]

        when:
        def json = JsonUtil.toJson(stations)

        then:
        println json
        json == '[{"id":"001","name":"nextree","settings":["A","B"],"site":{"id":"test","name":"test"}},{"id":"001","name":"nextree","settings":["A","B"],"site":{"id":"test","name":"test"}}]'
    }

    //
    def 'serialize pretty json for list'() {
        given:
        def stations = [station, station]

        when:
        def json = JsonUtil.toPrettyJson(stations)

        then:
        println json
        json == '''[ {
          |  "id" : "001",
          |  "name" : "nextree",
          |  "settings" : [ "A", "B" ],
          |  "site" : {
          |    "id" : "test",
          |    "name" : "test"
          |  }
          |}, {
          |  "id" : "001",
          |  "name" : "nextree",
          |  "settings" : [ "A", "B" ],
          |  "site" : {
          |    "id" : "test",
          |    "name" : "test"
          |  }
          |} ]'''.stripMargin()
    }

    //
    def 'deserialize json for list'() {
        given:
        def json = '[{"id":"001","name":"nextree","settings":["A","B"],"site":{"id":"test","name":"test"}},{"id":"001","name":"nextree","settings":["A","B"],"site":{"id":"test","name":"test"}}]'

        when:
        def items = JsonUtil.fromJsonList(json, Station.class)

        then:
        items.size() == 2
        items.get(0).name == 'nextree'
        items.get(1).name == 'nextree'
    }
}