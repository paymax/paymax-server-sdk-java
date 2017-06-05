package com.paymax.spring;

import com.paymax.model.AppInfo;
import com.paymax.spring.hook.PaymaxHookConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 如果设置了系统属性
 * <ul>
 * <li>com.paymax.spring.default.privateKey 默认商户私钥</li>
 * <li>com.paymax.spring.default.secretKey 默认商户的SecretKey</li>
 * <li>com.paymax.spring.default.publicKey 默认平台公钥</li>
 * <li>com.paymax.spring.default.app 默认应用的appKey</li>
 * </ul>则调用服务时相应{@link AppInfo}可以缺省
 * <p>
 * 同时提供了web hook 支持 需要添加以下系统属性
 * <ul>
 * <li>com.paymax.spring.hookUriWithoutAppId 假定部署服务器是 abc.com 部署context 是 test;如果此处申明/notify 则需要填写到拉卡
 * 拉的通知地址 应该是 http://abc.com/test/notify/myApp 其中myApp是作为一个识别符让控制器有机会使用最合适的支付子系统，如果无需识别
 * 可填入任意字符，但不可或缺。</li>
 * </ul>
 *
 * @author CJ
 */
@Configuration
@Import(PaymaxHookConfig.class)
@ComponentScan("com.paymax.spring.service")
public class PaymaxSpringConfig {
}
