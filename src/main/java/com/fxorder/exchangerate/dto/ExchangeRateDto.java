package com.fxorder.exchangerate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ExchangeRateDto {

    private final String currency;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final BigDecimal buyRate;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final BigDecimal tradeStanRate;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final BigDecimal sellRate;
    
    private final LocalDateTime dateTime;

    public ExchangeRateDto(String currency, BigDecimal buyRate, BigDecimal tradeStanRate,
                           BigDecimal sellRate, LocalDateTime dateTime) {
        this.currency = currency;
        this.buyRate = buyRate;
        this.tradeStanRate = tradeStanRate;
        this.sellRate = sellRate;
        this.dateTime = dateTime;
    }
}
