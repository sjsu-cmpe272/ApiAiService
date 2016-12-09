package com.apiai.service.domain.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by sindhya on 11/26/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherForecastCity {

    @JsonProperty("name")
    private String name;

    @JsonProperty("coord")
    private Map<String,String> coord;

    @JsonProperty("country")
    private String country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getCoord() {
        return coord;
    }

    public void setCoord(Map<String, String> coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
