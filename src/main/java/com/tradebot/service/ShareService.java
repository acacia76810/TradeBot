package com.tradebot.service;

import com.tradebot.model.Share;
import com.tradebot.repository.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShareService {
    @Autowired
    ShareRepository shareRepository;

    public List<Share> getAllShare(){
        return shareRepository.findAll();
    }
}
