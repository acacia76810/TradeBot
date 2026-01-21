package com.tradebot.tradebot;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.tradebot.model.StockPrice;
import com.tradebot.model.Upstock;
import com.tradebot.model.Stock.StockPriceId;
import com.tradebot.repository.StockRepository;
import com.tradebot.repository.UpstockRepository;

@SpringBootTest
public class StockRankTest {
	@Autowired
	UpstockRepository upstockRepository;
	@Autowired
	StockRepository stockRepository;
	@Value("${InitalTradeBalance}")
	double initialBalance;
	
	@Test
	void RankOnMonthTest() {
		Map<Double,Upstock> sortedRank=new TreeMap();
		List<Upstock> allEquity=upstockRepository.findAllByinstrumenttype("EQ");
		String fromDate="2026-01-20T09:16:00+05:30";
		String toDate = "2025-12-19T00:00:00+05:30";
		System.out.println(initialBalance);
		try {
		System.out.println(allEquity.size());
		for(Upstock stock:allEquity) {
			StockPriceId stockIdFromDate= new StockPriceId(stock.getInstrument_key(), fromDate);
			StockPriceId stockIdToDate= new StockPriceId(stock.getInstrument_key(), toDate);
			//stockId.setStockName(stock.getInstrument_key());
			StockPrice buyPriceStock=stockRepository.findByStockPriceId(stockIdFromDate);
			StockPrice currentPriceStock=stockRepository.findByStockPriceId(stockIdToDate);
			System.out.println(buyPriceStock +" - "+ currentPriceStock);
			if(buyPriceStock!=null && currentPriceStock!=null) {
			double buyPrice=buyPriceStock.getOpen()*initialBalance;
			double currentPrice=currentPriceStock.getOpen()*initialBalance;
			double profit=buyPrice-currentPrice;
			sortedRank.put(profit, stock);
			System.out.println(profit+" =>> "+buyPrice+" | "+currentPrice);
			}
			
		}
		for(Double stock:sortedRank.keySet()) {
			System.out.println(stock +" -- > "+sortedRank.get(stock).getTrading_symbol());
		}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
