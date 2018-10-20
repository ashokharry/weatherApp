package com.app.poc.weather.service.impl;

import com.app.poc.weather.model.WeatherModel;
import com.app.poc.weather.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService implements IWeatherService {

    private WebClient client;

    @Value("${openweathermap.key}")
    private String key;

    @Autowired
    public WeatherService() {
        this.client = WebClient.create("http://api.openweathermap.org/data/2.5/weather");
    }

    public WeatherService(WebClient client) {
        this.client = client;
    }

    @Override
    public Mono<WeatherModel> getWeatherData(String city){

       return client.get()
               .uri("?q="+city+"&appid="+key)
               .header("Content-Type", MediaType.APPLICATION_JSON.toString())
               .exchange()
               .flatMap(WeatherService::apply);

    }

    private static Mono<? extends WeatherModel> apply(ClientResponse res){
        if(res.statusCode().value() != HttpStatus.OK.value()){
            return Mono.justOrEmpty(WeatherModel.builder().build());
        }

        return res.bodyToMono(Map.class).map(map -> {

            return WeatherModel.builder()
                     .withTodaysDate(String.valueOf( Instant.ofEpochSecond((Integer)map.get("dt")).atZone(ZoneId.of("GMT-4")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
                     .withCityName((String) map.get("name"))
                     .withWeatherDescription((String) ((Map)((List)map.get("weather")).get(0)).get("description"))
                     .withTempretureFahrenheit(String.valueOf( (((Double)(((Map)map.get("main")).get("temp")) - 273.15)* 9/5)+32))
                     .withTempretureCelsius(String.valueOf( (Double)(((Map)map.get("main")).get("temp")) - 273.15))
                     .withSunriseTime(String.valueOf( ((Map)map.get("sys")).get("sunrise")))
                     .withSunsetTime(String.valueOf( ((Map)map.get("sys")).get("sunset"))).build();

        });
    }

}
