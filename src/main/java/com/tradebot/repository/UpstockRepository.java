package com.tradebot.repository;

import com.tradebot.model.Upstock;
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
}
