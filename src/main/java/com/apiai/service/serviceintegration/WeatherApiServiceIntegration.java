package com.apiai.service.serviceintegration;

import com.apiai.service.apiai.ApiAiConstants;
import com.apiai.service.domain.responses.weather.WeatherObj;
import com.apiai.service.domain.responses.weather.WeatherResponse;
import com.apiai.service.domain.weather.CurrentWeather;
import com.apiai.service.domain.weather.WeatherForecast;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by sindhya on 12/3/16.
 */
public class WeatherApiServiceIntegration {

    public WeatherResponse integrateWeatherService(String action, HashMap<String,JsonElement> param, String speech,String latlong){

        WeatherResponse weatherResponse=new WeatherResponse();

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet=new HttpGet();
        HttpResponse response;
        StringBuffer res=null;
        WeatherObj weatherObj= new WeatherObj();
        if(action.equals("weather")){

            String weather_location=null;

            JsonElement location=param.get("location");
            JsonElement date=param.get("weather");

            if(StringUtils.isEmpty(weather_location)) {
                weather_location = "SanJose";
            }
            else{
                weather_location=location.getAsString().toLowerCase();
            }

            if("today".equals(date.getAsString().toLowerCase())){

                httpGet=new HttpGet(ApiAiConstants.WEATHER_HOST+ApiAiConstants.WEATHER_TODAY_ENDPOINT+weather_location);

                try {
                    httpGet.addHeader(HttpHeaders.CONTENT_TYPE,"application/json");
                    response = client.execute(httpGet);
                    BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    res = new StringBuffer();
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        res.append(line);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }

                try {
                    //mapping
                    ObjectMapper objectMapper = new ObjectMapper();
                    CurrentWeather currentWeather = objectMapper.readValue(res.toString(), CurrentWeather.class);
                    weatherObj.setLocation(currentWeather.getWeather().get(0).getDesc());
                    weatherObj.setTemperature(currentWeather.getMeasures().getTemperature());
                    weatherObj.setPressure(currentWeather.getMeasures().getPressure());
                    weatherObj.setHumidity(currentWeather.getMeasures().getHumidity());


                }catch (IOException e){
                    e.printStackTrace();
                }

            }else if("tomorrow".equals(date.getAsString().toLowerCase())){

                httpGet=new HttpGet(ApiAiConstants.WEATHER_HOST+ApiAiConstants.WEATHER_FORECAST_ENDPOINT+weather_location);

                try {
                    response = client.execute(httpGet);
                    BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    res = new StringBuffer();
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        res.append(line);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }

                try {

                    ObjectMapper objectMapper = new ObjectMapper();
                    WeatherForecast weatherForecast = objectMapper.readValue(res.toString(), WeatherForecast.class);
                    weatherObj.setLocation(weatherForecast.getForecastCityData().getName());
                    weatherObj.setTemperature(weatherForecast.getWeatherMeasures().get(0).getMeasures().getTemperature());
                    weatherObj.setPressure(weatherForecast.getWeatherMeasures().get(0).getMeasures().getPressure());
                    weatherObj.setHumidity(weatherForecast.getWeatherMeasures().get(0).getMeasures().getHumidity());


                }catch (IOException e){
                    e.printStackTrace();
                }

            }
            weatherResponse=new WeatherResponse();
            weatherResponse.setData(weatherObj);
            weatherResponse.setType("weather");

        }
        return weatherResponse;

    }

}
