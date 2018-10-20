package com.app.poc.weather.controller;

import com.app.poc.weather.model.WeatherModel;
import com.app.poc.weather.service.IWeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class WeatherController {

    private IWeatherService weatherService;

    public WeatherController(IWeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping(value="/weather-details/city/{city}")
    Mono<WeatherModel> weatherLookup(@PathVariable("city") String city){
        return weatherService.getWeatherData(city);
    }
}
