package com.apiai.service.domain.responses.cars;

import com.apiai.service.domain.responses.ResponseObj;
import com.apiai.service.domain.responses.cars.Cars;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 * Created by sindhya on 12/9/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CarsResponse extends ResponseObj {

    List<Cars> cars;

    public List<Cars> getCars() {
        return cars;
    }

    public void setCars(List<Cars> cars) {
        this.cars = cars;
    }
}
