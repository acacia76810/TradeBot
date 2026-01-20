package com.tradebot.model.Stock;

import java.io.Serializable;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import org.springframework.data.mongodb.core.mapping.Field;

public class StockPriceId implements Serializable {
    @Field("stock_name")
    private String stockName;


    @Field("time_stamp")
    private Instant timeStamp;

    public StockPriceId() {
    }

    public StockPriceId(String stockName, String timeStamp) {
        this.stockName = stockName;
        OffsetDateTime odt = OffsetDateTime.parse(timeStamp);
        this.timeStamp = odt.toInstant().atOffset(ZoneOffset.of("+05:30")).toInstant();
        System.out.println("ContructorSET-"+timeStamp);
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StockPriceId that = (StockPriceId) o;
        return Objects.equals(stockName, that.stockName) && Objects.equals(timeStamp, that.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockName, timeStamp);
    }
}
