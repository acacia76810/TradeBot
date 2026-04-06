package com.tradebot.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tradebot.dao.Stock.Candle;
import com.tradebot.model.Stock.StockPriceId;
import com.tradebot.model.StockPrice;
import com.tradebot.repository.StockRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class StockUpdateAPIService {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    UpstockInstrumentService upstockInstrumentService;

    public JsonObject updateStockData(String stockId, String interval, String timeGap, String fromDate, String toDate){
        return updateStockData(stockId, interval, timeGap, fromDate, toDate, false);
    }

    private JsonObject updateStockData(String stockId, String interval, String timeGap, String fromDate, String toDate, boolean instrumentRefreshAttempted){

        String url = "https://api.upstox.com/v3/historical-candle/"+URLEncoder.encode(stockId, StandardCharsets.UTF_8)+"/"+interval+"/"+timeGap+"/"+fromDate+"/"+toDate;
        //System.out.println("URL --->>"+url);
        //System.out.println(URLEncoder.encode(url, StandardCharsets.UTF_8));
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer {your_access_token}")
                .build();

        try {

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // Check the response status
            if (httpResponse.statusCode() == 200) {
                // Do something with the response body (e.g., print it)
                JsonObject jsonObject = JsonParser.parseString(httpResponse.body()).getAsJsonObject();

                System.out.println(httpResponse.body());
                return jsonObject;
            } else {
                // Print an error message if the request was not successful
                System.err.println("Error: " + httpResponse.statusCode() + " - " + httpResponse.body());
                if (!instrumentRefreshAttempted && isInvalidInstrumentError(httpResponse.body())) {
                    upstockInstrumentService.refreshInstrumentData();
                    return updateStockData(stockId, interval, timeGap, fromDate, toDate, true);
                }
            }
        } catch (Exception e) {
            // Handle exceptions
            if (!instrumentRefreshAttempted && isInvalidInstrumentError(e.getMessage())) {
                upstockInstrumentService.refreshInstrumentData();
                return updateStockData(stockId, interval, timeGap, fromDate, toDate, true);
            }
            e.printStackTrace();
        }
        return null;
    }

    private boolean isInvalidInstrumentError(String errorMessage) {
        return errorMessage != null && errorMessage.toLowerCase().contains("invalid instrument");
    }

    public String saveUptockDB(String stockId, String interval, String timeGap, String fromDate, String toDate){
        JsonObject element=updateStockData(stockId,interval,timeGap,fromDate,toDate);
        //System.out.println("Update Element = "+element);
        int stockCounter=element.size();
        if(element!=null) {
            for (JsonElement canldeList : element.getAsJsonObject("data").getAsJsonArray("candles")) {
                JsonArray canldeArr = canldeList.getAsJsonArray();
            /*Candle candle=new Candle((String)canldeArr.get(0).getAsString(),(Double) canldeArr.get(0).getAsDouble(),
                    (Double)canldeArr.get(0).getAsDouble(),(Double)canldeArr.get(0).getAsDouble(),
                    (Double)canldeArr.get(0).getAsDouble(),(Double)canldeArr.get(0).getAsDouble(),
                    (Double)canldeArr.get(0).getAsDouble());*/
                StockPriceId priceId = new StockPriceId(stockId, (String) canldeArr.get(0).getAsString());
                StockPrice stock = new StockPrice(priceId,
                        (Double) canldeArr.get(1).getAsDouble(),
                        (Double) canldeArr.get(2).getAsDouble(),
                        (Double) canldeArr.get(3).getAsDouble(),
                        (Double) canldeArr.get(4).getAsDouble(),
                        (Double) canldeArr.get(5).getAsDouble(),
                        (Double) canldeArr.get(6).getAsDouble(),
                        stockId,
                        (String) canldeArr.get(0).getAsString());
                stockRepository.save(stock);
                //System.out.println("Remaining == >> "+--stockCounter);
                //System.out.println("----->>>>"+canldeArr.get(0)+" | "+canldeArr.get(1));
            }
        }
        return "Done";
    }

    public static void main(String[] args) {
        StockUpdateAPIService st=new StockUpdateAPIService();
        st.updateStockData("NSE_EQ%7CINE848E01016","days","1","2026-01-02","2026-01-01");
    }
}
