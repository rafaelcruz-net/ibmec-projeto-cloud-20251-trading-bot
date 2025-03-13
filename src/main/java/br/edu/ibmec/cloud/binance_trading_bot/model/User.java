package br.edu.ibmec.cloud.binance_trading_bot.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String binanceApiKey;

    @Column
    private String binanceSecretKey;

    @Column
    private Double saldoInicio;

    @OneToMany
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private List<UserConfiguration> configurations;

    @OneToMany
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private List<UserTrackingTicker> trackingTickers;
}
