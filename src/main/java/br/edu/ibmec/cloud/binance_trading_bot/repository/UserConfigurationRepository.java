package br.edu.ibmec.cloud.binance_trading_bot.repository;

import br.edu.ibmec.cloud.binance_trading_bot.model.UserConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConfigurationRepository extends JpaRepository<UserConfiguration, Integer> {
}
