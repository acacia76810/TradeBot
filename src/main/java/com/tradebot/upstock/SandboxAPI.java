package com.tradebot.upstock;

import com.tradebot.uitl.InitStockListJson;
import com.upstox.ApiClient;
import com.upstox.ApiException;
import com.upstox.Configuration;
import com.upstox.api.PlaceOrderV3Request;
import com.upstox.api.PlaceOrderV3Response;
import com.upstox.auth.OAuth;
import io.swagger.client.api.OrderApiV3;

public class SandboxAPI {
    String upstocAccessToken="eyJ0eXAiOiJKV1QiLCJrZXlfaWQiOiJza192MS4wIiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiIyNENZUDUiLCJqdGkiOiI2OTViZjFkZWRkODdjNzNmOGVhNTE5OWYiLCJpc011bHRpQ2xpZW50IjpmYWxzZSwiaXNQbHVzUGxhbiI6dHJ1ZSwiaWF0IjoxNzY3NjMzMzc0LCJpc3MiOiJ1ZGFwaS1nYXRld2F5LXNlcnZpY2UiLCJleHAiOjE3NzAxNTYwMDB9.zoyLExT6tUNXao9bttN69w63kVcSWtQ-7ULMGy1G7BI";
    void initSandBoc(){
        boolean sandbox = true;
        ApiClient sandboxClient = new ApiClient(sandbox);
        OAuth OAUTH2 = (OAuth) sandboxClient.getAuthentication("OAUTH2");
        OAUTH2.setAccessToken(upstocAccessToken);
        Configuration.setDefaultApiClient(sandboxClient);
    }
    void placeOrder(){
        boolean sandbox = true;
        ApiClient sandboxClient = new ApiClient(sandbox);
        OAuth OAUTH2 = (OAuth) sandboxClient.getAuthentication("OAUTH2");
        OAUTH2.setAccessToken(upstocAccessToken);
        Configuration.setDefaultApiClient(sandboxClient);
        OrderApiV3 orderApiV3 = new OrderApiV3();
        PlaceOrderV3Request body = new PlaceOrderV3Request();
        body.setQuantity(10);
        body.setProduct(PlaceOrderV3Request.ProductEnum.D);
        body.setValidity(PlaceOrderV3Request.ValidityEnum.DAY);
        body.setPrice(9F);
        body.setTag("string");
        body.setInstrumentToken("NSE_EQ|INE669E01016");
        body.orderType(PlaceOrderV3Request.OrderTypeEnum.LIMIT);
        body.setTransactionType(PlaceOrderV3Request.TransactionTypeEnum.BUY);
        body.setDisclosedQuantity(0);
        body.setTriggerPrice(0F);
        body.setIsAmo(false);
        body.setSlice(true);
        try {
            PlaceOrderV3Response result = orderApiV3.placeOrder(body);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling OrderApi#placeOrder ");
            e.printStackTrace();
        }

    }
    public static void main(String args[]){
        SandboxAPI api =new SandboxAPI();
        api.placeOrder();
    }
}
