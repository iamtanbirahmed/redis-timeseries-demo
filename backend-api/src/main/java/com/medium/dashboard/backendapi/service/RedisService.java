package com.medium.dashboard.backendapi.service;

import com.redislabs.redistimeseries.Aggregation;
import com.redislabs.redistimeseries.Range;
import com.redislabs.redistimeseries.RedisTimeSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RedisService {

    private RedisTimeSeries redisTimeSeries;

    public RedisService(RedisTimeSeries redisTimeSeries) {
        this.redisTimeSeries = redisTimeSeries;
    }

    public void testRedis() {

        Map<String, String> labels = new HashMap<>();
        labels.put("country", "US");

//        this.redisTimeSeries.create("cpu1", 60 * 10, labels);
//        this.redisTimeSeries.create("cpu1",  labels);


//        redisTimeSeries.create("cpu1-avg", 60 * 10 /*10min*/, labels);
//        redisTimeSeries.createRule("cpu1", Aggregation.AVG, 60 /*1min*/, "cpu1-avg");

        this.redisTimeSeries.add("cpu1", System.currentTimeMillis() / 1000 /* time sec */, 80.0, labels);
//        Range[] ranges = this.redisTimeSeries.mrange(System.currentTimeMillis() / 1000 - 10 * 60, System.currentTimeMillis() / 1000, Aggregation.AVG, 60, "country=US");
        Range[] ranges = this.redisTimeSeries.mrange(System.currentTimeMillis() / 1000 - 10 * 60, System.currentTimeMillis() / 1000,"country=US");

        for (Range r :
                ranges) {
            String key = r.getKey();
            System.out.println(key);
            Map<String, String> rangeLabels = r.getLabels();
            System.out.println(rangeLabels.toString());


        }

        System.out.println("Loading from cache.." + ranges);
    }


}
