package br.edu.ibmec.cloud.binance_trading_bot.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderResponse {
    private String symbol;
    private String orderId;
    private BigDecimal executedQty;
    private String type;
    private String side;
    private List<OrderFillResponse> fills;
}
