package com.tradebot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Share {
    @Id
    private String id;
    private Double currentPrice;
    private Double openPrice;
    private Double closePrice;

    public Share() {
    }

    public Share(Double currentPrice, Double openPrice, Double closePrice) {
        this.currentPrice = currentPrice;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }
}
