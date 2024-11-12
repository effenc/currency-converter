package com.challenge.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.challenge.converter.expose.web.CurrencyConverterController;
import com.challenge.converter.model.ConversionRequest;
import com.challenge.converter.model.ConversionResponse;
import com.challenge.converter.service.business.CurrencyConverterService;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

@SpringBootTest
class ConverterApplicationTests {

	@Mock
	private CurrencyConverterService currencyConverterService;

	@InjectMocks
	private CurrencyConverterController currencyConverterController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testConvertCurrency() {
		ConversionRequest request = ConversionRequest.builder()
				.fromCurrency("USD")
				.toCurrency("PEN")
				.amount(100.0)
				.build();
		when(currencyConverterService.convert(anyString(), anyString(), eq(100.0)))
				.thenReturn(Mono.just(85.0));
		when(currencyConverterService.getExchangeRate(anyString(), anyString()))
				.thenReturn(Mono.just(0.85));

		Mono<ResponseEntity<ConversionResponse>> responseMono
				= currencyConverterController.convertCurrency(request);

		responseMono.subscribe(response -> {
			assertEquals(85.0, Objects.requireNonNull(response.getBody()).getConvertedAmount());
			assertEquals(0.85, response.getBody().getExchangeRate());
		});
	}
}
