package com.fxorder.order.entity;

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
@Table(name = "order_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal fromAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_currency", nullable = false, length = 3)
    private CurrencyCode fromCurrency;

    @Column(name = "to_amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal toAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_currency", nullable = false, length = 3)
    private CurrencyCode toCurrency;

    @Column(name = "trade_rate", nullable = false, precision = 18, scale = 2)
    private BigDecimal tradeRate;

    @CreationTimestamp
    @Column(name = "date_time", nullable = false, updatable = false)
    private LocalDateTime dateTime;

    @Builder
    public OrderHistory(BigDecimal fromAmount, CurrencyCode fromCurrency, BigDecimal toAmount, 
                 CurrencyCode toCurrency, BigDecimal tradeRate) {
        this.fromAmount = fromAmount;
        this.fromCurrency = fromCurrency;
        this.toAmount = toAmount;
        this.toCurrency = toCurrency;
        this.tradeRate = tradeRate;
    }
}