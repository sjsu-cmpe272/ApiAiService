package com.apiai.service.domain.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by sindhya on 11/26/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherForecast {

    @JsonProperty("city")
    private WeatherForecastCity forecastCityData;

    @JsonProperty("cod")
    private String code;

    @JsonProperty("list")
    private List<WeatherForecastList> weatherMeasures;

    public WeatherForecastCity getForecastCityData() {
        return forecastCityData;
    }

    public void setForecastCityData(WeatherForecastCity forecastCityList) {
        this.forecastCityData = forecastCityList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<WeatherForecastList> getWeatherMeasures() {
        return weatherMeasures;
    }

    public void setWeatherMeasures(List<WeatherForecastList> weatherMeasures) {
        this.weatherMeasures = weatherMeasures;
    }
}
