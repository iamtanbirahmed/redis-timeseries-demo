package com.medium.dashboard.backendapi.controller;


import com.medium.dashboard.backendapi.dto.SensorData;
import com.medium.dashboard.backendapi.service.SensorService;
import com.redislabs.redistimeseries.Range;
import com.redislabs.redistimeseries.Value;
import com.redislabs.redistimeseries.information.Info;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/v0.0.1")
public class SensorController {
    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveSensorData(@RequestBody SensorData sensorData) {
        sensorService.saveSensorData(sensorData);
        return ResponseEntity.ok("Data Saved");
    }

    /**
     * GET values by key and time range
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<Value[]> getValuesByKeyRange(@PathParam("key") String key, @PathParam("start") Long start, @PathParam("end") Long end) {
        return ResponseEntity.ok(sensorService.getValuesByRange(key, start, end));
    }


    @GetMapping("/get-by-label")
    public ResponseEntity<Range[]> getValuesByLabels(@PathParam("key") String key, @PathParam("value") String value) {
        return ResponseEntity.ok(sensorService.getValuesByLabels(key, value));
    }


}
