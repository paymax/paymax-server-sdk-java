package com.paymax.spring;

import com.paymax.model.AppInfo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 如果设置了系统属性
 * <ul>
 * <li>com.paymax.spring.default.privateKey 默认商户私钥</li>
 * <li>com.paymax.spring.default.secretKey 默认商户的SecretKey</li>
 * <li>com.paymax.spring.default.publicKey 默认平台公钥</li>
 * <li>com.paymax.spring.default.app 默认应用的appKey</li>
 * </ul>则调用服务时相应{@link AppInfo}可以缺省
 *
 * @author CJ
 */
@Configuration
@ComponentScan("com.paymax.spring.service")
public class PaymaxSpringConfig {
}
