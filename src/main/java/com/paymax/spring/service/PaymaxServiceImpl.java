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
import java.util.UUID;

/**
 * @author CJ
 */
@Service
public class PaymaxServiceImpl implements PaymaxService {

    @Autowired
    private Environment environment;

    @Override
    public Charge createAppWechatCharge(AppInfo app, ChargeRequest request) throws PaymaxException, IOException {
        app = useDefault(app);
        Map<String, Object> chargeMap = createChargeMap(request, app);
        //请根据渠道要求确定是否需要传递extra字段
        chargeMap.put("channel", "wechat_app");

        return createCharge(app, chargeMap);
    }

    @Override
    public Charge createAppWechatCharge(ChargeRequest request) throws PaymaxException, IOException {
        return createAppWechatCharge(null, request);
    }

    @Override
    public Charge createWechatScanCharge(AppInfo app, ChargeRequest request, String openId) throws PaymaxException, IOException {
        app = useDefault(app);
        Map<String, Object> chargeMap = createChargeMap(request, app);
        //请根据渠道要求确定是否需要传递extra字段
        chargeMap.put("channel", "wechat_csb");
        Map<String, Object> extra = new HashMap<String, Object>();
        extra.put("open_id", openId);
        chargeMap.put("extra", extra);

        return createCharge(app, chargeMap);
    }

    @Override
    public Charge createWechatScanCharge(ChargeRequest request, String openId) throws PaymaxException, IOException {
        return createWechatScanCharge(null, request, openId);
    }

    @Override
    public Charge createWechatCharge(AppInfo app, ChargeRequest request, String openId) throws PaymaxException, IOException {
        app = useDefault(app);
        Map<String, Object> chargeMap = createChargeMap(request, app);
        chargeMap.put("channel", "wechat_wap");
        Map<String, Object> extra = new HashMap<String, Object>();
        extra.put("open_id", openId);
        chargeMap.put("extra", extra);

        return createCharge(app, chargeMap);
    }

    @Override
    public Charge createWechatCharge(ChargeRequest request, String openId) throws PaymaxException, IOException {
        return createWechatCharge(null, request, openId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public String javascriptForWechatCharge(Charge charge) {
        Map<String, Object> wap = (Map<String, Object>) charge.getCredential().get("wechat_wap");
        String params = (String) wap.get("jsApiParams");
        String uuid = "tmp_"+UUID.randomUUID().toString().replace("-", "");
        @SuppressWarnings("StringBufferReplaceableByString")
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("var ").append(uuid).append(" = function () {\n" +
                "    function onBridgeReady() {\n" +
                "        WeixinJSBridge.invoke(\n" +
                "            'getBrandWCPayRequest',");
        stringBuilder.append(params).append(",");
        stringBuilder.append("function (res) {\n" +
                "                if (res.err_msg == \"get_brand_wcpay_request：ok\") {\n" +
                "                }\n" +
                "            }\n" +
                "        );\n" +
                "    }\n" +
                "\n" +
                "    if (typeof WeixinJSBridge == \"undefined\") {\n" +
                "        if (document.addEventListener) {\n" +
                "            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);\n" +
                "        } else if (document.attachEvent) {\n" +
                "            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);\n" +
                "            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);\n" +
                "        }\n" +
                "    } else {\n" +
                "        onBridgeReady();\n" +
                "    }\n" +
                "};");
        stringBuilder.append(uuid).append("();");
        return stringBuilder.toString();
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

    @Override
    public Charge createH5Charge(ChargeRequest request, Number userId, String returnUrl, String showUrl) throws IOException, PaymaxException {
        return createH5Charge(null, request, userId, returnUrl, showUrl);
    }

    @Override
    public Charge createH5Charge(AppInfo app, ChargeRequest request, Number userId, String returnUrl, String showUrl) throws PaymaxException, IOException {
        app = useDefault(app);
        Map<String, Object> chargeMap = createChargeMap(request, app);
        //请根据渠道要求确定是否需要传递extra字段
        chargeMap.put("channel", "lakala_h5");
        Map<String, Object> extra = new HashMap<String, Object>();
        extra.put("user_id", userId);
        extra.put("return_url", returnUrl);
        extra.put("show_url", showUrl);
        chargeMap.put("extra", extra);

        return createCharge(app, chargeMap);
    }
}
