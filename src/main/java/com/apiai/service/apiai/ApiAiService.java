package com.apiai.service.apiai;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import com.apiai.service.domain.BotMessage;
import com.apiai.service.domain.responses.ResponseObj;
import com.apiai.service.serviceintegration.WeatherApiServiceIntegration;
import com.apiai.service.serviceintegration.YelpApiServiceIntegration;
import com.google.gson.JsonElement;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * Created by sindhya on 12/2/16.
 */
public class ApiAiService {

    AIConfiguration aiConfiguration=new AIConfiguration(ApiAiConstants.API_KEY);

    AIDataService dataService=new AIDataService(aiConfiguration);

    public ResponseObj apiAiBotService(BotMessage botMessageObj){

        ResponseObj service_resp=null;
        String message=botMessageObj.getMessage();
        StringBuilder latlongBuilder=new StringBuilder();
        String latlong=null;
        if(!StringUtils.isEmpty(botMessageObj.getLocation())) {

            String latitude=botMessageObj.getLocation().getLatitude();
            String longitude=botMessageObj.getLocation().getLongitude();

            latlongBuilder.append(latitude).append(",").append(longitude);
            latlong = latlongBuilder.toString();
        }


        try {

            AIRequest request = new AIRequest(message);
            AIResponse response = dataService.request(request);
            HashMap<String,JsonElement> parameters=response.getResult().getParameters();
            String speech=response.getResult().getFulfillment().getSpeech();
            String action=response.getResult().getAction();
            Integer status=response.getStatus().getCode();

            if(status==200){
                if(action.equals("search-restaurant")) {
                    YelpApiServiceIntegration serviceIntegration=new YelpApiServiceIntegration();
                    service_resp = serviceIntegration.integrateYelpService(action, parameters, speech,latlong);
                }
                if(action.equals("weather")){
                    WeatherApiServiceIntegration serviceIntegration=new WeatherApiServiceIntegration();
                    service_resp=serviceIntegration.integrateWeatherService(action,parameters,speech,latlong);

                }
                System.out.println(response);
            }else{
                System.out.println(response.getStatus().getErrorDetails());
            }

        }catch(AIServiceException e){
            e.printStackTrace();
        }

        return service_resp;

    }

}
