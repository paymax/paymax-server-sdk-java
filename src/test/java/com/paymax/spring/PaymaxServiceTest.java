package com.paymax.spring;

import com.paymax.exception.PaymaxException;
import com.paymax.model.Charge;
import me.jiangcai.lib.test.SpringWebTest;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author CJ
 */
@ContextConfiguration(classes = TestConfig.class)
public class PaymaxServiceTest extends SpringWebTest {

    @Autowired
    private PaymaxService paymaxService;

    @Test
    public void go() throws IOException, PaymaxException {
        ChargeRequest request = randomRequest();
        Charge charge = paymaxService.createWechatCharge(request);
        System.out.println(charge);
        assertThat(charge.getId())
                .isNotEmpty();
    }

    private ChargeRequest randomRequest() {
        ChargeRequest request = new ChargeRequest();
        request.setAmount(BigDecimal.valueOf((double) random.nextInt(100) + random.nextDouble()));
        request.setClientIpAddress("127.0.0.1");
        request.setBody("我系中文" + UUID.randomUUID().toString());
        request.setSubject("我系商品" + RandomStringUtils.randomAlphabetic(10));
        request.setOrderNumber(UUID.randomUUID().toString());
        return request;
    }


}