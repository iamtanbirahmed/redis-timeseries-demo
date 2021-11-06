package com.medium.dashboard.backendapi.repository;

import com.medium.dashboard.backendapi.dto.SensorData;
import com.medium.dashboard.backendapi.util.RandomGenerator;
import com.redislabs.redistimeseries.Range;
import com.redislabs.redistimeseries.Value;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class SensorRepository {

    private final RedisRepository redisRepository;

    public SensorRepository(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    public void save(SensorData data) {
        this.saveSensorValues(data.getShipId(), "temperature", data.getTemperature());
        this.saveSensorValues(data.getShipId(), "ultrasonic", data.getUltrasonic());
        this.saveSensorValues(data.getShipId(), "motion", data.getMotion());

    }

    private void saveSensorValues(Integer shipId, String sensorType, Map<String, Double> readings) {
        for (String sensorName : readings.keySet()) {
            String key = sensorType + ":" + sensorName.toUpperCase() + ":" + shipId;
            if (!this.redisRepository.keyExists(key)) {
                System.out.println("Creating new time-series with key:" + key);
                this.redisRepository.createTimeSeries(this.setLabels(shipId, sensorName), key);
            }
            this.redisRepository.addValue(key, readings.get(sensorName));
        }
    }

    private void saveSensorValues(Integer shipId, String sensorType, Boolean motion) {
        String key = sensorType + ":" + shipId;
        if (!this.redisRepository.keyExists(key)) {
            System.out.println("Creating new time-series with key:" + key);
            this.redisRepository.createTimeSeries(this.setLabels(shipId, sensorType), key);
        }
        this.redisRepository.addValue(key, motion ? 1.0 : 0.0);
    }

    private Map<String, String> setLabels(Integer shipId, String sensorName) {
        Map<String, String> labels = new HashMap();
        labels.put("ship", String.valueOf(shipId));
        labels.put("sensor", sensorName.toUpperCase());
        return labels;
    }


    public Optional<SensorData> findOne() {
        HashMap<String, Double> temperature = new HashMap<>();
        temperature.put("DHT22_1", RandomGenerator.generateDoubleValue(0, 1000));
        temperature.put("DHT22_2", RandomGenerator.generateDoubleValue(0, 1000));
        temperature.put("DHT22_3", RandomGenerator.generateDoubleValue(0, 1000));

        HashMap<String, Double> ultrasonic = new HashMap<>();
        ultrasonic.put("HCSR04_1", RandomGenerator.generateDoubleValue(0, 1000));
        ultrasonic.put("HCSR04_2", RandomGenerator.generateDoubleValue(0, 1000));
        ultrasonic.put("HCSR04_3", RandomGenerator.generateDoubleValue(0, 1000));
        return Optional.of(new SensorData(1, temperature, ultrasonic, false));
    }

    public Value[] getValuesByRange(String key, Long start, Long end) {
        key = key.replace('-', ':');
        return this.redisRepository.getValuesByRange(key, start, end);
    }

    public Range[] getValuesByLabels(String key, String value) {
        return this.redisRepository.getValuesByLabels(key, value);
    }
}
