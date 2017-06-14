package com.paymax.spring;

import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author CJ
 */
@Import(TestConfig.class)
@EnableWebMvc
public class HookTestConfig {
}
