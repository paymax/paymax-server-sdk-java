package com.payright.example;

import com.payright.exception.AuthorizationException;
import com.payright.exception.InvalidRequestException;
import com.payright.model.Refund;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by xiaowei.wang on 2016/4/27.
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
        refundMap.put("amount", 1);
        refundMap.put("description", "description");

        try {
            System.out.println( Refund.create("ch_4671d8bbac347cdd33669b2a",refundMap));
        } catch (AuthorizationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        }

    }

    /**
     * 退款查询订单
     */
    public void retrieve() {
        try {
            System.out.println(Refund.retrieve("ch_b6e8345164384b0f2cd1a10b","154651323135"));
        } catch (AuthorizationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        }

    }

}
