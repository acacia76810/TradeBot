package com.tradebot.dao.Stock;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Candle {

    //[2024-11-13T15:15:00+05:30, 195.7, 197.0, 195.7, 197.0, 1800.0, 0.0]

    private Date date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;
    private Double interest;


    public Candle(String date, Double open, Double high, Double low, Double close, Double volume, Double interest)  {
        try {
        //SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
            this.date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(date);
        }catch(ParseException e){
            e.printStackTrace();
        }
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.interest = interest;
    }
    public Candle(Object date, Object open, Object high, Object low, Object close, Object volume, Object interest)  {
        try {
            //SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
            this.date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(String.valueOf(date));
        }catch(ParseException e){
            e.printStackTrace();
        }
        this.open = (Double)open;
        this.high = (Double)high;
        this.low = (Double)low;
        this.close = (Double)close;
        this.volume = (Double)volume;
        this.interest = (Double)interest;
    }

    public Candle() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public Double getVolume() {
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

    @Override
    public String toString() {
        return "Candle{" +
                "date=" + date +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                ", interest=" + interest +
                '}';
    }
}
