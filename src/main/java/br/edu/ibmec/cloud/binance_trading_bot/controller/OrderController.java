package br.edu.ibmec.cloud.binance_trading_bot.controller;


import br.edu.ibmec.cloud.binance_trading_bot.model.User;
import br.edu.ibmec.cloud.binance_trading_bot.repository.UserRepository;
import br.edu.ibmec.cloud.binance_trading_bot.request.OrderRequest;
import br.edu.ibmec.cloud.binance_trading_bot.response.OrderResponse;
import br.edu.ibmec.cloud.binance_trading_bot.response.TickerResponse;
import br.edu.ibmec.cloud.binance_trading_bot.service.BinanceIntegration;
import com.binance.connector.client.SpotClient;
import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.impl.SpotClientImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("{id}/order")
public class OrderController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BinanceIntegration binanceIntegration;


    @PostMapping
    public ResponseEntity<OrderResponse> sendOrder(@PathVariable("id") int id, @RequestBody OrderRequest request) {

        Optional<User> optUser = this.repository.findById(id);

        if (optUser.isEmpty())
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        //Pego os dados do usuario no banco
        User user = optUser.get();

        //Configurando a chave de acesso para binance
        this.binanceIntegration.setAPI_KEY(user.getBinanceApiKey());
        this.binanceIntegration.setSECRET_KEY(user.getBinanceSecretKey());

        //Enviando a ordem

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            String result = this.binanceIntegration.createMarketOrder(request.getSymbol(),
                    request.getQuantity(),
                    request.getSide());
            OrderResponse response = objectMapper.readValue(result, OrderResponse.class);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
