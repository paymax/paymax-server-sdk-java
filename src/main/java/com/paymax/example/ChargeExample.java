package com.paymax.example;

import com.alibaba.fastjson.JSON;
import com.paymax.exception.AuthorizationException;
import com.paymax.exception.InvalidRequestException;
import com.paymax.exception.InvalidResponseException;
import com.paymax.model.Charge;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 注：本DEMO或文档中所示的默认配置参数仅供参考，接入方须视各自系统及交易情况进行相应的调整。如因采用默认配置参数导致交易异常及造成相关损失的，我司不承担相关责任。
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
        chargeMap.put("app", "app_7hqF2S6GYXET457i");
        chargeMap.put("currency","CNY");
        chargeMap.put("description","我是中文");
	    //请根据渠道要求确定是否需要传递extra字段
        Map<String, Object> extra = new HashMap<String, Object>();
        extra.put("user_id","中文测试");
        extra.put("return_url","http://132.123.221.22/333/kad");
        chargeMap.put("extra",extra);
        
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
            System.out.println(JSON.toJSONString(charge));
        }else {//http请求失败
	        System.out.println(JSON.toJSONString(charge));
            String failureCode = charge.getFailureCode();
            String failureMsg = charge.getFailureMsg();
            System.out.println("failureCode:"+failureCode);
            System.out.println("failureMsg:"+failureMsg);
        }
    }

    /**
     * 查询充值订单
     */
    public void retrieve() {
        try {
            Charge charge = Charge.retrieve("ch_6f97ac4f57bf182cfe140f34");
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
