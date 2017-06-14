package com.paymax.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * @author CJ
 */
@Configuration
@Import(PaymaxSpringConfig.class)
@PropertySource("classpath:/test_paymax.properties")
public class TestConfig {
}
