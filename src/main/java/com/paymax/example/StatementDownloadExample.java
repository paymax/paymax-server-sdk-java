package com.paymax.example;

import com.alibaba.fastjson.JSON;
import com.paymax.exception.AuthorizationException;
import com.paymax.exception.InvalidRequestException;
import com.paymax.exception.InvalidResponseException;
import com.paymax.model.StatementDownload;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author shane
 * @Time 2016/10/20 21:47
 * @Email baohua.shan@zhulebei.com
 * @Desc ...
 */
public class StatementDownloadExample {

    public static void main(String[] args) {
        StatementDownloadExample sd = new StatementDownloadExample();
        sd.download();
    }

    private void download() {
        Map<String, Object> statementMap = new HashMap<String, Object>();
        statementMap.put("appointDay", "20161020");
        statementMap.put("channelCategory", "WECHAT");
        statementMap.put("statementType", "ALL");
        try {
            String result = StatementDownload.download(statementMap);
            System.out.println(JSON.toJSONString(result));
        } catch (AuthorizationException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
