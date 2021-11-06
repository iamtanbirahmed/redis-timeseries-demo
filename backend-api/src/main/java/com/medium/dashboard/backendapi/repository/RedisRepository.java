package com.medium.dashboard.backendapi.repository;


import com.redislabs.redistimeseries.Range;
import com.redislabs.redistimeseries.RedisTimeSeries;
import com.redislabs.redistimeseries.Value;
import com.redislabs.redistimeseries.information.Info;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class RedisRepository {
    private final RedisTimeSeries redisTimeSeries;

    public RedisRepository(RedisTimeSeries redisTimeSeries) {
        this.redisTimeSeries = redisTimeSeries;
    }

    void addValue(String key, double value) {
        this.redisTimeSeries.add(key, System.currentTimeMillis() / 1000, value);
    }

    void createTimeSeries(Map<String, String> labels, String key) {
        this.redisTimeSeries.create(key, labels);
    }

    public Boolean keyExists(String key) {
        try {
            return this.redisTimeSeries.info(key) != null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Value[] getValuesByRange(String key, Long start, Long end) {
        return this.redisTimeSeries.range(key, start, end);
    }

    public Range[] getValuesByLabels(String key, String value) {
        return this.redisTimeSeries.mget(true, key + "=" + value);
    }
}
