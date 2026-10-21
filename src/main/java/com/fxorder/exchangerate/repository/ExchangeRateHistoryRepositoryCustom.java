package com.fxorder.exchangerate.repository;

import com.fxorder.exchangerate.entity.ExchangeRateHistory;

import java.util.List;

public interface ExchangeRateHistoryRepositoryCustom {

    List<ExchangeRateHistory> findLatest();
}
