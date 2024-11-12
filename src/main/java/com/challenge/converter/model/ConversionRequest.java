package com.challenge.converter.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversionRequest {
  String fromCurrency;
  String toCurrency;
  double amount;
}
