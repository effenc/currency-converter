package com.challenge.converter;

import static org.mockito.Mockito.when;

import com.challenge.converter.expose.web.CurrencyConverterController;
import com.challenge.converter.model.ConversionResponse;
import com.challenge.converter.service.business.CurrencyConverterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class ConverterApplicationTests {

	@Mock
	private CurrencyConverterService currencyConverterService;

	@InjectMocks
	private CurrencyConverterController currencyConverterController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testConvertCurrencySuccess() {
		double amount = 100.0;
		String fromCurrency = "USD";
		String toCurrency = "PEN";
		double rate = 3.77;
		double convertedAmount = amount * rate;

		ConversionResponse response = ConversionResponse.builder()
				.fromCurrency(fromCurrency)
				.toCurrency(toCurrency)
				.amount(amount)
				.convertedAmount(convertedAmount)
				.exchangeRate(rate)
				.build();

		when(currencyConverterService.convert(fromCurrency, toCurrency, amount))
				.thenReturn(Mono.just(response));

		StepVerifier.create(currencyConverterService.convert(fromCurrency, toCurrency, amount))
				.expectNextMatches(resp -> resp.getConvertedAmount() == convertedAmount &&
						resp.getExchangeRate() == rate &&
						resp.getFromCurrency().equals(fromCurrency) &&
						resp.getToCurrency().equals(toCurrency))
				.verifyComplete();
	}

	@Test
	void testConvertCurrencyRateNotAvailable() {
		String fromCurrency = "USD";
		String toCurrency = "JPY";
		double amount = 100.0;

		when(currencyConverterService.convert(fromCurrency, toCurrency, amount))
				.thenReturn(Mono.error(new IllegalArgumentException("Rate not available for USD_JPY")));

		// Prueba usando StepVerifier para error
		StepVerifier.create(currencyConverterService.convert(fromCurrency, toCurrency, amount))
				.expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException &&
						throwable.getMessage().equals("Rate not available for USD_JPY"))
				.verify();
	}
}
