package com.apiai.service.domain.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by sindhya on 11/26/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherForecastList {

    @JsonProperty("dt")
    private Integer date;

    @JsonProperty("main")
    private Measures measures;

    @JsonProperty("weather")
    private List<Weather> weather;

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Measures getMeasures() {
        return measures;
    }

    public void setMeasures(Measures measures) {
        this.measures = measures;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
}
