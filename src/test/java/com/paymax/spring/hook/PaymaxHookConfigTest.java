package com.paymax.spring.hook;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymax.spring.HookTestConfig;
import com.paymax.spring.event.ChargeChangeEvent;
import me.jiangcai.lib.test.SpringWebTest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author CJ
 */
//@ActiveProfiles("paymaxHook")
@ContextConfiguration(classes = {HookTestConfig.class, PaymaxHookConfigTest.PaymaxHookConfigTestConfig.class})
@WebAppConfiguration
public class PaymaxHookConfigTest extends SpringWebTest {

    private static final Log log = LogFactory.getLog(PaymaxHookConfigTest.class);

    static boolean oneSuccess = false;
    static boolean oneFailed = false;

    @Configuration
    static class PaymaxHookConfigTestConfig {
        @EventListener(ChargeChangeEvent.class)
        public void changed(ChargeChangeEvent event) {
            log.info("订单变化:" + event.getData());
            if (event.getData().getStatus().equalsIgnoreCase("SUCCEED")) {
                oneSuccess = true;
            } else if (event.getData().getStatus().equalsIgnoreCase("FAILED")) {
                oneFailed = true;
            }
        }
    }

    @Test
    public void go() throws Exception {

        assertThat(oneSuccess)
                .isFalse();
        assertThat(oneFailed)
                .isFalse();

        // 一次成功的支付
        makeHook("success.json");
        assertThat(oneSuccess)
                .isTrue();
        assertThat(oneFailed)
                .isFalse();

        // 一次失败的支付
        makeHook("failed.json");

        assertThat(oneSuccess)
                .isTrue();
        assertThat(oneFailed)
                .isTrue();

    }

    private static final ObjectMapper objectmapper = new ObjectMapper();

    private void makeHook(String jsonName) throws Exception {
        JsonNode request = objectmapper.readTree(new ClassPathResource("/hook/" + jsonName).getURL()).get("request");
        final JsonNode headers = request.get("headers");
        String sign = headers.get("Sign").get(0).asText();

        mockMvc.perform(
                post("/notify/abc")
                        .header("Sign", sign)
                        .contentType(headers.get("Content-type").get(0).asText())
                        .content(request.get("content").asText())
        )
                .andExpect(status().isOk())
                .andExpect(content().string("success"));

    }

}