package br.edu.ibmec.cloud.binance_trading_bot.repository;

import br.edu.ibmec.cloud.binance_trading_bot.model.UserTrackingTicker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTrackingTickerRepository extends JpaRepository<UserTrackingTicker, Integer> {
}
