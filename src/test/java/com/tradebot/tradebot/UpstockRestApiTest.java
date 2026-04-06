package com.tradebot.tradebot;

import com.tradebot.model.Upstock;
import com.tradebot.repository.UpstockRepository;
import com.tradebot.service.StockUpdateAPIService;
import com.upstox.ApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UpstockRestApiTest {
    @Autowired
    StockUpdateAPIService stockUpdateAPIService;
    @Autowired
    UpstockRepository upstockRepository;

    @Test
    void UpdateUpstockRestAPITest(){
        List<Upstock> allEquity=upstockRepository.findAllByinstrumenttype("EQ");
        String fromDate="2026-03-10";
        String toDate= "2026-03-24";
        String timeInterval="minutes";
        String tieGap="1";
        int retry=4;
        boolean saveOne=true;
        do {
            stockUpdateAPIService.updateStockData(allEquity.get(retry).getInstrument_key(),timeInterval,tieGap, toDate, fromDate );
            saveOne=true;
        }
        while(!saveOne);

    }

    @Test
    void SaveUpstockAPITest(){
        List<Upstock> allEquity=upstockRepository.findAllByinstrumenttype("EQ");
        String fromDate="2023-03-30";
        String toDate= "2026-04-04";
        String timeInterval="days";
        String tieGap="1";
        int allEquitySize=allEquity.size();
        //for(Upstock stock:allEquity){
        for (int i=0;i<allEquity.size();i++){
            stockUpdateAPIService.saveUptockDB(allEquity.get(i).getInstrument_key(),timeInterval,tieGap, toDate, fromDate );
            System.out.println("Remaining Stocks left =="+--allEquitySize);
        }
    }

}
