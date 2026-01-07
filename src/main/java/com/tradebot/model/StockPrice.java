package com.tradebot.model;

import com.tradebot.model.Stock.StockPriceId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "StockPrice")
public class StockPrice {
    @EmbeddedId
    private StockPriceId stockPriceId;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;
    private Double interest;
    private String stockName;
    private Instant stockDate;

    public StockPrice(){}
    public StockPrice(StockPriceId stockPriceId, Double open, Double high, Double low, Double close, Double volume, Double interest, String stockName, String stockDate) {
        this.stockPriceId = stockPriceId;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.interest = interest;
        this.stockName = stockName;
        //this.stockDate = stockDate;
        OffsetDateTime odt = OffsetDateTime.parse(stockDate);
        this.stockDate = odt.toInstant();
    }

    public StockPriceId getStockPriceId() {
        return stockPriceId;
    }

    public void setStockPriceId(StockPriceId stockPriceId) {
        this.stockPriceId = stockPriceId;
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
        if (this == o) return true;
        if (!(o instanceof StockPrice)) return false;
        StockPrice that = (StockPrice) o;
        return getStockPriceId().equals(that.getStockPriceId()) && Objects.equals(getOpen(), that.getOpen()) && Objects.equals(getHigh(), that.getHigh()) && Objects.equals(getLow(), that.getLow()) && Objects.equals(getClose(), that.getClose()) && Objects.equals(getVolume(), that.getVolume()) && Objects.equals(getInterest(), that.getInterest()) && Objects.equals(getStockName(), that.getStockName()) && Objects.equals(getStockDate(), that.getStockDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStockPriceId(), getOpen(), getHigh(), getLow(), getClose(), getVolume(), getInterest(), getStockName(), getStockDate());
    }
}
