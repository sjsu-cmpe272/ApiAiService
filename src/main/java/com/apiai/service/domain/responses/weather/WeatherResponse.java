package com.apiai.service.domain.responses.weather;

import com.apiai.service.domain.responses.ResponseObj;
import com.apiai.service.domain.responses.weather.WeatherObj;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by sindhya on 12/3/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WeatherResponse extends ResponseObj {

    @JsonProperty("data")
    WeatherObj data;

    public WeatherObj getData() {
        return data;
    }

    public void setData(WeatherObj data) {
        this.data = data;
    }

}
