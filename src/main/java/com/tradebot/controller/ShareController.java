package com.tradebot.controller;

import com.tradebot.model.Share;
import com.tradebot.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/share")
public class ShareController {

    @Autowired
    ShareService shareService;

    @GetMapping("/")
    public List<Share> getAllShare(){
        return shareService.getAllShare();
    }
}
