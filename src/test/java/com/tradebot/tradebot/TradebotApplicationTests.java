package com.tradebot.tradebot;

import com.tradebot.model.Stock.StockPriceId;
import com.tradebot.model.StockPrice;
import com.tradebot.repository.StockRepository;
import com.tradebot.service.ShareService;
import com.upstox.ApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TradebotApplicationTests {
	@Autowired
	ShareService shareService;
	@Autowired
	StockRepository stockRepository;

	@Test
	void contextLoads() throws ApiException {

		StockPrice stocck=new StockPrice();
		StockPriceId priceId=new StockPriceId();
		priceId.setStockName("Chaitanya");
		priceId.setTimeStamp(Instant.now());
		stocck.setStockPriceId(priceId);
		stocck.setOpen(21d);
		stocck.setHigh(24d);
		stocck.setClose(22d);
		stocck.setLow(12d);
		stocck.getVolume(2322D);
		stocck.setStockName("Chaitanya");
		stocck.setStockDate(Instant.now());
		stockRepository.save(stocck);
		//System.out.println(shareService.getStockPriceFromUpstockAPI("NSE_EQ|INE619A01035","2026-01-02","2025-12-31","1day"));

				/*Airport savedAirport = repo.save(airport);

				assertThat(savedAirport).isNotNull();
				assertThat(savedAirport.getCountryCode()).isEqualTo("VN");
				assertThat(savedAirport.getCityCode()).isEqualTo("HAN");*/

	}

	@Test
	void fetchTest() throws ApiException {
		List<StockPrice> stockList= (List<StockPrice>) stockRepository.findAll();

		for(StockPrice stock:stockList) {
			System.out.println(stock.getOpen());
		}
	}
	@Test
	void fetchUpstockPrice() throws ApiException {
		System.out.println(shareService.getStockPriceFromUpstockAPI("NSE_EQ|INE619A01035","2025-12-31","2026-01-02","day","1"));

	}


}
