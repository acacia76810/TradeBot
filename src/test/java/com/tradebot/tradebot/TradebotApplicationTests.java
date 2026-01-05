package com.tradebot.tradebot;

import com.tradebot.model.StockPrice;
import com.tradebot.service.ShareService;
import com.upstox.ApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TradebotApplicationTests {
	@Autowired
	ShareService shareService;

	@Test
	void contextLoads() throws ApiException {

		StockPrice stocck=new StockPrice();
		System.out.println(shareService.getStockPriceFromUpstockAPI("NSE_EQ|INE619A01035","2026-01-02","2025-12-31","1day"));

				/*Airport savedAirport = repo.save(airport);

				assertThat(savedAirport).isNotNull();
				assertThat(savedAirport.getCountryCode()).isEqualTo("VN");
				assertThat(savedAirport.getCityCode()).isEqualTo("HAN");*/

	}

}
