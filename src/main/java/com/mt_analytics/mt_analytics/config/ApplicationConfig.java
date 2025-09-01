package com.mt_analytics.mt_analytics.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
