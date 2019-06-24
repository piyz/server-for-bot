package by.matrosov.vkbotserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SimpleController {

    @RequestMapping(value = "/vk", method = RequestMethod.POST)
    @ResponseBody
    public String hello(){
        return "12ed1bba";
    }

}
