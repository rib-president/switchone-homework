package com.fxorder.exchangerate.repository;

import com.fxorder.exchangerate.entity.ExchangeRateHistory;
import com.fxorder.enums.CurrencyCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeRateHistoryRepository extends JpaRepository<ExchangeRateHistory, Long>, ExchangeRateHistoryRepositoryCustom {
    Optional<ExchangeRateHistory> findFirstByCurrencyCodeOrderByDateTimeDesc(CurrencyCode currencyCode);
}
