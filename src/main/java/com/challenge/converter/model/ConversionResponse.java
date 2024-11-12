package com.challenge.converter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ConversionResponse {
  String fromCurrency;
  String toCurrency;
  double amount;
  double convertedAmount;
  double exchangeRate;
  String responseMessage;
}
