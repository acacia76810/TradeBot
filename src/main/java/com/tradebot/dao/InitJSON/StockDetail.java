package com.tradebot.dao.InitJSON;

public class StockDetail {
    private String stockID;
    private String stockCode;
    private String stockName;

    public StockDetail(String stockID, String stockCode, String stockName) {
        this.stockID = stockID;
        this.stockCode = stockCode;
        this.stockName = stockName;
    }

    public StockDetail() {

    }

    public String getStockID() {
        return stockID;
    }

    public void setStockID(String stockID) {
        this.stockID = stockID;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
}
