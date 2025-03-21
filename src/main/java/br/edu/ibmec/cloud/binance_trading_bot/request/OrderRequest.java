package br.edu.ibmec.cloud.binance_trading_bot.request;

import lombok.Data;

@Data
public class OrderRequest {
    private String symbol;
    private String side;
    private double quantity;
    private double price;
}
