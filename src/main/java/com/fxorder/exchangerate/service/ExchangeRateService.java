package com.fxorder.exchangerate.service;

import com.fxorder.exchangerate.dto.ExchangeRateDetailResponse;
import com.fxorder.exchangerate.dto.ExchangeRateDto;
import com.fxorder.exchangerate.dto.ExchangeRateLatestResponse;
import com.fxorder.exchangerate.entity.ExchangeRateHistory;
import com.fxorder.enums.CurrencyCode;
import com.fxorder.exchangerate.repository.ExchangeRateHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateHistoryRepository exchangeRateHistoryRepository;

    @Transactional(readOnly = true)
    public ExchangeRateLatestResponse getLatestRates() {
        List<ExchangeRateHistory> latest = exchangeRateHistoryRepository.findLatest();

        List<ExchangeRateDto> dtos = latest.stream()
                .map(e -> new ExchangeRateDto(
                        e.getCurrencyCode().name(),
                        e.getBuyRate(),
                        e.getBaseRate(),
                        e.getSellRate(),
                        e.getDateTime()))
                .toList();

        return new ExchangeRateLatestResponse(dtos);
    }

    @Transactional(readOnly = true)
    public ExchangeRateDetailResponse getLatestRateByCurrency(String currency) {
        CurrencyCode currencyCode = CurrencyCode.valueOf(currency.toUpperCase());
        ExchangeRateHistory history = exchangeRateHistoryRepository.findFirstByCurrencyCodeOrderByDateTimeDesc(currencyCode)
                .orElseThrow(() -> new IllegalArgumentException("아래 통화에 대한 환율 정보가 없습니다 " + currencyCode));

        return new ExchangeRateDetailResponse(
                history.getCurrencyCode().name(),
                history.getBuyRate(),
                history.getBaseRate(),
                history.getSellRate(),
                history.getDateTime()
        );
    }
}
