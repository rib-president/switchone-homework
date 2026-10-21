package com.fxorder.exchangerate.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ExchangeRateLatestResponse {

    private final List<ExchangeRateDto> exchangeRateList;

    public ExchangeRateLatestResponse(List<ExchangeRateDto> exchangeRateList) {
        this.exchangeRateList = exchangeRateList;
    }
}
