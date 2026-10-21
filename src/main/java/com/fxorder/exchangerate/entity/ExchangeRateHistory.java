package com.fxorder.exchangerate.entity;

import com.fxorder.enums.CurrencyCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "exchange_rate_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeRateHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_code", nullable = false, length = 3)
    private CurrencyCode currencyCode;

    @Column(name = "base_rate", nullable = false, precision = 18, scale = 2)
    private BigDecimal baseRate;

    @Column(name = "buy_rate", nullable = false, precision = 18, scale = 2)
    private BigDecimal buyRate;

    @Column(name = "sell_rate", nullable = false, precision = 18, scale = 2)
    private BigDecimal sellRate;

    @CreationTimestamp
    @Column(name = "date_time", nullable = false, updatable = false)
    private LocalDateTime dateTime;

    @Builder
    public ExchangeRateHistory(CurrencyCode currencyCode, BigDecimal baseRate,
                               BigDecimal buyRate, BigDecimal sellRate) {
        this.currencyCode = currencyCode;
        this.baseRate = baseRate;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
    }
}
