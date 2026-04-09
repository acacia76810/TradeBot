package com.tradebot.controller;

import com.tradebot.model.Stock.StockPriceId;
import com.tradebot.model.StockPrice;
import com.tradebot.model.Upstock;
import com.tradebot.repository.StockRepository;
import com.tradebot.repository.UpstockRepository;
import com.tradebot.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        return buildAutoRankingMap(15);
    }

    @PostMapping("/top-performing")
    public List<StockRankingResponse> topPerformingFunds(@RequestBody StockRankingRequest request) {
        int dateCount = request != null && request.getDateCount() != null ? request.getDateCount() : 15;
        int topCount = request != null && request.getTopCount() != null ? request.getTopCount() : 10;
        int maxGraphPoints = request != null && request.getMaxGraphPoints() != null ? request.getMaxGraphPoints() : 40;
        System.out.println("Called - - - -- - - - - - - -- - -top performiang");
        dateCount = Math.max(1, dateCount);
        topCount = Math.max(1, topCount);
        maxGraphPoints = Math.max(10, maxGraphPoints);

        Map<Double, Upstock> ranked = buildAutoRankingMap(dateCount);
        List<StockRankingResponse> response = new ArrayList<>();

        List<Map.Entry<Double, Upstock>> rankedEntries = new ArrayList<>(ranked.entrySet());
        Collections.reverse(rankedEntries);

        for (Map.Entry<Double, Upstock> entry : rankedEntries) {
            if (response.size() >= topCount) {
                break;
            }
            Upstock stock = entry.getValue();
            if (stock == null || stock.getInstrument_key() == null) {
                continue;
            }

            List<StockPrice> allPrices = stockRepository.findAllByInstrumentKey(
                    stock.getInstrument_key(),
                    Sort.by(Sort.Direction.ASC, "stockDate")
            );

            if (allPrices.isEmpty()) {
                continue;
            }

            int start = Math.max(0, allPrices.size() - maxGraphPoints);
            List<StockGraphPoint> points = new ArrayList<>();
            for (int i = start; i < allPrices.size(); i++) {
                StockPrice price = allPrices.get(i);
                points.add(new StockGraphPoint(
                        String.valueOf(price.getStockDate()),
                        price.getOpen(),
                        price.getVolume()
                ));
            }

            response.add(new StockRankingResponse(
                    stock.getInstrument_key(),
                    stock.getTrading_symbol(),
                    stock.getName(),
                    entry.getKey(),
                    points
            ));
        }

        return response;
    }

    private Map<Double, Upstock> buildAutoRankingMap(int dateCount) {
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

    public static class StockRankingRequest {
        private Integer dateCount;
        private Integer topCount;
        private Integer maxGraphPoints;

        public Integer getDateCount() {
            return dateCount;
        }

        public void setDateCount(Integer dateCount) {
            this.dateCount = dateCount;
        }

        public Integer getTopCount() {
            return topCount;
        }

        public void setTopCount(Integer topCount) {
            this.topCount = topCount;
        }

        public Integer getMaxGraphPoints() {
            return maxGraphPoints;
        }

        public void setMaxGraphPoints(Integer maxGraphPoints) {
            this.maxGraphPoints = maxGraphPoints;
        }
    }

    public static class StockRankingResponse {
        private final String instrumentKey;
        private final String tradingSymbol;
        private final String name;
        private final Double profit;
        private final List<StockGraphPoint> graphPoints;

        public StockRankingResponse(String instrumentKey, String tradingSymbol, String name, Double profit, List<StockGraphPoint> graphPoints) {
            this.instrumentKey = instrumentKey;
            this.tradingSymbol = tradingSymbol;
            this.name = name;
            this.profit = profit;
            this.graphPoints = graphPoints;
        }

        public String getInstrumentKey() {
            return instrumentKey;
        }

        public String getTradingSymbol() {
            return tradingSymbol;
        }

        public String getName() {
            return name;
        }

        public Double getProfit() {
            return profit;
        }

        public List<StockGraphPoint> getGraphPoints() {
            return graphPoints;
        }
    }

    public static class StockGraphPoint {
        private final String time;
        private final Double stockPrice;
        private final Double volume;

        public StockGraphPoint(String time, Double stockPrice, Double volume) {
            this.time = time;
            this.stockPrice = stockPrice;
            this.volume = volume;
        }

        public String getTime() {
            return time;
        }

        public Double getStockPrice() {
            return stockPrice;
        }

        public Double getVolume() {
            return volume;
        }
    }
}
