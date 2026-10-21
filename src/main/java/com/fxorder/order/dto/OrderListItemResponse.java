package com.fxorder.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderListItemResponse {
    private Long id;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal fromAmount;
    
    private String fromCurrency;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal toAmount;
    
    private String toCurrency;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal tradeRate;
    
    private LocalDateTime dateTime;
}