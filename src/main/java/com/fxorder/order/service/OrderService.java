package com.fxorder.order.service;

import com.fxorder.balance.entity.Balance;
import com.fxorder.balance.repository.BalanceRepository;
import com.fxorder.enums.CurrencyCode;
import com.fxorder.exchangerate.entity.ExchangeRateHistory;
import com.fxorder.exchangerate.repository.ExchangeRateHistoryRepository;
import com.fxorder.order.dto.OrderListItemResponse;
import com.fxorder.order.dto.OrderRequest;
import com.fxorder.order.dto.OrderResponse;
import com.fxorder.order.entity.OrderHistory;
import com.fxorder.order.repository.OrderHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderHistoryRepository orderHistoryRepository;
    private final ExchangeRateHistoryRepository exchangeRateHistoryRepository;
    private final BalanceRepository balanceRepository;

    @Transactional(readOnly = true)
    public List<OrderListItemResponse> getOrderList() {
        return orderHistoryRepository.findAllByOrderByDateTimeDesc().stream()
                .map(order -> OrderListItemResponse.builder()
                        .id(order.getId())
                        .fromAmount(order.getFromAmount())
                        .fromCurrency(order.getFromCurrency().name())
                        .toAmount(order.getToAmount())
                        .toCurrency(order.getToCurrency().name())
                        .tradeRate(order.getTradeRate())
                        .dateTime(order.getDateTime())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        CurrencyCode fromCurrencyCode = request.getFromCurrency();
        CurrencyCode toCurrencyCode = request.getToCurrency();
        BigDecimal forexAmount = request.getForexAmount();

        if (fromCurrencyCode != CurrencyCode.KRW && toCurrencyCode != CurrencyCode.KRW) {
            throw new IllegalArgumentException("모든 주문은 KRW를 기준으로 매수/매도 진행되어야 합니다.");
        }

        BigDecimal tradeRate;
        BigDecimal fromAmount;
        BigDecimal toAmount;
        if (fromCurrencyCode == CurrencyCode.KRW) {
            ExchangeRateHistory exchangeRate = exchangeRateHistoryRepository.findFirstByCurrencyCodeOrderByDateTimeDesc(toCurrencyCode)
                .orElseThrow(() -> new IllegalArgumentException("Exchange rate not found for " + toCurrencyCode));
            tradeRate = exchangeRate.getBuyRate();
            toAmount = forexAmount;
            fromAmount = forexAmount.multiply(tradeRate).setScale(0, RoundingMode.DOWN);
        } else {
            ExchangeRateHistory exchangeRate = exchangeRateHistoryRepository.findFirstByCurrencyCodeOrderByDateTimeDesc(fromCurrencyCode)
                .orElseThrow(() -> new IllegalArgumentException("Exchange rate not found for " + fromCurrencyCode));
            tradeRate = exchangeRate.getSellRate();
            fromAmount = forexAmount;
            toAmount = forexAmount.multiply(tradeRate).setScale(0, RoundingMode.DOWN);
        }

        Balance fromBalance = balanceRepository.findWithLockByCurrencyCode(fromCurrencyCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 통화에 대한 잔고가 없습니다: " + fromCurrencyCode));
        fromBalance.deductAmount(fromAmount);

        Balance toBalance = balanceRepository.findWithLockByCurrencyCode(toCurrencyCode)
                .orElseGet(() -> balanceRepository.save(Balance.builder()
                        .currencyCode(toCurrencyCode)
                        .amount(BigDecimal.ZERO)
                        .build()));
        toBalance.addAmount(toAmount);

        OrderHistory orderHistory = OrderHistory.builder()
                .fromAmount(fromAmount)
                .fromCurrency(fromCurrencyCode)
                .toAmount(toAmount)
                .toCurrency(toCurrencyCode)
                .tradeRate(tradeRate)
                .build();

        OrderHistory savedOrder = orderHistoryRepository.save(orderHistory);

        return OrderResponse.builder()
                .fromAmount(savedOrder.getFromAmount())
                .fromCurrency(savedOrder.getFromCurrency().name())
                .toAmount(savedOrder.getToAmount())
                .toCurrency(savedOrder.getToCurrency().name())
                .tradeRate(savedOrder.getTradeRate())
                .dateTime(savedOrder.getDateTime())
                .build();
    }
}
