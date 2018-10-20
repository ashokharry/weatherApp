package com.app.poc.weather.model;

import java.util.Objects;

public class WeatherModel {

    private String todaysDate;
    private String cityName;
    private String weatherDescription;
    private String tempretureFahrenheit;
    private String tempretureCelsius;
    private String sunriseTime;
    private String sunsetTime;

    public String getTodaysDate() {
        return todaysDate;
    }

    private void setTodaysDate(String todaysDate) {
        this.todaysDate = todaysDate;
    }

    public String getCityName() {
        return cityName;
    }

    private void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    private void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getTempretureFahrenheit() {
        return tempretureFahrenheit;
    }

    private void setTempretureFahrenheit(String tempretureFahrenheit) {
        this.tempretureFahrenheit = tempretureFahrenheit;
    }

    public String getTempretureCelsius() {
        return tempretureCelsius;
    }

    private void setTempretureCelsius(String tempretureCelsius) {
        this.tempretureCelsius = tempretureCelsius;
    }

    public String getSunriseTime() {
        return sunriseTime;
    }

    private void setSunriseTime(String sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public String getSunsetTime() {
        return sunsetTime;
    }

    private void setSunsetTime(String sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public static Builder builder(){return new Builder();}

    public static final class Builder {

        private WeatherModel weatherModel;

        private Builder(){
            weatherModel= new WeatherModel();
        }

        public Builder withTodaysDate(String todaysDate){
            weatherModel.setTodaysDate(todaysDate);
            return this;
        }

        public Builder withCityName(String cityName){
            weatherModel.setCityName(cityName);
            return this;
        }

        public Builder withWeatherDescription(String weatherDescription){
            weatherModel.setWeatherDescription(weatherDescription);
            return this;
        }

        public Builder withTempretureFahrenheit(String tempretureFahrenheit){
            weatherModel.setTempretureFahrenheit(tempretureFahrenheit);
            return this;
        }

        public Builder withTempretureCelsius(String tempretureCelsius){
            weatherModel.setTempretureCelsius(tempretureCelsius);
            return this;
        }

        public Builder withSunriseTime(String sunriseTime){
            weatherModel.setSunriseTime(sunriseTime);
            return this;
        }

        public Builder withSunsetTime(String sunsetTime){
            weatherModel.setSunsetTime(sunsetTime);
            return this;
        }

        public WeatherModel build(){return weatherModel;}
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherModel that = (WeatherModel) o;
        return Objects.equals(todaysDate, that.todaysDate) &&
                Objects.equals(cityName, that.cityName) &&
                Objects.equals(weatherDescription, that.weatherDescription) &&
                Objects.equals(tempretureFahrenheit, that.tempretureFahrenheit) &&
                Objects.equals(tempretureCelsius, that.tempretureCelsius) &&
                Objects.equals(sunriseTime, that.sunriseTime) &&
                Objects.equals(sunsetTime, that.sunsetTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(todaysDate, cityName, weatherDescription, tempretureFahrenheit, tempretureCelsius, sunriseTime, sunsetTime);
    }
}
