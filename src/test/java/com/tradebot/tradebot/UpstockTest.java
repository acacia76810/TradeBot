package com.tradebot.tradebot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tradebot.model.Upstock;
import com.tradebot.repository.UpstockRepository;
import com.tradebot.service.ShareService;
import com.upstox.ApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
public class UpstockTest {

    @Autowired
    UpstockRepository upstockRepository;
    @Autowired
    ShareService shareService;

    @Test
    void updateUpstockDB() throws IOException {
        try (Reader reader = new InputStreamReader(new FileInputStream(new ClassPathResource("NSE.json").getFile().getAbsolutePath()), "UTF-8")) {
            JsonElement json = JsonParser.parseReader( reader );
            JsonArray exchangeJson = json.getAsJsonArray();

            exchangeJson.forEach(js->{
                        System.out.println(js);
                Upstock upstock=new Upstock();
                upstock.setInstrument_key(CheckAndGet(js,"instrument_key"));
                upstock.setSegment(CheckAndGet(js,"segment"));
                upstock.setName(CheckAndGet(js,"name"));
                upstock.setExchange(CheckAndGet(js,"exchange"));
                upstock.setIsin(CheckAndGet(js,"isin"));
                upstock.setInstrument_type(CheckAndGet(js,"instrument_type"));
                upstock.setLot_size(CheckAndGet(js,"lot_size"));
                upstock.setFreeze_quantity(CheckAndGet(js,"freeze_quantity"));
                upstock.setExchange_token(CheckAndGet(js,"exchange_token"));
                upstock.setTick_size(CheckAndGet(js,"tick_size"));
                upstock.setTrading_symbol(CheckAndGet(js,"trading_symbol"));
                upstock.setShort_name(CheckAndGet(js,"short_name"));
                upstock.setSecurity_type(CheckAndGet(js,"security_type"));
                upstock.setWeekly(CheckAndGet(js,"weekly"));
                upstock.setExpiry(CheckAndGet(js,"expiry"));
                upstock.setUnderlying_symbol(CheckAndGet(js,"underlying_symbol"));
                upstock.setMinimum_lot(CheckAndGet(js,"minimum_lot"));
                upstock.setUnderlying_key(CheckAndGet(js,"underlying_key"));
                upstock.setUnderlying_type(CheckAndGet(js,"underlying_type"));
                upstock.setStrike_price(CheckAndGet(js,"strike_price"));
                upstock.setQty_multiplier(CheckAndGet(js,"qty_multiplier"));
                upstock.setMtf_enabled(CheckAndGet(js,"mtf_enabled"));
                upstock.setMtf_bracket(CheckAndGet(js,"mtf_bracket"));
                upstock.setIntraday_margin(CheckAndGet(js,"intraday_margin"));
                upstock.setIntraday_leverage(CheckAndGet(js,"intraday_leverage"));
                try {
                    upstockRepository.save(upstock);
                }catch(org.springframework.dao.DuplicateKeyException e){
                    System.out.println("Already Present "+CheckAndGet(js,"name"));
                }
                    });

        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
        String fromDate="2026-01-06";
        String toDate= "2026-01-06";
        String timeInterval="day";
        AtomicInteger equitySize= new AtomicInteger(allEquity.size());
        //allEquity.forEach(equity->{
        for(int j=0;j<2;j++) {
            //equitySize--;
            try {
                //shareService.updateStockPriceFromUpstockAPI(equity.getInstrument_key(), toDate, fromDate, timeInterval);
                shareService.updateStockPriceFromUpstockAPI(allEquity.get(j).getInstrument_key(), toDate, fromDate, timeInterval);
            } catch (ApiException e) {
                //throw new RuntimeException(e);
                try {
                    System.out.println("Remaining " + equitySize.getAndDecrement());
                    for (int i = 10; i > 0; i--) {
                        System.out.println("Waiting for " + i + " seconds");
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }

            //});
        }
    }

    @Test
    void DuplicateStockTest(){
        Upstock allEquity=upstockRepository.findOneByinstrumenttype("EQ");
        System.out.println(allEquity.getName());
    }

    @Test
    void InsertOneStockTest(){
        List<Upstock> allEquity=upstockRepository.findAllByinstrumenttype("EQ");
        String fromDate="2026-01-06";
        String toDate= "2026-01-06";
        String timeInterval="day";
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
