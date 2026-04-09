package com.tradebot.repository;


import com.tradebot.model.Stock.StockPriceId;
import com.tradebot.model.StockPrice;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends MongoRepository<StockPrice, StockPriceId> {

    @Query("{'stock_name': ?0 }")
    List<StockPrice> findAllByStocknameAscByTime(String stockName, Sort sort);
    @Query("{'stockPriceId.stockName': ?0 }")
    List<StockPrice> findAllByInstrumentKey(String instrumentKey, Sort sort);
    StockPrice findByStockPriceId(StockPriceId id);

    //StockPrice findStockPriceStockDate(StockPrice )

}
