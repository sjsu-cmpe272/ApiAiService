package com.apiai.service.domain;


import ai.api.model.AIContext;
import ai.api.model.AIOutputContext;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by sindhya on 12/3/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class BotMessage {

    String message;
    Location location;
    //List<AIContext> context;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /*public List<AIContext> getContext() {
        return context;
    }

    public void setContext(List<AIContext> context) {
        this.context = context;
    }*/
}
