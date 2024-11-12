package com.challenge.converter.service.business;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CurrencyConverterServiceImpl implements CurrencyConverterService{
  private static final Map<String, Double> exchangeRates = new HashMap<>();

  static {
    exchangeRates.put("USD_PEN", 3.77);
    exchangeRates.put("PEN_USD", 0.27);
  }

  public Mono<Double> convert(String fromCurrency, String toCurrency, double amount) {
    String key = fromCurrency + "_" + toCurrency;
    if (exchangeRates.containsKey(key)) {
      return Mono.just(amount * exchangeRates.get(key));
    } else {
      return Mono.error(new IllegalArgumentException("Rate not available for " + key));
    }
  }

  public Mono<Double> getExchangeRate(String fromCurrency, String toCurrency) {
    String key = fromCurrency + "_" + toCurrency;
    if (exchangeRates.containsKey(key)) {
      return Mono.just(exchangeRates.get(key));
    } else {
      return Mono.error(new IllegalArgumentException("Rate not available for " + key));
    }
  }
}
