package com.paymax.spring.hook.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.paymax.model.Charge;
import com.paymax.model.Refund;
import com.paymax.sign.RSA;
import com.paymax.spring.event.ChargeChangeEvent;
import com.paymax.spring.event.RefundChangeEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author CJ
 */
@Controller
public class PaymaxWebHook {

    @Autowired
    private Environment environment;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    private static final Log log = LogFactory.getLog(PaymaxWebHook.class);

    @RequestMapping(method = RequestMethod.POST, value = "${com.paymax.spring.hookUriWithoutAppId}/{app}")
    public ResponseEntity<String> webRequest(HttpServletRequest request, @RequestHeader("Sign") String sign
            , @PathVariable("app") String app) throws IOException {
        final String content = StreamUtils.copyToString(request.getInputStream(), Charset.forName(RSA.CHAR_SET));
        log.debug("来访数据:" + content);
        if (!RSA.verify(content
                , sign, environment.getRequiredProperty("com.paymax.spring.default.publicKey"))) {
            log.info("未获得信任的通知回调；来源IP:" + request.getRemoteAddr());
            throw new IllegalAccessError("bad sign");
        }

        final JSONObject jsonObject = JSON.parseObject(content);
        String notifyNo = jsonObject.getString("notifyNo");
        String type = jsonObject.getString("type");
        log.debug("received " + type + " change event:" + notifyNo);

        if (type.equalsIgnoreCase("CHARGE")) {
            applicationEventPublisher.publishEvent(new ChargeChangeEvent(jsonObject.getObject("data", Charge.class)));
        } else if (type.equalsIgnoreCase("REFUND")) {
            applicationEventPublisher.publishEvent(new RefundChangeEvent(jsonObject.getObject("data", Refund.class)));
        } else {
            throw new IllegalArgumentException("not support for type:" + type);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body("success");
    }

}
