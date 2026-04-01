package com.tradebot.tradebot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@SpringBootTest
public class SampleTest {
    @Test
    public void main() {
        String url = "https://api.upstox.com/v2/instruments/search?query=RELIANCE&exchanges=NSE&segments=FO&instrument_types=CE,PE&expiry=current_month&atm_offset=0&page_number=1&records=20";
        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer eyJ0eXAiOiJKV1QiLCJrZXlfaWQiOiJza192MS4wIiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiIyNENZUDUiLCJqdGkiOiI2OWMzOGMyNjgwNmFjMjU5NWYyYThlNTciLCJpc011bHRpQ2xpZW50IjpmYWxzZSwiaXNQbHVzUGxhbiI6ZmFsc2UsImlhdCI6MTc3NDQyMzA3OCwiaXNzIjoidWRhcGktZ2F0ZXdheS1zZXJ2aWNlIiwiZXhwIjoxNzc0NDc2MDAwfQ.SFeEOykI1mSXIx_Pz7727JmeKTXP8DEVlohW7VeBygY";

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Accept", acceptHeader)
                .header("Authorization", authorizationHeader)
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}