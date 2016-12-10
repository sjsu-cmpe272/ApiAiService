package com.apiai.service.domain.responses;

import ai.api.model.AIOutputContext;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Created by sindhya on 12/3/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseObj {

    @JsonProperty("type")
    String type;

    @JsonProperty("speech")
    String speech;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }


}
