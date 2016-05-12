package com.payright.example;

import com.payright.exception.AuthorizationException;
import com.payright.exception.InvalidRequestException;
import com.payright.model.Charge;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
/**
 * Created by xiaowei.wang on 2016/4/27.
 */
public class ChargeExample {

    public static void main(String[] args) {
        ChargeExample ce = new ChargeExample();
        ce.charge();
        ce.retrieve();
    }

    /**
     * 创建充值订单
     */
    public void charge() {
        Charge charge = null;
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", 1);
        chargeMap.put("subject", "Your Subject");
        chargeMap.put("body", "Your Body");
        chargeMap.put("order_no", UUID.randomUUID());
        chargeMap.put("channel", "alipay_app");
        chargeMap.put("client_ip", "127.0.0.1");
        chargeMap.put("app", "app_49b0f1dd741646d2b277524de2785836");
        chargeMap.put("currency","CNY");
        chargeMap.put("description","description");
        try {
            System.out.println( Charge.create(chargeMap));
        } catch (AuthorizationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询充值订单
     */
    public void retrieve() {
        try {
            System.out.println(Charge.retrieve("ch_4671d8bbac347cdd33669b2a"));
        } catch (AuthorizationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        }
    }
}
