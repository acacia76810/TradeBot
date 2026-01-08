package com.tradebot.repository;


import com.tradebot.model.Stock.StockPriceId;
import com.tradebot.model.StockPrice;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends CrudRepository<StockPrice, StockPriceId> {

    @Query("{'stockName': ?0 }")
    List<StockPrice> findAllByStocknameAscByTime(String stockName, Sort sort);


}
