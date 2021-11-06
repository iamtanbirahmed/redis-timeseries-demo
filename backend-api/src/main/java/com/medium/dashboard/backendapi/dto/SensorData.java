package com.medium.dashboard.backendapi.dto;

import lombok.*;

import java.util.Map;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SensorData {
    Integer shipId;
    Map<String, Double> temperature;
    Map<String, Double> ultrasonic;
    Boolean motion;
}
