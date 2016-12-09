package com.apiai.service.domain;



/**
 * Created by sindhya on 12/3/16.
 */
public class BotMessage {

    String message;
    Location location;

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
}
