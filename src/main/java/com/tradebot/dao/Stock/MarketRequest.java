package com.tradebot.dao.Stock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketRequest {
    private String stockID;
    private String fromDate;
    private String toDate;

    public String getStockID() {
        return stockID;
    }

    public void setStockID(String stockID) {
        this.stockID = stockID;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
