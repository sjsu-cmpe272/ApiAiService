package com.apiai.service.apiai;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIContext;
import ai.api.model.AIOutputContext;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import com.apiai.service.domain.BotMessage;
import com.apiai.service.domain.responses.ResponseObj;
import com.apiai.service.domain.responses.hotel.HotelsResponse;
import com.apiai.service.serviceintegration.CarsApiServiceIntegration;
import com.apiai.service.serviceintegration.HotelApiServiceIntegration;
import com.apiai.service.serviceintegration.WeatherApiServiceIntegration;
import com.apiai.service.serviceintegration.YelpApiServiceIntegration;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sindhya on 12/2/16.
 */
public class ApiAiService {

    AIConfiguration aiConfiguration=new AIConfiguration(ApiAiConstants.API_KEY);

    AIDataService dataService=new AIDataService(aiConfiguration);

    static String context = null;
    static String sessionid = null;
    List<AIContext> reqContext =  new ArrayList<>();

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
            request.setSessionId(sessionid);
            request.setContexts(new Gson().fromJson(context, reqContext.getClass()));

            AIResponse response = dataService.request(request);
            HashMap<String,JsonElement> parameters=response.getResult().getParameters();
            String speech=response.getResult().getFulfillment().getSpeech();
            String action=response.getResult().getAction();
            Integer status=response.getStatus().getCode();

            List<AIOutputContext> respContext = response.getResult().getContexts();

            if(status==200){
                if(action.equals("search-restaurant")) {
                    sessionid="";
                    context = "";
                    YelpApiServiceIntegration serviceIntegration=new YelpApiServiceIntegration();
                    service_resp = serviceIntegration.integrateYelpService(action, parameters, speech,latlong);
                }else if(action.equals("weather")){
                    sessionid="";
                    context = "";
                    WeatherApiServiceIntegration serviceIntegration=new WeatherApiServiceIntegration();
                    service_resp=serviceIntegration.integrateWeatherService(action,parameters,speech,latlong);

                }else if (action.equals("hotel") && respContext.isEmpty()){
                    sessionid="";
                    context = "";
                    HotelApiServiceIntegration serviceIntegration = new HotelApiServiceIntegration();
                    service_resp = serviceIntegration.integrateHotelService(action,parameters);

                }else if (action.equals("cars") && respContext.isEmpty()){
                    sessionid="";
                    context = "";
                    CarsApiServiceIntegration serviceIntegration = new CarsApiServiceIntegration();
                    service_resp = serviceIntegration.integrateCarsService(action,parameters);


                }else {
                    service_resp = new ResponseObj();
                    service_resp.setType("text");
                    service_resp.setSpeech(speech);
                    //service_resp.setaIcontext(respContext);
                    sessionid = response.getId();
                    context = response.getResult().getContexts().toString();
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
