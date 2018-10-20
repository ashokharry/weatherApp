package com.app.poc.weather;

import com.app.poc.weather.model.WeatherModel;
import com.app.poc.weather.service.impl.WeatherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherServiceTest {

	@Mock
	private WebClient webClient;

	@Mock
	private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

	@Mock
	private WebClient.RequestHeadersSpec requestHeadersSpec;

	private WeatherService weatherService;

	@Before
	public void setUp() {
		weatherService = new WeatherService(webClient);

		when(webClient.get()).thenReturn(requestHeadersUriSpec);
		when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
		when(requestHeadersSpec.header(anyString(), anyString())).thenReturn(requestHeadersSpec);
	}

	@Test
	public void shouldReturnEmptyResponseIfError() {

		ClientResponse clientResponse = ClientResponse.create(HttpStatus.NOT_FOUND).header("Content-Type","application/json").build();

		Mono<ClientResponse> result = Mono.just(clientResponse);

		when(requestHeadersSpec.exchange()).thenReturn(result);

		StepVerifier.create(weatherService.getWeatherData("London")).expectNextMatches(res-> {
			return res.equals(WeatherModel.builder().build());
		}).expectComplete()
				.verify();
	}

	@Test
	public void shouldReturnWeatherDetailsResponseIf200() {

		String data = "{\"coord\":{\"lon\":-0.13,\"lat\":51.51},\n" +
				"        \"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\n" +
				"        \"base\":\"stations\",\n" +
				"            \"main\":{\"temp\":280.32,\"pressure\":1028,\"humidity\":81,\"temp_min\":278.15,\"temp_max\":283.15},\n" +
				"        \"visibility\":10000,\n" +
				"            \"wind\":{\"speed\":1.5,\"deg\":350},\n" +
				"        \"clouds\":{\"all\":20},\"dt\":1539982200,\"sys\":{\"type\":1,\"id\":5093,\n" +
				"            \"message\":0.0032,\"country\":\"GB\",\"sunrise\":1539930756,\"sunset\":1539968232},\"id\":2643743,\"name\":\"London\",\"cod\":200}";
		ClientResponse clientResponse = ClientResponse.create(HttpStatus.OK).header("Content-Type","application/json").body(data).build();

		Mono<ClientResponse> result = Mono.just(clientResponse);

		when(requestHeadersSpec.exchange()).thenReturn(result);

		StepVerifier.create(weatherService.getWeatherData("London")).expectNextMatches(res-> {
			return res.getCityName().equals("London");
		}).expectComplete()
				.verify();
	}
}
