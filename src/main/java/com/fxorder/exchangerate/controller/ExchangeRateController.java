package com.fxorder.exchangerate.controller;

import com.fxorder.exchangerate.dto.ExchangeRateDetailResponse;
import com.fxorder.exchangerate.dto.ExchangeRateLatestResponse;
import com.fxorder.exchangerate.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange-rate")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping("/latest")
    public ExchangeRateLatestResponse getLatestRates() {
        return exchangeRateService.getLatestRates();
    }

    @GetMapping("/latest/{currency}")
    public ExchangeRateDetailResponse getLatestRateByCurrency(@PathVariable("currency") String currency) {
        return exchangeRateService.getLatestRateByCurrency(currency);
    }
}
