package com.paymax.spring.service;

import com.paymax.exception.AuthorizationException;
import com.paymax.exception.InvalidRequestException;
import com.paymax.exception.InvalidResponseException;
import com.paymax.exception.PaymaxException;
import com.paymax.model.AppInfo;
import com.paymax.model.Charge;
import com.paymax.spring.ChargeRequest;
import com.paymax.spring.PaymaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CJ
 */
@Service
public class PaymaxServiceImpl implements PaymaxService {

    @Autowired
    private Environment environment;

    @Override
    public Charge createWechatCharge(AppInfo app, ChargeRequest request) throws PaymaxException, IOException {
        app = useDefault(app);
        Map<String, Object> chargeMap = createChargeMap(request, app);
        //请根据渠道要求确定是否需要传递extra字段
        chargeMap.put("channel", "wechat_app");

        return createCharge(app, chargeMap);
    }

    @Override
    public Charge createWechatCharge(ChargeRequest request) throws PaymaxException, IOException {
        return createWechatCharge(null, request);
    }

    private Charge createCharge(AppInfo app, Map<String, Object> chargeMap) throws AuthorizationException, IOException, InvalidRequestException, InvalidResponseException {
        return Charge.create(app, chargeMap);
    }

    private AppInfo useDefault(AppInfo supplier) {
        if (supplier == null) {
            supplier = new AppInfo();
        }
        if (StringUtils.isEmpty(supplier.getApp())) {
            supplier.setApp(environment.getRequiredProperty("com.paymax.spring.default.app"));
        }
        if (StringUtils.isEmpty(supplier.getSecretKey())
                || StringUtils.isEmpty(supplier.getPrivateKey())
                || StringUtils.isEmpty(supplier.getPublicKey())
                ) {
            supplier.setPrivateKey(environment.getRequiredProperty("com.paymax.spring.default.privateKey"));
            supplier.setPublicKey(environment.getRequiredProperty("com.paymax.spring.default.publicKey"));
            supplier.setSecretKey(environment.getRequiredProperty("com.paymax.spring.default.secretKey"));
        }
        return supplier;
    }

    private Map<String, Object> createChargeMap(ChargeRequest request, AppInfo appInfo) {
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", request.getAmount());
        chargeMap.put("subject", request.getSubject());
        chargeMap.put("body", request.getBody());
        chargeMap.put("order_no", request.getOrderNumber());
        chargeMap.put("client_ip", request.getClientIpAddress());
        chargeMap.put("app", appInfo.getApp());
//        chargeMap.put("currency","CNY");
        if (!StringUtils.isEmpty(request.getDescription()))
            chargeMap.put("description", request.getDescription());
        if (!CollectionUtils.isEmpty(request.getMetadata()))
            chargeMap.put("metadata", request.getMetadata());
        return chargeMap;
    }

    @Override
    public Charge createPCCharge(AppInfo app, ChargeRequest request, Number userId, String returnUrl) throws PaymaxException, IOException {
        app = useDefault(app);
        Map<String, Object> chargeMap = createChargeMap(request, app);
        //请根据渠道要求确定是否需要传递extra字段
        chargeMap.put("channel", "lakala_web");
        Map<String, Object> extra = new HashMap<String, Object>();
        extra.put("user_id", userId);
        extra.put("return_url", returnUrl);
        chargeMap.put("extra", extra);

        return createCharge(app, chargeMap);
    }

    @Override
    public Charge createPCCharge(ChargeRequest request, Number userId, String returnUrl) throws PaymaxException, IOException {
        return createPCCharge(null, request, userId, returnUrl);
    }
}
