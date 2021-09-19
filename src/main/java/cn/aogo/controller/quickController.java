package cn.aogo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class quickController {
    @RequestMapping("/quick")
    @ResponseBody
    public String quick() {
        return "Springboot 真香";
    }
}
