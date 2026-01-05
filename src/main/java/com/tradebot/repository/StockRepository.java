package com.tradebot.repository;


import com.tradebot.model.Stock.StockPriceId;
import com.tradebot.model.StockPrice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends CrudRepository<StockPrice, StockPriceId> {



}
