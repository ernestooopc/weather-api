package com.weather.model;

import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Weather implements Serializable {
    private String datetime;
    private double tempmax;
    private double tempmin;
    private double temp;
    private double humidity;
    private double windspeed;
    private double visibility;
    private double solarradiation;
    private String description; // Agregar este campo para la descripción
}
