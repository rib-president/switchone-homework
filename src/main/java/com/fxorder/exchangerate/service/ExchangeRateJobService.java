package com.fxorder.exchangerate.service;

import com.fxorder.exchangerate.dto.ExchangeRateApiResponse;
import com.fxorder.exchangerate.entity.ExchangeRateHistory;
import com.fxorder.enums.CurrencyCode;
import com.fxorder.exchangerate.repository.ExchangeRateHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateJobService {

    private final RestTemplate restTemplate;
    private final ExchangeRateHistoryRepository exchangeRateHistoryRepository;

    private static final BigDecimal BUY_RATE_MULTIPLIER = new BigDecimal("1.05");
    private static final BigDecimal SELL_RATE_MULTIPLIER = new BigDecimal("0.95");
    private static final BigDecimal JPY_UNIT = new BigDecimal("100");
    private static final int SCALE = 2;

    @Value("${exchange-rate.api.url}")
    private String apiUrl;

    @Scheduled(fixedRate = 60_000)
    public void collectExchangeRates() {
        log.info("환율 수집 스케줄러 실행");
        try {
            ExchangeRateApiResponse response = restTemplate.getForObject(apiUrl, ExchangeRateApiResponse.class);

            if (response == null || !"success".equals(response.getResult())) {
                log.error("환율 API 호출 실패: response={}", response);
                return;
            }

            Map<String, BigDecimal> rates = response.getRates();
            BigDecimal krwRate = rates.get("KRW");

            if (krwRate == null) {
                log.error("KRW 환율 정보가 응답에 없습니다");
                return;
            }

            LocalDateTime now = LocalDateTime.now();
            List<ExchangeRateHistory> histories = new ArrayList<>();

            for (CurrencyCode currency : CurrencyCode.values()) {
                BigDecimal foreignRate = rates.get(currency.name());
                if (foreignRate == null) {
                    log.warn("{}에 대한 환율 정보 없음, 건너뜀", currency);
                    continue;
                }

                BigDecimal baseRate = krwRate.divide(foreignRate, 10, RoundingMode.HALF_UP);

                if (currency == CurrencyCode.JPY) {
                    baseRate = baseRate.multiply(JPY_UNIT);
                }

                baseRate = baseRate.setScale(SCALE, RoundingMode.HALF_UP);
                BigDecimal buyRate = baseRate.multiply(BUY_RATE_MULTIPLIER).setScale(SCALE, RoundingMode.HALF_UP);
                BigDecimal sellRate = baseRate.multiply(SELL_RATE_MULTIPLIER).setScale(SCALE, RoundingMode.HALF_UP);

                histories.add(ExchangeRateHistory.builder()
                        .currencyCode(currency)
                        .baseRate(baseRate)
                        .buyRate(buyRate)
                        .sellRate(sellRate)
                        .build());
            }

            exchangeRateHistoryRepository.saveAll(histories);
            log.info("환율 수신 완료: {}건 저장", histories.size());
        } catch (Exception e) {
            log.error("환율 수집 중 오류 발생", e);
        }
    }
}
