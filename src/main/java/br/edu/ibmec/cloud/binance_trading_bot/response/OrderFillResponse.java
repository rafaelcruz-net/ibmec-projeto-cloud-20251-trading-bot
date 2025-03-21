package br.edu.ibmec.cloud.binance_trading_bot.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderFillResponse {
    private double price;
    private BigDecimal qty;
}
