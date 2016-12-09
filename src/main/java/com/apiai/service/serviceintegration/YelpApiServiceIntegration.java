package com.apiai.service.serviceintegration;

import com.apiai.service.apiai.ApiAiConstants;
import com.apiai.service.domain.responses.yelp.YelpResponse;
import com.apiai.service.domain.responses.yelp.BusinessRegion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by sindhya on 12/3/16.
 */
public class YelpApiServiceIntegration {
    public YelpResponse integrateYelpService(String action, HashMap<String,JsonElement> param, String speech,String latlong){

        YelpResponse yelpResponse=new YelpResponse();
        BusinessRegion businessRegion=new BusinessRegion();
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost();

        HttpResponse response;
        StringBuffer result=null;
        if(action.equals("search-restaurant")) {


            String yelp_location=null;
            String yelp_term=null;
            String yelp_latlong=null;
            StringBuffer yelp_category=new StringBuffer();

            JsonElement location=param.get("location");
            JsonElement cuisine=param.get("cuisine");
            JsonElement restaurant=param.get("restaurant");

            if (StringUtils.isEmpty(location)){

                if(!StringUtils.isEmpty(latlong)) {
                    yelp_latlong = latlong;
                }
                else{
                    yelp_location="SanJose";
                }
            }else{
                yelp_location=location.getAsString().replace(" ","");
            }

            if(!StringUtils.isEmpty(cuisine)){
                yelp_category.append(cuisine.getAsString().toLowerCase());
            }

            if(!StringUtils.isEmpty(restaurant)){
                if (yelp_category.length()>0)
                    yelp_category.append(",");
                yelp_category.append(restaurant.getAsString().toLowerCase());
            }
            yelp_term="restaurant";

            JSONObject obj = new JSONObject();

            if(yelp_category.length()==0) {
                request = new HttpPost("http://" + ApiAiConstants.YELP_HOST + ApiAiConstants.YELP_ENDPOINT);

                if(StringUtils.isEmpty(yelp_location)) {
                    obj.put("latlong", yelp_latlong);

                }else{
                    obj.put("location",yelp_location);
                }
                obj.put("term",yelp_term);
            }
            else{

                if(StringUtils.isEmpty(yelp_location)) {
                    obj.put("latlong", yelp_latlong);
                }else{
                    obj.put("location",yelp_location);
                }

                obj.put("term",yelp_term);
                if (yelp_category.length() >0)
                    obj.put("filter",yelp_category.toString());

                request=new HttpPost("http://"+ApiAiConstants.YELP_HOST+ApiAiConstants.YELP_ENDPOINT);
            }

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
                System.out.println(result.toString());


            }catch (IOException e){
                e.printStackTrace();
            }

            try {
                //mapping
                ObjectMapper objectMapper = new ObjectMapper();
                businessRegion = objectMapper.readValue(result.toString(), BusinessRegion.class);

            }catch (IOException e){
                e.printStackTrace();
            }

            //yelp response
            yelpResponse=new YelpResponse();
            yelpResponse.setData(businessRegion.getBusinessList());
            yelpResponse.setType("yelp");

        }

        return yelpResponse;

    }

}
