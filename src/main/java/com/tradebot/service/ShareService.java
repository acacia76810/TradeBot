package com.tradebot.service;

import com.tradebot.dao.Stock.Candle;
import com.tradebot.model.StockPrice;
import com.tradebot.repository.StockRepository;
import com.tradebot.uitl.InitStockListJson;
import com.upstox.ApiException;
import com.upstox.api.GetHistoricalCandleResponse;
import io.swagger.client.api.HistoryApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ShareService {
    @Autowired
    InitStockListJson initStockListJson;
    @Autowired
    StockRepository stockRepository;


    private static final Logger logger = LoggerFactory.getLogger(ShareService.class);

    public List<Candle> getStockPriceFromUpstockAPI(String instrumentKey, String toDate, String fromDate, String interval) throws ApiException {
        HistoryApi apiInstance = new HistoryApi();
        Candle stockCandle = null;
        List<Candle> stockCandleList = new ArrayList<>();
        String apiVersion = "2.0";
        GetHistoricalCandleResponse result =null;
        try {
            result = apiInstance.getHistoricalCandleData1(instrumentKey, interval, toDate, fromDate, apiVersion);
            for (List<Object> candle : result.getData().getCandles()) {
                stockCandle = new Candle(candle.get(0), candle.get(1), candle.get(2), candle.get(3), candle.get(4),
                        candle.get(5), candle.get(6));
                stockCandleList.add(stockCandle);
            }
        }
        catch (ApiException e) {
            throw new ApiException(e);
        }
        return stockCandleList;
    }
    public List<Candle> updateStockPriceFromUpstockAPI(String instrumentKey, String toDate, String fromDate, String interval) throws ApiException {
        HistoryApi apiInstance = new HistoryApi();
        Candle stockCandle = null;
        List<Candle> stockCandleList = new ArrayList<>();
        String apiVersion = "2.0";
        GetHistoricalCandleResponse result =null;
        try {
            result = apiInstance.getHistoricalCandleData1(instrumentKey, interval, toDate, fromDate, apiVersion);
            for (List<Object> candle : result.getData().getCandles()) {
                stockCandle = new Candle(candle.get(0), candle.get(1), candle.get(2), candle.get(3), candle.get(4),
                        candle.get(5), candle.get(6));
                stockCandleList.add(stockCandle);
            }
        }
        catch (ApiException e) {
            throw new ApiException(e);
        }
        return stockCandleList;
    }


    /*public Map<String, StockPrice>  getStockPriceFromDBOrUpstockAPI(String instrumentKey, String toDate, String fromDate, String interval) throws ApiException {
        Optional<StockPrice> stockData=stockRepository.findById(instrumentKey);
        StockPrice stockDbDataonDate= null;
        List<Candle> stockCandleList = new ArrayList<>();
        if(stockData.isPresent()){
            stockDbDataonDate=stockData.get();
        }else{
            stockCandleList=getStockPriceFromUpstockAPI(instrumentKey,toDate,fromDate,interval);
        }
        try {
            result = apiInstance.getHistoricalCandleData1(instrumentKey, interval, toDate, fromDate, apiVersion);
        }
        catch (ApiException e) {
            throw new ApiException(e);
        }
        return result.getData().getCandles();
    }*/

   /* public Map<String, StockData> getAllStockPrice(String instrumentKey,String toDate, String fromDate,String interval){
        //System.out.println(initStockListJson.equityStockjson);
        int equityStockSize=initStockListJson.equityStockjson.size();
        List<List<Object>> fetchStockPriceList=new ArrayList<>();
        int counter=0;
        Map<String, StockData> returnStockData=new HashMap<>();
        int limit=10;
        for(String stockName: initStockListJson.stockDetailsMap.keySet()){
            System.out.print(++counter +"of"+ equityStockSize +" - "+stockName+"\n");
            try {
                StockData stock = new StockData();
                Optional<StockData> stokOptional=stockRepo.findById(initStockListJson.stockDetailsMap.get(stockName).getStockName());
                List<CandlesOnly> allStockFromDB = stockRepo.findCandlesByStockNameAndDateRange(
                        stockName,
                        StringToDate(fromDate),
                        StringToDate(toDate)
                );
                fetchStockPriceList = getStockPriceFromUpstockAPI(stockName, toDate, fromDate, interval);
                List<Candle> stockCandleList = new ArrayList<>();
                StockHdata stockHD = new StockHdata();
                stock.setStockName(initStockListJson.stockDetailsMap.get(stockName).getStockName());
                Candle stockCandle = null;

                for (List<Object> candle : fetchStockPriceList) {
                    stockCandle = new Candle(candle.get(0), candle.get(1), candle.get(2), candle.get(3), candle.get(4),
                            candle.get(5), candle.get(6));
                    stockCandleList.add(stockCandle);
                    stock.setCandles(stockCandleList);
                    Map<String,List<Candle>> stockHdataD=new HashMap<>();
                    stockHdataD.put(initStockListJson.stockDetailsMap.get(stockName).getStockName(),stockCandleList);
                    stockHD.setStockData(stockHdataD);
                    stockRepo.save(stock);

                }

                //assert stockCandle != null;
                logger.debug("{} -- {}", stock.getStockName(), stockCandle);

                returnStockData.put(stockName, stock);
                //stockHdataRepo.save(returnStockData);
                if(--limit==0){
                    break;
                }
            }catch(ApiException e){
                System.out.println("Not able to find data - "+e.getMessage()+"\n-->>Cause :: "+e.getCause());
            }
        }

        return returnStockData;
    }*/

    public Date StringToDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            // Parse the string to a Date object
            date = formatter.parse(dateString);
            System.out.println("Parsed Date: " + date);
        } catch (ParseException e) {
            System.err.println("Error parsing date string: " + e.getMessage());
        }
        return date;
    }
}
