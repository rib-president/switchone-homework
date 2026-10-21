package com.fxorder.exchangerate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDetailResponse {
    private String currency;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal buyRate;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal tradeStanRate;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal sellRate;
    
    private LocalDateTime dateTime;
}
