package com.tradebot.tradebot;

import com.tradebot.model.Stock.StockPriceId;
import com.tradebot.model.StockPrice;
import com.tradebot.repository.StockRepository;
import com.tradebot.service.ShareService;
import com.upstox.ApiException;
import org.assertj.core.internal.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

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
		priceId.setStockName("AHUJA");
		priceId.setTimeStamp(Instant.now());
		stocck.setOpen(22d);
		stocck.setHigh(29d);
		stocck.setClose(25d);
		stocck.setLow(19d);
		stocck.getVolume(2300D);
		stockRepository.save(stocck);
		System.out.println(shareService.getStockPriceFromUpstockAPI("NSE_EQ|INE619A01035","2026-01-02","2025-12-31","1day"));

				/*Airport savedAirport = repo.save(airport);

				assertThat(savedAirport).isNotNull();
				assertThat(savedAirport.getCountryCode()).isEqualTo("VN");
				assertThat(savedAirport.getCityCode()).isEqualTo("HAN");*/

	}

}
