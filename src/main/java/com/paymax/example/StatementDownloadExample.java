package com.paymax.example;

import com.paymax.exception.AuthorizationException;
import com.paymax.exception.InvalidRequestException;
import com.paymax.exception.InvalidResponseException;
import com.paymax.model.StatementDownload;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 注：本DEMO或文档中所示的默认配置参数仅供参考，接入方须视各自系统及交易情况进行相应的调整。如因采用默认配置参数导致交易异常及造成相关损失的，我司不承担相关责任。
 * 对账单下载接口示例
 */
public class StatementDownloadExample {

    public static void main(String[] args) {
        StatementDownloadExample sd = new StatementDownloadExample();
        sd.download();
    }

    private void download() {
        Map<String, Object> statementMap = new HashMap<String, Object>();
        statementMap.put("appointDay", "20161027");
        statementMap.put("channelCategory", "ALIPAY");
        statementMap.put("statementType", "SUCCESS");
        try {
            String result = StatementDownload.download(statementMap);
            System.out.println(result);
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
