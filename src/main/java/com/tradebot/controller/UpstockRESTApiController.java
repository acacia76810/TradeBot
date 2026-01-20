package com.tradebot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UpstockRESTApiController {
    @PostMapping("/UpdateStocks")
    public String UpdateStock(){

        return "done";
    }
}
