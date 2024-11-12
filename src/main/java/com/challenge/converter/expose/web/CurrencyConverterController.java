package com.challenge.converter.expose.web;

import com.challenge.converter.model.ConversionRequest;
import com.challenge.converter.model.ConversionResponse;
import com.challenge.converter.service.business.CurrencyConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/convert")
public class CurrencyConverterController {

  @Autowired
  private CurrencyConverterService currencyConverterService;

  @PostMapping
  public Mono<ResponseEntity<ConversionResponse>>
      convertCurrency(@RequestBody ConversionRequest request) {
    return currencyConverterService.convert(request.getFromCurrency(),
            request.getToCurrency(), request.getAmount())
        .zipWith(currencyConverterService.getExchangeRate(request.getFromCurrency(),
            request.getToCurrency()))
        .map(conversionResult -> {
          double convertedAmount = conversionResult.getT1();
          double exchangeRate = conversionResult.getT2();

          ConversionResponse response = ConversionResponse.builder()
              .fromCurrency(request.getFromCurrency())
              .toCurrency(request.getToCurrency())
              .amount(request.getAmount())
              .convertedAmount(convertedAmount)
              .exchangeRate(exchangeRate)
              .build();

          return ResponseEntity.ok(response);
        });
  }
}
