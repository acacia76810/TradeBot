package com.tradebot.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Objects;

@Document(collection = "Upstock")
public class Upstock {
    @Id
    @Indexed(unique = true)
    private String instrument_key;
    private String segment;
    private String name;
    private String exchange;
    private String isin;
    private String instrument_type;
    private String lot_size;
    private String freeze_quantity;
    private String exchange_token;
    private String tick_size;
    private String trading_symbol;
    private String short_name;
    private String security_type;
    private String weekly;
    private String expiry;
    private String underlying_symbol;
    private String minimum_lot;
    private String underlying_key;
    private String underlying_type;
    private String strike_price;
    private String qty_multiplier;
    private String mtf_enabled;
    private String mtf_bracket;
    private String intraday_margin;
    private String intraday_leverage;

    public String getInstrument_key() {
        return instrument_key;
    }

    public void setInstrument_key(String instrument_key) {
        this.instrument_key = instrument_key;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getInstrument_type() {
        return instrument_type;
    }

    public void setInstrument_type(String instrument_type) {
        this.instrument_type = instrument_type;
    }

    public String getLot_size() {
        return lot_size;
    }

    public void setLot_size(String lot_size) {
        this.lot_size = lot_size;
    }

    public String getFreeze_quantity() {
        return freeze_quantity;
    }

    public void setFreeze_quantity(String freeze_quantity) {
        this.freeze_quantity = freeze_quantity;
    }

    public String getExchange_token() {
        return exchange_token;
    }

    public void setExchange_token(String exchange_token) {
        this.exchange_token = exchange_token;
    }

    public String getTick_size() {
        return tick_size;
    }

    public void setTick_size(String tick_size) {
        this.tick_size = tick_size;
    }

    public String getTrading_symbol() {
        return trading_symbol;
    }

    public void setTrading_symbol(String trading_symbol) {
        this.trading_symbol = trading_symbol;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getSecurity_type() {
        return security_type;
    }

    public void setSecurity_type(String security_type) {
        this.security_type = security_type;
    }

    public String getWeekly() {
        return weekly;
    }

    public void setWeekly(String weekly) {
        this.weekly = weekly;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getUnderlying_symbol() {
        return underlying_symbol;
    }

    public void setUnderlying_symbol(String underlying_symbol) {
        this.underlying_symbol = underlying_symbol;
    }

    public String getMinimum_lot() {
        return minimum_lot;
    }

    public void setMinimum_lot(String minimum_lot) {
        this.minimum_lot = minimum_lot;
    }

    public String getUnderlying_key() {
        return underlying_key;
    }

    public void setUnderlying_key(String underlying_key) {
        this.underlying_key = underlying_key;
    }

    public String getUnderlying_type() {
        return underlying_type;
    }

    public void setUnderlying_type(String underlying_type) {
        this.underlying_type = underlying_type;
    }

    public String getStrike_price() {
        return strike_price;
    }

    public void setStrike_price(String strike_price) {
        this.strike_price = strike_price;
    }

    public String getQty_multiplier() {
        return qty_multiplier;
    }

    public void setQty_multiplier(String qty_multiplier) {
        this.qty_multiplier = qty_multiplier;
    }

    public String getMtf_enabled() {
        return mtf_enabled;
    }

    public void setMtf_enabled(String mtf_enabled) {
        this.mtf_enabled = mtf_enabled;
    }

    public String getMtf_bracket() {
        return mtf_bracket;
    }

    public void setMtf_bracket(String mtf_bracket) {
        this.mtf_bracket = mtf_bracket;
    }

    public String getIntraday_margin() {
        return intraday_margin;
    }

    public void setIntraday_margin(String intraday_margin) {
        this.intraday_margin = intraday_margin;
    }

    public String getIntraday_leverage() {
        return intraday_leverage;
    }

    public void setIntraday_leverage(String intraday_leverage) {
        this.intraday_leverage = intraday_leverage;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Upstock upstock = (Upstock) o;
        return Objects.equals(instrument_key, upstock.instrument_key) && Objects.equals(segment, upstock.segment) && Objects.equals(name, upstock.name) && Objects.equals(exchange, upstock.exchange) && Objects.equals(isin, upstock.isin) && Objects.equals(instrument_type, upstock.instrument_type) && Objects.equals(lot_size, upstock.lot_size) && Objects.equals(freeze_quantity, upstock.freeze_quantity) && Objects.equals(exchange_token, upstock.exchange_token) && Objects.equals(tick_size, upstock.tick_size) && Objects.equals(trading_symbol, upstock.trading_symbol) && Objects.equals(short_name, upstock.short_name) && Objects.equals(security_type, upstock.security_type) && Objects.equals(weekly, upstock.weekly) && Objects.equals(expiry, upstock.expiry) && Objects.equals(underlying_symbol, upstock.underlying_symbol) && Objects.equals(minimum_lot, upstock.minimum_lot) && Objects.equals(underlying_key, upstock.underlying_key) && Objects.equals(underlying_type, upstock.underlying_type) && Objects.equals(strike_price, upstock.strike_price) && Objects.equals(qty_multiplier, upstock.qty_multiplier) && Objects.equals(mtf_enabled, upstock.mtf_enabled) && Objects.equals(mtf_bracket, upstock.mtf_bracket) && Objects.equals(intraday_margin, upstock.intraday_margin) && Objects.equals(intraday_leverage, upstock.intraday_leverage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrument_key, segment, name, exchange, isin, instrument_type, lot_size, freeze_quantity, exchange_token, tick_size, trading_symbol, short_name, security_type, weekly, expiry, underlying_symbol, minimum_lot, underlying_key, underlying_type, strike_price, qty_multiplier, mtf_enabled, mtf_bracket, intraday_margin, intraday_leverage);
    }
    public Upstock(){}

    public Upstock(String instrument_key, String segment, String name, String exchange, String isin, String instrument_type, String lot_size, String freeze_quantity, String exchange_token, String tick_size, String trading_symbol, String short_name, String security_type, String weekly, String expiry, String underlying_symbol, String minimum_lot, String underlying_key, String underlying_type, String strike_price, String qty_multiplier, String mtf_enabled, String mtf_bracket, String intraday_margin, String intraday_leverage) {
        this.instrument_key = instrument_key;
        this.segment = segment;
        this.name = name;
        this.exchange = exchange;
        this.isin = isin;
        this.instrument_type = instrument_type;
        this.lot_size = lot_size;
        this.freeze_quantity = freeze_quantity;
        this.exchange_token = exchange_token;
        this.tick_size = tick_size;
        this.trading_symbol = trading_symbol;
        this.short_name = short_name;
        this.security_type = security_type;
        this.weekly = weekly;
        this.expiry = expiry;
        this.underlying_symbol = underlying_symbol;
        this.minimum_lot = minimum_lot;
        this.underlying_key = underlying_key;
        this.underlying_type = underlying_type;
        this.strike_price = strike_price;
        this.qty_multiplier = qty_multiplier;
        this.mtf_enabled = mtf_enabled;
        this.mtf_bracket = mtf_bracket;
        this.intraday_margin = intraday_margin;
        this.intraday_leverage = intraday_leverage;
    }
}
