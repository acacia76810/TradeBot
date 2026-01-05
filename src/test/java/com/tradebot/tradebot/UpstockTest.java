package com.tradebot.tradebot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tradebot.dao.InitJSON.StockDetail;
import com.tradebot.dao.upstock.UpstockDao;
import com.tradebot.model.Upstock;
import com.tradebot.repository.UpstockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@SpringBootTest
public class UpstockTest {

    @Autowired
    UpstockRepository upstockRepository;

    @Test
    void updateUpstockDB() throws IOException {
        try (Reader reader = new InputStreamReader(new FileInputStream(new ClassPathResource("NSE.json").getFile().getAbsolutePath()), "UTF-8")) {
            JsonElement json = JsonParser.parseReader( reader );
            JsonArray exchangeJson = json.getAsJsonArray();
            JsonArray equityStockjson = null;
            //for (int i=0;i<exchangeJson.size();i++){
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
                upstockRepository.save(upstock);
                    });
                //if(exchangeJson.get(i).getAsJsonObject().get("segment").getAsString().equals("NSE_EQ")) {
                /*Upstock upstock=new Upstock();
                upstock.setInstrument_key("");
                upstock.setSegment(exchangeJson.get(i).getAsJsonObject().("segment").getAsString());
                upstock.setName("String name") ;
                upstock.setExchange("") ;
                upstock.setIsin("") ;
                upstock.setInstrument_type("");
                upstock.setLot_size("") ;
                upstock.setFreeze_quantity("");
                upstock.setExchange_token("");
                upstock.setTick_size("") ;
                upstock.setTrading_symbol("");
                upstock.setShort_name("") ;
                upstock.setSecurity_type("") ;*/


                                                //}
            //}
            //this.stocknameListMaster=stockList;
        } catch (Exception e) {
            // do something
            e.printStackTrace();
        }
    }
    String CheckAndGet(JsonElement json,String key){
        if(json.getAsJsonObject().has(key)){
            System.out.println("-------"+json.getAsJsonObject().get(key).getAsString());
            return json.getAsJsonObject().get(key).getAsString();
        }
        return "";
    }
}
