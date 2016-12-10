package com.apiai.service.serviceintegration;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import com.apiai.service.apiai.ApiAiConstants;
import com.apiai.service.domain.responses.hotel.HotelsResponse;
import com.apiai.service.domain.responses.weather.WeatherResponse;
import com.apiai.service.domain.responses.yelp.BusinessRegion;
import com.apiai.service.domain.responses.yelp.YelpResponse;

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
public class HotelApiServiceIntegration {

    public HotelsResponse integrateHotelService(String action, HashMap<String,JsonElement> param) {
       
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost();

        HttpResponse response;
        StringBuffer result;
        HotelsResponse info =null;
        
        /* action check */
            JSONObject obj = new JSONObject();
            JsonElement location=param.get("geo-city");
            JsonElement fromDate=param.get("checkin");
            JsonElement toDate=param.get("checkout");

            request = new HttpPost(ApiAiConstants.HOTEL_HOST +  ApiAiConstants.HOTEL_ENDPOINT);
            obj.put("location",location);
            obj.put("fromDate",fromDate);
            obj.put("toDate", toDate);

            try {
                StringEntity input = new StringEntity(obj.toJSONString());
                input.setContentType("application/json");
                request.setEntity(input);
                response = client.execute(request);
                BufferedReader br=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                result = new StringBuffer();
                String line = "";
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                info = new Gson().fromJson(result.toString(), HotelsResponse.class);

                info.setType("hotel");
            }catch (Exception e) {
                e.printStackTrace();
            }

        return info;
    }
}
