package com.challenge.converter.service.business;

import com.challenge.converter.config.ExchangeRateProperties;
import com.challenge.converter.model.ConversionResponse;
import com.challenge.converter.utils.Constants;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CurrencyConverterServiceImpl implements CurrencyConverterService {
  private final Map<String, Double> exchangeRates = new HashMap<>();

  @Autowired
  private ExchangeRateProperties exchangeRateProperties;

  @PostConstruct
  public void loadExchangeRates() {
    Flux.fromIterable(exchangeRateProperties.getExchangeRates())
        .doOnNext(rate -> exchangeRates.put(rate.getPair(), rate.getRate()))
        .subscribe();
  }

  public Mono<ConversionResponse> convert(String fromCurrency, String toCurrency, double amount) {
    String key = fromCurrency + Constants.GUION + toCurrency;

    return Mono.justOrEmpty(exchangeRates.get(key))
        .map(rate -> {
          double convertedAmount = amount * rate;
          return ConversionResponse.builder()
              .fromCurrency(fromCurrency)
              .toCurrency(toCurrency)
              .amount(amount)
              .convertedAmount(convertedAmount)
              .exchangeRate(rate)
              .responseMessage(Constants.RESPONSE_MESSAGE_SUCCESS)
              .build();
        })
        .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ILEGAL_ARGUMENT + key)))
        .doOnError(error -> log.error( Constants.MESSAGE_ERROR + error.getMessage()));
  }

  public Mono<Double> getExchangeRate(String fromCurrency, String toCurrency) {
    String key = fromCurrency + Constants.GUION + toCurrency;

    return Mono.justOrEmpty(exchangeRates.get(key))
        .switchIfEmpty(Mono.error(new IllegalArgumentException(Constants.ILEGAL_ARGUMENT + key)));
  }
}
