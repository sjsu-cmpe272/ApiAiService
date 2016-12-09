package com.apiai.service.domain.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by sindhya on 11/26/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeather {

    @JsonProperty("coord")
    private Map<String,String> coordMap;

    @JsonProperty("weather")
    private List<Weather> weather;

    @JsonProperty("name")
    private String place;

    @JsonProperty("cod")
    private String code;

    @JsonProperty("main")
    private Measures measures;

    public Map<String, String> getCoordMap() {
        return coordMap;
    }

    public void setCoordMap(Map<String, String> coordMap) {
        this.coordMap = coordMap;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Measures getMeasures() {
        return measures;
    }

    public void setMeasures(Measures measures) {
        this.measures = measures;
    }
}
