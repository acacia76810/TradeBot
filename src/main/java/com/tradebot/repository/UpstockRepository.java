package com.tradebot.repository;

import com.tradebot.model.Upstock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpstockRepository extends CrudRepository<Upstock,String> {
}
