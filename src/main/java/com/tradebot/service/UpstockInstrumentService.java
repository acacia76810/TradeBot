package com.tradebot.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tradebot.model.Upstock;
import com.tradebot.repository.UpstockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.GZIPInputStream;

@Service
public class UpstockInstrumentService {

    private static final String NSE_DOWNLOAD_URL = "https://assets.upstox.com/market-quote/instruments/exchange/NSE.json.gz";
    private static final String UPSTOCK_COLLECTION_NAME = "Upstock";
    private static final Path NSE_JSON_PATH = Path.of("src", "main", "resources", "NSE.json");

    @Autowired
    private UpstockRepository upstockRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public void refreshInstrumentData() {
        try {
            downloadAndReplaceNseJson();
            mongoTemplate.dropCollection(UPSTOCK_COLLECTION_NAME);
            updateUpstockDB();
        } catch (Exception e) {
            throw new RuntimeException("Failed to refresh Upstock instrument data", e);
        }
    }

    public void updateUpstockDB() throws IOException {
        try (Reader reader = Files.newBufferedReader(NSE_JSON_PATH)) {
            JsonElement json = JsonParser.parseReader(reader);
            JsonArray exchangeJson = json.getAsJsonArray();

            exchangeJson.forEach(js -> {
                Upstock upstock = new Upstock();
                upstock.setInstrument_key(checkAndGet(js, "instrument_key"));
                upstock.setSegment(checkAndGet(js, "segment"));
                upstock.setName(checkAndGet(js, "name"));
                upstock.setExchange(checkAndGet(js, "exchange"));
                upstock.setIsin(checkAndGet(js, "isin"));
                upstock.setInstrument_type(checkAndGet(js, "instrument_type"));
                upstock.setLot_size(checkAndGet(js, "lot_size"));
                upstock.setFreeze_quantity(checkAndGet(js, "freeze_quantity"));
                upstock.setExchange_token(checkAndGet(js, "exchange_token"));
                upstock.setTick_size(checkAndGet(js, "tick_size"));
                upstock.setTrading_symbol(checkAndGet(js, "trading_symbol"));
                upstock.setShort_name(checkAndGet(js, "short_name"));
                upstock.setSecurity_type(checkAndGet(js, "security_type"));
                upstock.setWeekly(checkAndGet(js, "weekly"));
                upstock.setExpiry(checkAndGet(js, "expiry"));
                upstock.setUnderlying_symbol(checkAndGet(js, "underlying_symbol"));
                upstock.setMinimum_lot(checkAndGet(js, "minimum_lot"));
                upstock.setUnderlying_key(checkAndGet(js, "underlying_key"));
                upstock.setUnderlying_type(checkAndGet(js, "underlying_type"));
                upstock.setStrike_price(checkAndGet(js, "strike_price"));
                upstock.setQty_multiplier(checkAndGet(js, "qty_multiplier"));
                upstock.setMtf_enabled(checkAndGet(js, "mtf_enabled"));
                upstock.setMtf_bracket(checkAndGet(js, "mtf_bracket"));
                upstock.setIntraday_margin(checkAndGet(js, "intraday_margin"));
                upstock.setIntraday_leverage(checkAndGet(js, "intraday_leverage"));
                try {
                    upstockRepository.save(upstock);
                } catch (DuplicateKeyException e) {
                    System.out.println("Already Present " + checkAndGet(js, "name"));
                }
            });
        }
    }

    private void downloadAndReplaceNseJson() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(NSE_DOWNLOAD_URL))
                .GET()
                .build();

        HttpResponse<InputStream> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
        if (response.statusCode() != 200) {
            throw new IOException("Unable to download NSE.json.gz. Status code: " + response.statusCode());
        }

        Path parent = NSE_JSON_PATH.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        try (InputStream responseStream = response.body();
             GZIPInputStream gzipInputStream = new GZIPInputStream(responseStream)) {
            Files.copy(gzipInputStream, NSE_JSON_PATH, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private String checkAndGet(JsonElement json, String key) {
        if (json.getAsJsonObject().has(key) && !json.getAsJsonObject().get(key).isJsonNull()) {
            return json.getAsJsonObject().get(key).getAsString();
        }
        return "";
    }
}
