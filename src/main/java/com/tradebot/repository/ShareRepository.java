package com.tradebot.repository;

import com.tradebot.model.Share;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends MongoRepository<Share, String> {

}
