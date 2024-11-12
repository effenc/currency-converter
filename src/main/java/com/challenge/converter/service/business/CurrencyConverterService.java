package com.challenge.converter.service.business;

import reactor.core.publisher.Mono;

public interface CurrencyConverterService {

  Mono<Double> convert(String fromCurrency, String toCurrency, double amount);
  Mono<Double> getExchangeRate(String fromCurrency, String toCurrency);

}
