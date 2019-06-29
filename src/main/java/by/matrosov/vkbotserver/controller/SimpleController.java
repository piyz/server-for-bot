package by.matrosov.vkbotserver.controller;

import by.matrosov.vkbotserver.model.Message;
import by.matrosov.vkbotserver.model.RequestMessage;
import by.matrosov.vkbotserver.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SimpleController {

    private static final Logger logger = LoggerFactory.getLogger(SimpleController.class);

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/vk", method = RequestMethod.POST)
    @ResponseBody
    public String getMessage(@RequestBody RequestMessage requestMessage){
        String text = requestMessage.getObject().get("text").asText();
        int owner = requestMessage.getObject().get("from_id").asInt();
        logger.info("get message " + text + " from " + owner);
        messageService.add(new Message("#" + text, owner));
        return "ok";
    }

}
