package com.challenge.converter.service.business;

import com.challenge.converter.model.ConversionResponse;
import reactor.core.publisher.Mono;

public interface CurrencyConverterService {

  Mono<ConversionResponse> convert(String fromCurrency, String toCurrency, double amount);
  Mono<Double> getExchangeRate(String fromCurrency, String toCurrency);

}
