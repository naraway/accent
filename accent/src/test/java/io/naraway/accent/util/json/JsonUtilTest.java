/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.json;

import io.naraway.accent.domain.type.IdName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilTest {
    //
    @Test
    void serialize() {
        //
        List<String> settings = new ArrayList<>();
        settings.add("A");
        settings.add("B");
        Station station = new Station("001", "nextree", settings, new IdName("test", "test"));
        assertNotNull(station);
        System.out.println(JsonUtil.toJson(station));
    }

    @Test
    void serializePretty() {
        //
        List<String> settings = new ArrayList<>();
        settings.add("A");
        settings.add("B");
        Station station = new Station("001", "nextree", settings, new IdName("test", "test"));
        assertNotNull(station);
        System.out.println(JsonUtil.toPrettyJson(station));
    }

    @Test
    void deserialize() {
        //
        String json = "{\"id\":\"001\",\"name\":\"nextree\",\"settings\":[\"A\",\"B\"],\"site\":{\"id\":\"test\",\"name\":\"test\"}}";
        Station station = JsonUtil.fromJson(json, Station.class);
        assertEquals("nextree", station.getName());
    }

    @Test
    void serializeList() {
        //
        List<String> settings = new ArrayList<>();
        settings.add("A");
        settings.add("B");
        Station station1 = new Station("001", "nextree", settings, new IdName("test", "test"));
        Station station2 = new Station("002", "nextree2", settings, new IdName("test2", "test2"));
        List<Station> stations = new ArrayList<>();
        stations.add(station1);
        stations.add(station2);
        assertNotNull(stations);
        System.out.println(JsonUtil.toJson(stations));
    }

    @Test
    void serializePrettyList() {
        //
        List<String> settings = new ArrayList<>();
        settings.add("A");
        settings.add("B");
        Station station1 = new Station("001", "nextree", settings, new IdName("test", "test"));
        Station station2 = new Station("002", "nextree2", settings, new IdName("test2", "test2"));
        List<Station> stations = new ArrayList<>();
        stations.add(station1);
        stations.add(station2);
        assertNotNull(stations);
        System.out.println(JsonUtil.toPrettyJson(stations));
    }

    @Test
    void deserializeList() {
        //
        String json = "[{\"id\":\"001\",\"name\":\"nextree\",\"settings\":[\"A\",\"B\"],\"site\":{\"id\":\"test\",\"name\":\"test\"}}, {\"id\":\"001\",\"name\":\"nextree\",\"settings\":[\"A\",\"B\"],\"site\":{\"id\":\"test\",\"name\":\"test\"}}]";
        List<Station> stations = JsonUtil.fromJsonList(json, Station.class);
        assertEquals(2, stations.size());
    }
}
