package com.challenge.converter.expose.web;

import com.challenge.converter.model.ConversionRequest;
import com.challenge.converter.model.ConversionResponse;
import com.challenge.converter.service.business.CurrencyConverterService;
import com.challenge.converter.utils.Constants;
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
            .map(ResponseEntity::ok)
            .onErrorResume(IllegalArgumentException.class, error -> {
              String errorMessage = Constants.MESSAGE_WARNING
                    + request.getFromCurrency() + Constants.TO + request.getToCurrency();
              return Mono.just(ResponseEntity.badRequest()
                  .body(new ConversionResponse(
                      request.getFromCurrency(),
                      request.getToCurrency(),
                      request.getAmount(),
                      Constants.DOUBLE_CERO,
                      Constants.DOUBLE_CERO,
                      errorMessage
                  )));
            });
  }
}