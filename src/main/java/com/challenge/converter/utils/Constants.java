package com.challenge.converter.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

  public static final String RESPONSE_MESSAGE_SUCCESS = "Conversion at current exchange rate";
  public static final String ILEGAL_ARGUMENT = "Rate not available for ";
  public static final String MESSAGE_ERROR = "Error during conversion";
  public static final String MESSAGE_WARNING = "Tipo de cambio no disponible para ";
  public static final String GUION = "_";
  public static final String TO = " to ";
  public static final Double DOUBLE_CERO = 0.0;
}
