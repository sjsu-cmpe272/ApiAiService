package com.apiai.service.controller;

import com.apiai.service.apiai.ApiAiService;
import com.apiai.service.domain.BotMessage;
import com.apiai.service.domain.responses.ResponseObj;
import org.springframework.web.bind.annotation.*;

/**
 * Created by sindhya on 12/2/16.
 */

@CrossOrigin
@RestController
public class ApiAiServiceController {

    @RequestMapping(value="/bot/message",method= RequestMethod.POST)
    public ResponseObj apiService(@RequestBody BotMessage message){

        ApiAiService service=new ApiAiService();
        ResponseObj responseObj=service.apiAiBotService(message);

        return responseObj;

    }

}
