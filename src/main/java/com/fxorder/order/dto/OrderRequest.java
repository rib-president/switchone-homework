package com.fxorder.order.dto;

import com.fxorder.enums.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private BigDecimal forexAmount;
    private CurrencyCode fromCurrency;
    private CurrencyCode toCurrency;
}