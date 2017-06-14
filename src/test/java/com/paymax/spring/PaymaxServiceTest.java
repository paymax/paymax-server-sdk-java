package com.paymax.spring;

import com.paymax.exception.PaymaxException;
import com.paymax.model.Charge;
import me.jiangcai.lib.test.SpringWebTest;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Ignore;
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
    @Ignore
    public void h5() throws IOException, PaymaxException {
        ChargeRequest request = randomRequest();
        Charge charge = paymaxService.createH5Charge(request,1,"http://www.baidu.com","http://www.baidu.com");
        System.out.println(charge);
        assertThat(charge.getId())
                .isNotEmpty();
    }

    @Test
    public void appWechat() throws IOException, PaymaxException {
        ChargeRequest request = randomRequest();
        Charge charge = paymaxService.createAppWechatCharge(request);
        System.out.println(charge);
        assertThat(charge.getId())
                .isNotEmpty();
    }

    @Test
    public void wechatScan() throws IOException, PaymaxException {
        ChargeRequest request = randomRequest();
        Charge charge = paymaxService.createWechatScanCharge(request,"obc-jswk25IUGp3q8RPTYu083rmk");
        System.out.println(charge);
        assertThat(charge.getId())
                .isNotEmpty();
    }

    @Test
    public void wechat() throws IOException, PaymaxException {
        ChargeRequest request = randomRequest();
        Charge charge = paymaxService.createWechatCharge(request,"o6Wfs1Y4sktqIILhSx7upmcCCa0s");
        System.out.println(charge);
        System.out.println(paymaxService.javascriptForWechatCharge(charge));
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