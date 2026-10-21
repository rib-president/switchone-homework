package com.fxorder.balance.service;

import com.fxorder.balance.entity.Balance;
import com.fxorder.balance.repository.BalanceRepository;
import com.fxorder.enums.CurrencyCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BalanceRunnerService implements CommandLineRunner {

  private final BalanceRepository balanceRepository;

  @Override
  @Transactional
  public void run(String... args) {
    if (balanceRepository.count() > 0) {
      return;
    }

    log.info("Balance 초기 데이터 적재를 시작합니다.");

    List<Balance> initialBalances = List.of(
        Balance.builder()
            .currencyCode(CurrencyCode.USD)
            .amount(new BigDecimal("1000.00")) // 1,000 달러
            .build(),
        Balance.builder()
            .currencyCode(CurrencyCode.CNY)
            .amount(new BigDecimal("10000.00")) // 10,000 위안
            .build(),
        Balance.builder()
            .currencyCode(CurrencyCode.JPY)
            .amount(new BigDecimal("100000.00")) // 100,000 엔
            .build(),
        Balance.builder()
            .currencyCode(CurrencyCode.KRW)
            .amount(new BigDecimal("1000000.00")) // 1,000,000 원
            .build()
    );

    balanceRepository.saveAll(initialBalances);

    log.info("Balance 초기 데이터 적재 완료.");
  }
}
