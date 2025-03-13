package br.edu.ibmec.cloud.binance_trading_bot.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Double lossPercent;

    @Column
    private Double profitPercent;

    @Column
    private Double quantityPerOrder;
}
