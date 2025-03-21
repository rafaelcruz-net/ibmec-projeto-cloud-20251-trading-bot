package br.edu.ibmec.cloud.binance_trading_bot.controller;

import br.edu.ibmec.cloud.binance_trading_bot.model.User;
import br.edu.ibmec.cloud.binance_trading_bot.model.UserTrackingTicker;
import br.edu.ibmec.cloud.binance_trading_bot.repository.UserRepository;
import br.edu.ibmec.cloud.binance_trading_bot.response.TickerResponse;
import br.edu.ibmec.cloud.binance_trading_bot.service.BinanceIntegration;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("{id}/tickers")
public class TickerController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BinanceIntegration binanceIntegration;

    @GetMapping
    public ResponseEntity<List<TickerResponse>> getTickers(@PathVariable("id") int id) {
        Optional<User> optUser = this.repository.findById(id);

        if (optUser.isEmpty())
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        //Pego os dados do usuario no banco
        User user = optUser.get();

        //Obtendo as moedas cadastradas para consulta
        ArrayList<String> tickers = new ArrayList<>();

        for (UserTrackingTicker item: user.getTrackingTickers()) {
            tickers.add(item.getSymbol());
        }

        //Configurando a chave de acesso para binance
        this.binanceIntegration.setAPI_KEY(user.getBinanceApiKey());
        this.binanceIntegration.setSECRET_KEY(user.getBinanceSecretKey());

        //Obtendo o ultimo preços das moedas cadastradas para o usuario
        String result = this.binanceIntegration.getTickers(tickers);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            List<TickerResponse> response = objectMapper.readValue(result,
                                                new TypeReference<List<TickerResponse>>() {});
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
