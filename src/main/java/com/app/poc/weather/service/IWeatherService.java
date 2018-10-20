package com.app.poc.weather.service;

import com.app.poc.weather.model.WeatherModel;
import reactor.core.publisher.Mono;

public interface IWeatherService {
    Mono<WeatherModel> getWeatherData(String City);
}
