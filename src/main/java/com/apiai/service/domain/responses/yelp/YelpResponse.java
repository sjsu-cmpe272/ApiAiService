package com.apiai.service.domain.responses.yelp;


import com.apiai.service.domain.responses.ResponseObj;
import com.apiai.service.domain.responses.yelp.Business;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by sindhya on 12/3/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YelpResponse extends ResponseObj {

    @JsonProperty("data")
    List<Business> data;

    public List<Business> getData() {
        return data;
    }

    public void setData(List<Business> data) {
        this.data = data;
    }
}
