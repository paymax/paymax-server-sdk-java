package com.paymax.model;

import com.alibaba.fastjson.JSONObject;
import com.paymax.config.PaymaxConfig;
import com.paymax.exception.AuthorizationException;
import com.paymax.exception.InvalidRequestException;
import com.paymax.exception.InvalidResponseException;

import java.io.IOException;
import java.util.Map;

/**
 * @Author shane
 * @Time 2016/10/20 21:39
 * @Email baohua.shan@zhulebei.com
 * @Desc ...
 */
public class StatementDownload extends Paymax {

    public static String download(Map<String,Object> params) throws AuthorizationException, InvalidResponseException, InvalidRequestException, IOException {
        return request(PaymaxConfig.API_BASE_URL+ PaymaxConfig.STATEMENT_DOWNLOAD, JSONObject.toJSONString(params), String.class);
    }

}
