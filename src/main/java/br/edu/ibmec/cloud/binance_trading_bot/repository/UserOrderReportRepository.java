package br.edu.ibmec.cloud.binance_trading_bot.repository;


import br.edu.ibmec.cloud.binance_trading_bot.model.UserOrderReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOrderReportRepository extends JpaRepository<UserOrderReport, Integer> {
}
