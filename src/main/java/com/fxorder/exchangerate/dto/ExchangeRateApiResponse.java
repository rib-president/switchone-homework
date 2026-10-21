package com.fxorder.exchangerate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ExchangeRateApiResponse {

    private String result;

    @JsonProperty("base_code")
    private String baseCode;

    private Map<String, BigDecimal> rates;
}
