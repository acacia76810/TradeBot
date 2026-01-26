package com.tradebot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
public class HolidayService {

    public boolean isHoliday(String inputDate){
        HttpResponse<String> response = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.upstox.com/v2/market/holidays/" + inputDate))
                    .header("Accept", "application/json")
                    .GET()
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Holiday Response:"+response.body());
            ObjectMapper mapper=new ObjectMapper();
            JsonNode josn =mapper.readTree(response.body());
            System.out.println(josn.get("data").size());
            if(josn.get("data").size()>0){
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isWeekendHoliday(String dateStr){
        try {
            // Default parse expects ISO-8601 format (yyyy-MM-dd)
            LocalDate date = LocalDate.parse(dateStr); //
            DayOfWeek day = date.getDayOfWeek(); //

            return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY; //
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + dateStr);
            return false;
        }
    }

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        HolidayService holiday=new HolidayService();
        String date="2026-01-23";
        System.out.println(holiday.isHoliday(date)+" | "+holiday.isWeekendHoliday(date));
    }
}
