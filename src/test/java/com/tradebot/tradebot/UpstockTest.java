package com.tradebot.tradebot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tradebot.model.Upstock;
import com.tradebot.repository.UpstockRepository;
import com.tradebot.service.ShareService;
import com.tradebot.service.UpstockInstrumentService;
import com.upstox.ApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
public class UpstockTest {

    @Autowired
    UpstockRepository upstockRepository;
    @Autowired
    ShareService shareService;
    @Autowired
    UpstockInstrumentService upstockInstrumentService;

    @Test
    void updateUpstockDB() throws IOException {
        upstockInstrumentService.updateUpstockDB();
    }
    String CheckAndGet(JsonElement json,String key){
        if(json.getAsJsonObject().has(key)){
            //System.out.println("-------"+json.getAsJsonObject().get(key).getAsString());
            return json.getAsJsonObject().get(key).getAsString();
        }
        return "";
    }

    @Test
    void getAllEquity(){
        List<Upstock> allEquity=upstockRepository.findAllByinstrumenttype("EQ");
        System.out.println(upstockRepository.findAllByinstrumenttype("EQ"));
    }

    @Test
    void UpdateAllEquity(){
        List<Upstock> allEquity=upstockRepository.findAllByinstrumenttype("EQ");
        String fromDate="2026-01-02";
        String toDate= "2026-01-04";
        String timeInterval="day";
        AtomicInteger equitySize= new AtomicInteger(allEquity.size());
        allEquity.forEach(equity->{
        //for(int j=0;j<2;j++) {
            //equitySize--;
            try {
                shareService.updateStockPriceFromUpstockAPI(equity.getInstrument_key(), toDate, fromDate, timeInterval);
              //  shareService.updateStockPriceFromUpstockAPI(allEquity.get(j).getInstrument_key(), toDate, fromDate, timeInterval);
            } catch (ApiException e) {
                //throw new RuntimeException(e);
                try {
                    System.out.println("Remaining " + equitySize.getAndDecrement());
                    for (int i = 1; i > 0; i--) {
                        System.out.println("Waiting for " + i + " seconds");
                        Thread.sleep(10);
                    }
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }

            });
        //}
    }

    @Test
    void DuplicateStockTest(){
        Upstock allEquity=upstockRepository.findOneByinstrumenttype("EQ");
        System.out.println(allEquity.getName());
    }

    @Test
    void InsertOneStockTest(){
        List<Upstock> allEquity=upstockRepository.findAllByinstrumenttype("EQ");
        String fromDate="2026-01-05";
        String toDate= "2026-01-06";
        String timeInterval="minutes";
        int retry=4;
        boolean saveOne=true;
            try {
                do {
                    shareService.updateStockPriceFromUpstockAPI(allEquity.get(retry).getInstrument_key(), toDate, fromDate, timeInterval);
                    saveOne=true;
                }
                while(!saveOne);
            } catch (ApiException e) {
                retry++;
                saveOne=false;
                System.out.println("exception in saving");
                e.printStackTrace();
            }

    }
}
