package com.tradebot.uitl;

import com.tradebot.dao.InitJSON.StockDetail;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minidev.json.JSONArray;
import net.minidev.json.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InitStockListJson {
    Path filePath= Paths.get(new ClassPathResource("NSE.json").getFile().getAbsolutePath());
    private JSONArray exchnageList;
    public JsonArray equityStockjson;
    public List<String> stocknameListMaster;
    public Map<String, StockDetail> stockDetailsMap;
    private JsonElement json;
    @PostConstruct
    void init() throws IOException, ParseException {
        new InitStockListJson();
    }
    public InitStockListJson() throws IOException, ParseException {
        System.out.println("********************InitStockCodeJSON");
        this.equityStockjson=new JsonArray();
        List<String> stockList=new ArrayList<>();
        Map<String, StockDetail> stockMap=new HashMap<>();
        try (Reader reader = new InputStreamReader(new FileInputStream(new ClassPathResource("NSE.json").getFile().getAbsolutePath()), "UTF-8")) {
            this.json = JsonParser.parseReader( reader );
            JsonArray exchangeJson = this.json.getAsJsonArray();
            for (int i=0;i<exchangeJson.size();i++){
                if(exchangeJson.get(i).getAsJsonObject().get("segment").getAsString().equals("NSE_EQ")) {
                    this.equityStockjson.add(exchangeJson.get(i));
                    stockList.add(exchangeJson.get(i).getAsJsonObject().get("instrument_key").getAsString());
                    StockDetail stockDetail=new StockDetail(exchangeJson.get(i).getAsJsonObject().get("instrument_key").getAsString(),
                            exchangeJson.get(i).getAsJsonObject().get("trading_symbol").getAsString(),
                            exchangeJson.get(i).getAsJsonObject().get("name").getAsString());

                    stockMap.put(exchangeJson.get(i).getAsJsonObject().get("instrument_key").getAsString(),stockDetail);
                    this.stockDetailsMap=stockMap;
                }
            }
            this.stocknameListMaster=stockList;
        } catch (Exception e) {
            // do something
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        InitStockListJson stock=new InitStockListJson();
        //System.out.println(stock.json.toString());
        try {
            JsonArray exchangeJson = stock.json.getAsJsonArray();

            for (int i=0;i<exchangeJson.size();i++){
                if(exchangeJson.get(i).getAsJsonObject().get("segment").getAsString().equals("NSE_EQ")) {
                    stock.equityStockjson.add(exchangeJson.get(i));

                }
            }
            System.out.println("All Equity:"+stock.equityStockjson.size());
            System.out.println("All Json size:"+exchangeJson.size());
            for(int i=0;i<stock.equityStockjson.size();i++){
                System.out.println(stock.equityStockjson.get(i));
            }
        }catch(Exception e){
            System.out.println("Message:"+e.getMessage()+"\n Cause:"
                    +e.getCause());
        }

        System.out.println("Sucess");

    }

}

