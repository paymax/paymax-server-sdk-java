package com.paymax.example;

import com.paymax.exception.AuthorizationException;
import com.paymax.exception.InvalidRequestException;
import com.paymax.exception.InvalidResponseException;
import com.paymax.model.Refund;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * 注：本DEMO或文档中所示的默认配置参数仅供参考，接入方须视各自系统及交易情况进行相应的调整。如因采用默认配置参数导致交易异常及造成相关损失的，我司不承担相关责任。
 */
public class RefundExample {

    public static void main(String[] args) {
        RefundExample ce = new RefundExample();
        ce.charge();
        ce.retrieve();
    }

    /**
     * 创建退款订单
     */
    public void charge() {
        Map<String, Object> refundMap = new HashMap<String, Object>();
        refundMap.put("amount", "0.01");
        refundMap.put("description", "description");
        Map<String, Object> extra = new HashMap<String, Object>();
        extra.put("extra_key2","extra_value2");
        extra.put("extra_key1","extra_value1");
        refundMap.put("extra",extra);
        try {
            Refund refund = Refund.create("ch_5f9464cac493723545d1a018",refundMap);
            printResult(refund);
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
     * @param refund
     */
    private void printResult(Refund refund) {
        if (refund.getReqSuccessFlag()){//http请求成功
            System.out.println(refund);
        }else {//http请求失败
            String failureCode = refund.getFailureCode();
            String failureMsg = refund.getFailureMsg();
            System.out.println("failureCode:"+failureCode);
            System.out.println("failureMsg:"+failureMsg);
        }
    }

    /**
     * 退款查询订单
     */
    public void retrieve() {
        try {
            Refund refund = Refund.retrieve("ch_c090e7d9a9d9d94a6e9b0bce","re_d6586ff6e077b95985344538");
            printResult(refund);
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
