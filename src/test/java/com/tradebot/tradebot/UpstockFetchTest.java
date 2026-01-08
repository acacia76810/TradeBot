package com.tradebot.tradebot;

import com.tradebot.model.StockPrice;
import com.tradebot.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest
public class UpstockFetchTest {

    @Autowired
    StockRepository stockRepository;

    @Test
    void FetchStockBetween(){
        List<StockPrice> stockList=stockRepository.findAllByStocknameAscByTime("NSE_EQ|INE0LLY01014", Sort.by(Sort.Direction.ASC, "stockDate"));
        stockList.forEach(stock->{
            System.out.println(stock.getStockName()+" | "+stock.getStockDate());
        });
        //System.out.println();
    }
}
