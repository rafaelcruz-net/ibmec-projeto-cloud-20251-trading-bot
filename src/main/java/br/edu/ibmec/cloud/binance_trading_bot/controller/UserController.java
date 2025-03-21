package br.edu.ibmec.cloud.binance_trading_bot.controller;


import br.edu.ibmec.cloud.binance_trading_bot.model.User;
import br.edu.ibmec.cloud.binance_trading_bot.model.UserConfiguration;
import br.edu.ibmec.cloud.binance_trading_bot.model.UserTrackingTicker;
import br.edu.ibmec.cloud.binance_trading_bot.repository.UserConfigurationRepository;
import br.edu.ibmec.cloud.binance_trading_bot.repository.UserRepository;
import br.edu.ibmec.cloud.binance_trading_bot.repository.UserTrackingTickerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConfigurationRepository userConfigurationRepository;

    @Autowired
    private UserTrackingTickerRepository userTrackingTickerRepository;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        this.userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Integer id) {
        return this.userRepository.findById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("{id}/configuration")
    public ResponseEntity<User> associteConfiguration(@PathVariable("id") Integer id, @RequestBody UserConfiguration configuration) {
        Optional<User> optionalUser = this.userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //Cria a configuração na base de dados
        this.userConfigurationRepository.save(configuration);

        //Associa a configuração ao usuario
        User user = optionalUser.get();
        user.getConfigurations().add(configuration);
        userRepository.save(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    @PostMapping("{id}/tracking-ticker")
    public ResponseEntity<User> associateTicker(@PathVariable("id") Integer id, @RequestBody UserTrackingTicker ticker) {
        Optional<User> optionalUser = this.userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //Cria a configuração na base de dados
        this.userTrackingTickerRepository.save(ticker);

        //Associa a configuração ao usuario
        User user = optionalUser.get();
        user.getTrackingTickers().add(ticker);
        userRepository.save(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

}
