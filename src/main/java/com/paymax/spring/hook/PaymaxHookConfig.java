package com.paymax.spring.hook;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * <a href="https://github.com/paymax/paymax-doc/blob/master/webhooks%E9%80%9A%E7%9F%A5.md">文档</a>
 *
 * @author CJ
 */
//@Profile("paymaxHook")
@Configuration
@ComponentScan("com.paymax.spring.hook.controller")
public class PaymaxHookConfig {
}
