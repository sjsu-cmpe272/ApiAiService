package com.apiai.service.serviceintegration;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import com.apiai.service.apiai.ApiAiConstants;
import com.apiai.service.domain.responses.cars.CarsResponse;
import com.apiai.service.domain.responses.hotel.HotelsResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by sindhya on 12/9/16.
 */
public class CarsApiServiceIntegration {

  public CarsResponse integrateHotelService(String action, HashMap<String,JsonElement> param) {

    HttpClient client = HttpClientBuilder.create().build();
    HttpPost request = new HttpPost();

    HttpResponse response;
    StringBuffer result;
    CarsResponse info = null;
        
        /* action check */
    if ("blah".equalsIgnoreCase(action)) {
      JSONObject obj = new JSONObject();
      JsonElement location = param.get("pickupDropoff");
      JsonElement fromDate = param.get("pickupDateTime");
      JsonElement toDate = param.get("dropOffDateTime");
      JsonElement driverAge = param.get("driverAge");

      request = new HttpPost(ApiAiConstants.CARS_HOST + ApiAiConstants.CARS_ENDPOINT);
      obj.put("pickupDropoff", location);
      obj.put("pickupDateTime", fromDate);
      obj.put("dropOffDateTime", toDate);
      obj.put("driverAge", toDate);

      try {
        StringEntity input = new StringEntity(obj.toJSONString());
        input.setContentType("application/json");
        request.setEntity(input);
        response = client.execute(request);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        result = new StringBuffer();
        String line = "";
        while ((line = br.readLine()) != null) {
          result.append(line);
        }
        info = new Gson().fromJson(result.toString(), CarsResponse.class);


        info.setType("cars");
      } catch (Exception e) {
        e.printStackTrace();
      }

    }

    return info;
  }
}
