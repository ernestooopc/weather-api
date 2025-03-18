package com.weather.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.model.Weather;
import org.slf4j.Logger;

@RestController
@RequestMapping("/")
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);


    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;
    

    @GetMapping("clima/{cityCode}")
    @Cacheable(value = "weather_single", key = "#cityCode")
    public Weather getWeather(@PathVariable String cityCode) throws JsonProcessingException{
        logger.info("Obteniendo datos de la API externa para: " + cityCode);

        String url = apiUrl + cityCode + "/?key="  + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        String data = restTemplate.getForObject(url, String.class);
        ObjectMapper objetcMapper = new ObjectMapper();
        JsonNode rootNode = objetcMapper.readTree(data);
        JsonNode today = rootNode.path("days").get(0);

        String datetime = today.get("datetime").asText();
        double tempmax = fahrenheitToCelsius(today.get("tempmax").asDouble());
        double tempmin = fahrenheitToCelsius(today.get("tempmin").asDouble());
        double temp = fahrenheitToCelsius(today.get("temp").asDouble());
        double humidity = today.get("humidity").asDouble();
        double windspeed = today.get("windspeed").asDouble();
        double visibility = today.get("visibility").asDouble();
        double solarradiation = today.get("solarradiation").asDouble();
        String description = today.get("description").asText();
        Weather weather = new Weather(datetime, tempmax, tempmin, temp, humidity, windspeed, visibility, solarradiation, description);

        logger.info("Datos guardados en cache para: " + cityCode);

        return weather;       

       
    }


    @GetMapping("clima/{cityCode}/all")
    @Cacheable(value = "weather", key = "#cityCode")
    public List<Weather> getAllWeather(@PathVariable String cityCode)throws JsonProcessingException{

        String url = apiUrl + cityCode + "/?key="  + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        String data = restTemplate.getForObject(url, String.class);
        ObjectMapper objetcMapper = new ObjectMapper();
        JsonNode rootNode = objetcMapper.readTree(data);

        List<Weather> result = new ArrayList<>();
        JsonNode days = rootNode.path("days");
        for (JsonNode day : days) {

            String datetime = day.get("datetime").asText();
            double tempmax = fahrenheitToCelsius(day.get("tempmax").asDouble());
            double tempmin = fahrenheitToCelsius(day.get("tempmin").asDouble());
            double temp = fahrenheitToCelsius(day.get("temp").asDouble());
            double humidity = day.get("humidity").asDouble();
            double windspeed = day.get("windspeed").asDouble();
            double visibility = day.get("visibility").asDouble();
            double solarradiation = day.get("solarradiation").asDouble();
            String description = day.get("description").asText();
            result.add(new Weather(datetime, tempmax, tempmin, temp, humidity, windspeed, visibility, solarradiation, description));
            
        }
        return result;

    }

    public static double fahrenheitToCelsius(double fahrenheit){
        return (fahrenheit - 32) * (5.0 / 9.0);
    }


    
}
