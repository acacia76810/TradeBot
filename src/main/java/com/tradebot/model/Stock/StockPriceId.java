package com.tradebot.model.Stock;

import java.io.Serializable;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StockPriceId implements Serializable {
    @Column(name = "stock_name")
    private String stockName;


    @Column(name = "time_stamp",length = 12)
    private Instant timeStamp;

    public StockPriceId() {
    }

    public StockPriceId(String stockName, String timeStamp) {
        this.stockName = stockName;
        OffsetDateTime odt = OffsetDateTime.parse(timeStamp);
        this.timeStamp = odt.toInstant();
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
