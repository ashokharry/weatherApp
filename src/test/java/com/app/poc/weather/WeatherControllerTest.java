package com.app.poc.weather;

import com.app.poc.weather.model.WeatherModel;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherControllerTest {

	private WireMockServer wireMockServer;

	private WeatherModel weatherModel;

	@Autowired
	private WebTestClient webTestClient;

	@Before
	public void setUp() {

		weatherModel.builder().withTodaysDate("1540027800")
				.withCityName("Hong Kong").withWeatherDescription("few clouds").withTempretureFahrenheit("298.52")
				.withTempretureCelsius("298.52").withSunriseTime("1539987700").withSunsetTime("1540029255").build();

		wireMockServer = new WireMockServer(options().dynamicPort());
		wireMockServer.start();
		configureFor(wireMockServer.port());

		stubFor(get(urlMatching("/weather-details/city/*")).willReturn(aResponse()
				.withHeader("Content-Type","application/json")
				.withStatus(200)
				.withBody("{\n" +
						"    \"todaysDate\": \"1540027800\",\n" +
						"    \"cityName\": \"Hong Kong\",\n" +
						"    \"weatherDescription\": \"few clouds\",\n" +
						"    \"tempretureFahrenheit\": \"298.52\",\n" +
						"    \"tempretureCelsius\": \"298.52\",\n" +
						"    \"sunriseTime\": \"1539987700\",\n" +
						"    \"sunsetTime\": \"1540029255\"\n" +
						"}")));
	}

	@After
	public void tearDown(){wireMockServer.stop();}

	@Test
	public void shouldReturnResponseIf200() {

		this.webTestClient.get()
				.uri("/weather-details/city/Hong Kong")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.consumeWith(response ->
						Assertions.assertThat(response.getResponseBody()).isNotNull());

	}
}
