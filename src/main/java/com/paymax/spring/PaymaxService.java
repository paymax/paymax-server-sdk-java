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
     * 发起<a href="https://github.com/paymax/paymax-doc/blob/master/API%E6%96%87%E6%A1%A3.md#%E5%BE%AE%E4%BF%A1%E7%A7%BB%E5%8A%A8%E6%94%AF%E4%BB%98">微信移动支付请求</a>
     *
     * @param app     可选，参考{@link PaymaxSpringConfig}
     * @param request 请求细节
     * @return paymax 唯一支付订单号
     */
    Charge createAppWechatCharge(AppInfo app, ChargeRequest request) throws PaymaxException, IOException;

    /**
     * 发起<a href="https://github.com/paymax/paymax-doc/blob/master/API%E6%96%87%E6%A1%A3.md#%E5%BE%AE%E4%BF%A1%E7%A7%BB%E5%8A%A8%E6%94%AF%E4%BB%98">微信移动支付请求</a>
     *
     * @param request 请求细节
     * @return paymax 唯一支付订单号
     */
    Charge createAppWechatCharge(ChargeRequest request) throws PaymaxException, IOException;

    /**
     * 发起微信公众号（C2B扫码）支付
     *
     * @param app     可选，参考{@link PaymaxSpringConfig}
     * @param request 请求细节
     * @param openId  微信用户的openId
     * @return paymax 唯一支付订单号; credential.wechat_csb.qr_code
     */
    Charge createWechatScanCharge(AppInfo app, ChargeRequest request, String openId) throws PaymaxException, IOException;

    /**
     * 发起微信公众号（C2B扫码）支付
     *
     * @param request 请求细节
     * @param openId  微信用户的openId
     * @return paymax 唯一支付订单号；credential.wechat_csb.qr_code
     */
    Charge createWechatScanCharge(ChargeRequest request, String openId) throws PaymaxException, IOException;

    /**
     * 发起<a href="https://github.com/paymax/paymax-doc/blob/master/API%E6%96%87%E6%A1%A3.md#%E6%8B%89%E5%8D%A1%E6%8B%89-pc-%E7%BD%91%E5%85%B3%E6%94%AF%E4%BB%98">卡拉卡PC网关支付请求</a>
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
     * 发起<a href="https://github.com/paymax/paymax-doc/blob/master/API%E6%96%87%E6%A1%A3.md#%E6%8B%89%E5%8D%A1%E6%8B%89-pc-%E7%BD%91%E5%85%B3%E6%94%AF%E4%BB%98">卡拉卡PC网关支付请求</a>
     *
     * @param request   请求细节
     * @param userId    必填，用户在商户系统中的唯一标识，请使用纯数字格式；
     * @param returnUrl 必填，支付完成后的回调地址；
     * @return paymax 唯一支付订单号
     */
    Charge createPCCharge(ChargeRequest request, Number userId, String returnUrl) throws PaymaxException
            , IOException;

    /**
     * 发起<a href="https://github.com/paymax/paymax-doc/blob/master/API%E6%96%87%E6%A1%A3.md#%E6%8B%89%E5%8D%A1%E6%8B%89-h5-%E6%94%AF%E4%BB%98">拉卡拉-h5-支付请求</a>
     *
     * @param request   请求细节
     * @param userId    必填，用户在商户系统中的唯一标识，请使用纯数字格式；
     * @param returnUrl 必填，支付完成后的回调地址；
     * @param showUrl   支付界面的返回按钮跳转的地址；
     * @return paymax 唯一支付订单号
     */
    Charge createH5Charge(ChargeRequest request, Number userId, String returnUrl, String showUrl) throws IOException, PaymaxException;

    /**
     * 发起<a href="https://github.com/paymax/paymax-doc/blob/master/API%E6%96%87%E6%A1%A3.md#%E6%8B%89%E5%8D%A1%E6%8B%89-h5-%E6%94%AF%E4%BB%98">拉卡拉-h5-支付请求</a>
     *
     * @param request   请求细节
     * @param userId    必填，用户在商户系统中的唯一标识，请使用纯数字格式；
     * @param returnUrl 必填，支付完成后的回调地址；
     * @param showUrl   支付界面的返回按钮跳转的地址；
     * @return paymax 唯一支付订单号
     */
    Charge createH5Charge(AppInfo appInfo, ChargeRequest request, Number userId, String returnUrl, String showUrl) throws PaymaxException, IOException;
}
