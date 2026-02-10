package com.tradebot.tradebot;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
	Map<String,Double> RankOnMonthTest(String fromDate,String toDate) {
		Map<Double,Upstock> sortedRank=new TreeMap();
		List<Upstock> allEquity=upstockRepository.findAllByinstrumenttype("EQ");
		//String fromDate="2026-01-29T00:00:00+05:30";
		//String toDate = "2026-01-23T00:00:00+05:30";
		Map<String,Double> stockCollection=new HashMap<>();
		//System.out.println(initialBalance);
		try {
			//System.out.println(allEquity.size());
			for(Upstock stock:allEquity) {
				StockPriceId stockIdFromDate= new StockPriceId(stock.getInstrument_key(), fromDate);
				StockPriceId stockIdToDate= new StockPriceId(stock.getInstrument_key(), toDate);
				//stockId.setStockName(stock.getInstrument_key());
				StockPrice buyPriceStock=stockRepository.findByStockPriceId(stockIdFromDate);
				StockPrice currentPriceStock=stockRepository.findByStockPriceId(stockIdToDate);
				//System.out.println(buyPriceStock +" - "+ currentPriceStock);
				if(buyPriceStock!=null && currentPriceStock!=null) {
					int stockQty= (int) (initialBalance/buyPriceStock.getOpen());
					double buyPrice=buyPriceStock.getOpen()*stockQty;
					double currentPrice=currentPriceStock.getOpen()*stockQty;
					double profit=buyPrice-currentPrice;
					stockCollection.put(stock.getInstrument_key(),profit);
					//sortedRank.put(profit, stock);
					//System.out.println(profit+" =>> "+buyPrice+" | "+currentPrice);
				}

			}
			/*for(Double stock:sortedRank.keySet()) {
				System.out.println(stock +" -- > "+sortedRank.get(stock).getTrading_symbol()+" -- >> "+sortedRank.get(stock).getInstrument_key());
			}*/

		}catch(Exception e) {
			e.printStackTrace();
		}
		return stockCollection;
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

	@Test
	public void incrementDate(){
		int dateCount=30;
		for(int i=0;i<30;i++){
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -i);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
			System.out.println(sdf.format(cal.getTime()));
		}

	}

	@Test
	public void AutoRanking(){
		int dateCount=365;
		int previousDayAdjuster=-3;
		Map<Double,Upstock> sortedRank=new TreeMap();
		Calendar fromDate = Calendar.getInstance();
		fromDate.add(Calendar.DATE, previousDayAdjuster);
		fromDate.set(Calendar.HOUR_OF_DAY, 0);
		fromDate.set(Calendar.MINUTE, 0);
		fromDate.set(Calendar.SECOND, 0);
		fromDate.set(Calendar.MILLISECOND, 0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		//System.out.println(sdf.format(fromDate.getTime()));
		previousDayAdjuster--;
		Map<String,Double> stockCollectionMap= new HashMap<>();
		for(int i=0;i<dateCount;i++){
			//previousDayAdjuster=previousDayAdjuster-1;
			Calendar toDate = Calendar.getInstance();
			toDate.add(Calendar.DATE, (previousDayAdjuster-i));
			toDate.set(Calendar.HOUR_OF_DAY, 0);
			toDate.set(Calendar.MINUTE, 0);
			toDate.set(Calendar.SECOND, 0);
			toDate.set(Calendar.MILLISECOND, 0);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
			System.out.println("Calculating: "+sdf.format(fromDate.getTime())+" | "+sdf2.format(toDate.getTime()) +" | remaining: "+(dateCount-i)+" days.");

			//Date toDate=new Date(sdf.format(cal.getTime()));
			///System.out.println(toDate);
			Map<String,Double> returnCollection= RankOnMonthTest(sdf.format(fromDate.getTime()),sdf2.format(toDate.getTime()));
			returnCollection.forEach((key,value)->stockCollectionMap.merge(key,value,Double::sum));
			//stockCollectionMap.merge(returnCollection.keySet());
		}
		System.out.println("Total"+stockCollectionMap.size());
		for(String stock:stockCollectionMap.keySet()) {
			System.out.println(stock +" -- > "+stockCollectionMap.get(stock));
			sortedRank.put(stockCollectionMap.get(stock),upstockRepository.findOneByinstrumentkey(stock));
		}
		for(Double stock:sortedRank.keySet()) {
			System.out.println(stock +" -- > "+sortedRank.get(stock).getInstrument_key()+" | "+sortedRank.get(stock).getName());
		}
	}

}
