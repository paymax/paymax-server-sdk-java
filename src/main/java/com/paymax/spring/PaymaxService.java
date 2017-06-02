package com.paymax.spring;

import com.paymax.exception.PaymaxException;
import com.paymax.model.AppInfo;
import com.paymax.model.Charge;

import java.io.IOException;

/**
 * @author CJ
 */
public interface PaymaxService {

    /**
     * 发起<a href="https://github.com/paymax/paymax-doc/blob/master/API%E6%96%87%E6%A1%A3.md#微信移动支付">微信移动支付请求</a>
     *
     * @param app     可选，参考{@link PaymaxSpringConfig}
     * @param request 请求细节
     * @return paymax 唯一支付订单号
     */
    Charge createWechatCharge(AppInfo app, ChargeRequest request) throws PaymaxException, IOException;

    /**
     * 发起<a href="https://github.com/paymax/paymax-doc/blob/master/API%E6%96%87%E6%A1%A3.md#微信移动支付">微信移动支付请求</a>
     *
     * @param request 请求细节
     * @return paymax 唯一支付订单号
     */
    Charge createWechatCharge(ChargeRequest request) throws PaymaxException, IOException;

    /**
     * 发起<a href="https://github.com/paymax/paymax-doc/blob/master/API%E6%96%87%E6%A1%A3.md#拉卡拉-pc-网关支付">卡拉卡PC网关支付请求</a>
     *
     * @param app       可选，参考{@link PaymaxSpringConfig}
     * @param request   请求细节
     * @param userId    必填，用户在商户系统中的唯一标识，请使用纯数字格式；
     * @param returnUrl 必填，支付完成后的回调地址；
     * @return paymax 唯一支付订单号
     */
    Charge createPCCharge(AppInfo app, ChargeRequest request, Number userId, String returnUrl) throws PaymaxException
            , IOException;

    /**
     * 发起<a href="https://github.com/paymax/paymax-doc/blob/master/API%E6%96%87%E6%A1%A3.md#拉卡拉-pc-网关支付">卡拉卡PC网关支付请求</a>
     *
     * @param request   请求细节
     * @param userId    必填，用户在商户系统中的唯一标识，请使用纯数字格式；
     * @param returnUrl 必填，支付完成后的回调地址；
     * @return paymax 唯一支付订单号
     */
    Charge createPCCharge(ChargeRequest request, Number userId, String returnUrl) throws PaymaxException
            , IOException;
}
