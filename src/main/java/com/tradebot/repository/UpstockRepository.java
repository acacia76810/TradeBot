package com.tradebot.repository;

import com.tradebot.model.Upstock;
import com.tradebot.model.Stock.StockPriceId;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableMongoRepositories
public interface UpstockRepository extends MongoRepository<Upstock,String> {
    @Query("{ 'instrument_type' : ?0 }")
    public List<Upstock> findAllByinstrumenttype(String instrumentType);
    @Query("{ 'instrument_type' : ?0 }")
    Upstock findOneByinstrumenttype(String eq);
    @Query("{ 'stockName' : ?0, 'stockDate' : ?1 }")
    List<Upstock> findByStockPriceId(String firstName, String lastName);
}
