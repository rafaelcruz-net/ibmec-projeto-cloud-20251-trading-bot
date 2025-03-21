package br.edu.ibmec.cloud.binance_trading_bot.service;

import com.binance.connector.client.SpotClient;
import com.binance.connector.client.impl.SpotClientImpl;
import kotlin.contracts.Returns;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Data
public class BinanceIntegration {
    private String BASE_URL = "https://testnet.binance.vision";
    private String API_KEY;
    private String SECRET_KEY;

    public String getTickers(ArrayList<String> symbols) {
        SpotClient client = new SpotClientImpl(this.API_KEY, this.SECRET_KEY, this.BASE_URL);
        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbols", symbols);
        String result = client.createMarket().ticker(parameters);
        return result;
    }

    public String createMarketOrder(String symbol, double quantity, String side) {
        SpotClient client = new SpotClientImpl(this.API_KEY, this.SECRET_KEY, this.BASE_URL);

        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        parameters.put("side", side);
        parameters.put("type", "MARKET");
        parameters.put("quantity", quantity);
        String result = client.createTrade().newOrder(parameters);
        return result;

    }
}
