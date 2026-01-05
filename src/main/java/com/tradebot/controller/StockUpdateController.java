package com.tradebot.controller;

import com.tradebot.dao.Stock.MarketRequest;
import com.upstox.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class StockUpdateController {

    @PostMapping("/FetchAndUpdateStock")
    public ResponseEntity<String> StockFetchUpdate(@RequestBody MarketRequest request) throws ApiException {
        //1minute, 30minute, day, week, month
//        return ResponseEntity.status(HttpStatus.OK)
  //              .body(stockFetchService.getStockPriceFromDB(request.getStockID(), request.getToDate(), request.getFromDate(), "day"));
        return ResponseEntity.status(HttpStatus.OK).body("GOOD");
    }
}
