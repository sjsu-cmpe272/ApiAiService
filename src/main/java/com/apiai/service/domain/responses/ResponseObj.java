package com.apiai.service.domain.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by sindhya on 12/3/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseObj {

    @JsonProperty("type")
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
