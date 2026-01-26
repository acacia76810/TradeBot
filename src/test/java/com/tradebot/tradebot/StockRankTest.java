package com.tradebot.tradebot;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.tradebot.service.HolidayService;
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

	@Autowired
	HolidayService holidayService;
	@Value("${InitalTradeBalance}")
	double initialBalance;
	
	@Test
	void RankOnMonthTest() {
		Map<Double,Upstock> sortedRank=new TreeMap();
		List<Upstock> allEquity=upstockRepository.findAllByinstrumenttype("EQ");
		String fromDate="2026-01-23T00:00:00+05:30";
		String toDate = "2026-01-02T00:00:00+05:30";
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
				System.out.println(stock +" -- > "+sortedRank.get(stock).getTrading_symbol()+" -- >> "+sortedRank.get(stock).getInstrument_key());
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void RankDesending(){
		Date todayDate= new Date();
		todayDate.setHours(-24);
		todayDate.setMinutes(0);
		todayDate.setSeconds(0);
		//todayDate.
		int rankCaluculationDepth=30;
		ZonedDateTime zdt = todayDate.toInstant().atZone(ZoneId.of("Asia/Kolkata"));
		String formatted = zdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));

		System.out.println(formatted);
		System.out.println(todayDate);
		StockPriceId stockIdFromDate= new StockPriceId("NSE_EQ|INE848E01016", formatted);
		System.out.println(stockRepository.findByStockPriceId(stockIdFromDate));

		for(int i=0;i<rankCaluculationDepth;i++){

			double buyPrice = 0;
			if(getPriceForDateandStock("NSE_EQ|INE848E01016",getFormattedDate(todayDate))==0){
				if(!holidayService.isHoliday(getFormattedDate(todayDate))||!holidayService.isWeekendHoliday(getFormattedDate(todayDate))){
					//buyPrice=
				}
			}
			System.out.print("\n"+buyPrice +" -- "+getFormattedDate(todayDate));
			todayDate.setHours(-24);
			double currentPrice = getPriceForDateandStock("NSE_EQ|INE848E01016",getFormattedDate(todayDate));
			System.out.print(" ---- >>>"+currentPrice +" -- "+getFormattedDate(todayDate));
		}

	}


	double getPriceForDateandStock(String stockId,String date){
		StockPriceId stockIdFromDate= new StockPriceId(stockId, date);
		StockPrice stock =stockRepository.findByStockPriceId(stockIdFromDate);
		return stock==null?0:stock.getOpen();
	}

	String getFormattedDate(Date date){
		ZonedDateTime zdt = date.toInstant().atZone(ZoneId.of("Asia/Kolkata"));
		return zdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));
	}

}
