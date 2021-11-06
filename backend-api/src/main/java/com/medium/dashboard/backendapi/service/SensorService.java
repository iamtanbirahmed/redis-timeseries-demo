package com.medium.dashboard.backendapi.service;

import com.medium.dashboard.backendapi.dto.SensorData;
import com.medium.dashboard.backendapi.repository.SensorRepository;
import com.redislabs.redistimeseries.Range;
import com.redislabs.redistimeseries.Value;
import com.redislabs.redistimeseries.information.Info;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public void saveSensorData(SensorData sensorData) {
        this.sensorRepository.save(sensorData);
    }

    public SensorData getSensorData() {
        SensorData sensorData = this.sensorRepository.findOne().orElseThrow(() -> new NoSuchElementException("Could not find any sensor data"));
        return sensorData;
    }

    public Value[] getValuesByRange(String key, Long start, Long end) {
        return this.sensorRepository.getValuesByRange(key, start, end);
    }

    public Range[] getValuesByLabels(String key, String value) {
        return this.sensorRepository.getValuesByLabels(key, value);
    }
}
