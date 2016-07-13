package com.paymax.example;

import com.paymax.exception.AuthorizationException;
import com.paymax.exception.InvalidRequestException;
import com.paymax.exception.InvalidResponseException;
import com.paymax.model.Charge;

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
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("metadata_key1","metadata_value1");
        metadata.put("metadata_key2","metadata_value2");
        chargeMap.put("metadata",metadata);
        try {
            Charge charge = Charge.create(chargeMap);
            printResult(charge);
        } catch (AuthorizationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        }

    }

    /**
     * 输出请求结果
     *
     * @param charge
     */
    private void printResult(Charge charge) {
        if (charge.getReqSuccessFlag()){//http请求成功
            System.out.println(charge);
        }else {//http请求失败
            String failureCode = charge.getFailureCode();
            String failureMsg = charge.getFailureMsg();
        }
    }

    /**
     * 查询充值订单
     */
    public void retrieve() {
        try {
            Charge charge = Charge.retrieve("ch_6e2e029873f8b0a87035e03f");
            printResult(charge);
        } catch (AuthorizationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        }
    }
}
