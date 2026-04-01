package com.tradebot.controller;

import com.tradebot.model.Stock.StockPriceId;
import com.tradebot.model.StockPrice;
import com.tradebot.model.Upstock;
import com.tradebot.repository.StockRepository;
import com.tradebot.repository.UpstockRepository;
import com.tradebot.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/stock-rank")
public class StockRankController {

    @Autowired
    private UpstockRepository upstockRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private HolidayService holidayService;

    @Value("${InitalTradeBalance}")
    private double initialBalance;

    @GetMapping("/month")
    public Map<String, Double> rankOnMonth(@RequestParam String fromDate, @RequestParam String toDate) {
        List<Upstock> allEquity = upstockRepository.findAllByinstrumenttype("EQ");
        Map<String, Double> stockCollection = new HashMap<>();
        try {
            for (Upstock stock : allEquity) {
                StockPriceId stockIdFromDate = new StockPriceId(stock.getInstrument_key(), fromDate);
                StockPriceId stockIdToDate = new StockPriceId(stock.getInstrument_key(), toDate);
                StockPrice buyPriceStock = stockRepository.findByStockPriceId(stockIdFromDate);
                StockPrice currentPriceStock = stockRepository.findByStockPriceId(stockIdToDate);

                if (buyPriceStock != null && currentPriceStock != null) {
                    int stockQty = (int) (initialBalance / buyPriceStock.getOpen());
                    double buyPrice = buyPriceStock.getOpen() * stockQty;
                    double currentPrice = currentPriceStock.getOpen() * stockQty;
                    double profit = buyPrice - currentPrice;
                    stockCollection.put(stock.getInstrument_key(), profit);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stockCollection;
    }

    @GetMapping("/auto")
    public Map<Double, Upstock> autoRanking() {
        int dateCount = 15;
        int previousDayAdjuster = -1;
        Map<Double, Upstock> sortedRank = new TreeMap<>();
        Calendar fromDate = Calendar.getInstance();
        fromDate.add(Calendar.DATE, previousDayAdjuster);
        fromDate.set(Calendar.HOUR_OF_DAY, 0);
        fromDate.set(Calendar.MINUTE, 0);
        fromDate.set(Calendar.SECOND, 0);
        fromDate.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        
        previousDayAdjuster--;
        String startDate = sdf.format(fromDate.getTime());
        String endDate;
        Map<String, Double> stockCollectionMap = new HashMap<>();
        
        for (int i = 0; i < dateCount; i++) {
            Calendar toDate = Calendar.getInstance();
            toDate.add(Calendar.DATE, (previousDayAdjuster - i));
            toDate.set(Calendar.HOUR_OF_DAY, 0);
            toDate.set(Calendar.MINUTE, 0);
            toDate.set(Calendar.SECOND, 0);
            toDate.set(Calendar.MILLISECOND, 0);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            endDate = sdf2.format(toDate.getTime());
            
            Map<String, Double> returnCollection = rankOnMonth(sdf.format(fromDate.getTime()), sdf2.format(toDate.getTime()));
            returnCollection.forEach((key, value) -> stockCollectionMap.merge(key, value, Double::sum));
        }
        
        for (String stock : stockCollectionMap.keySet()) {
            sortedRank.put(stockCollectionMap.get(stock), upstockRepository.findOneByinstrumentkey(stock));
        }
        
        return sortedRank;
    }
}
