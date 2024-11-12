package com.challenge.converter.config;

import com.challenge.converter.model.ExchangeRate;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "currency")
public class ExchangeRateProperties {
  private List<ExchangeRate> exchangeRates;
}