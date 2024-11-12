package com.challenge.converter.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversionResponse {
  String fromCurrency;
  String toCurrency;
  double amount;
  double convertedAmount;
  double exchangeRate;
}
