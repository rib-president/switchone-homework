package com.fxorder.balance.entity;

import com.fxorder.enums.CurrencyCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "balance")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_code", nullable = false, unique = true, length = 3)
    private CurrencyCode currencyCode;

    @Column(name = "amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Builder
    public Balance(CurrencyCode currencyCode, BigDecimal amount) {
        this.currencyCode = currencyCode;
        this.amount = amount != null ? amount : BigDecimal.ZERO;
    }

    public void deductAmount(BigDecimal deduct) {
        if (this.amount.compareTo(deduct) < 0) {
            throw new IllegalArgumentException("잔고가 부족합니다: " + this.currencyCode);
        }
        this.amount = this.amount.subtract(deduct);
    }

    public void addAmount(BigDecimal addition) {
        this.amount = this.amount.add(addition);
    }
}