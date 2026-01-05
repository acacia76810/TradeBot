package com.tradebot.model;

import com.tradebot.model.Stock.StockPriceId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "StockPrice")
public class StockPrice {
    @EmbeddedId
    private StockPriceId date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;
    private Double interest;
    private String stockName;
    private Instant stockDate;

    public StockPriceId getDate() {
        return date;
    }

    public void setDate(StockPriceId date) {
        this.date = date;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getVolume(double v) {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getVolume() {
        return volume;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Instant getStockDate() {
        return stockDate;
    }

    public void setStockDate(Instant stockDate) {
        this.stockDate = stockDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StockPrice that = (StockPrice) o;
        return Objects.equals(date, that.date) && Objects.equals(open, that.open) && Objects.equals(high, that.high) && Objects.equals(low, that.low) && Objects.equals(close, that.close) && Objects.equals(volume, that.volume) && Objects.equals(interest, that.interest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, open, high, low, close, volume, interest);
    }
}
