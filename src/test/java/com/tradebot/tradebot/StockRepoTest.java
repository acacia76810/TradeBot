package com.tradebot.tradebot;

import com.tradebot.model.Stock.StockPriceId;
import com.tradebot.repository.StockRepository;
import com.tradebot.service.ShareService;
import com.upstox.ApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

@SpringBootTest
public class StockRepoTest {
    @Autowired
    StockRepository stockRepository;
    @Autowired
    ShareService shareService;
    @Test
    void StockFetchTest(){
        Instant dateTime=Instant.parse("2026-01-05T12:12:11.467+00:00");
        StockPriceId stockPriceId=new StockPriceId("AHUJA", dateTime);
        stockRepository.findById(stockPriceId);
    }
    @Test
    void saveUpstockToDB() {
        try {
            shareService.getStockPriceFromUpstockAPI("NSE_EQ|INE388Y01029", "2026-01-02", "2025-12-31", "day");

        }catch(ApiException e){
            e.printStackTrace();
        }
    }
}
