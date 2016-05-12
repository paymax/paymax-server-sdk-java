package com.payright.config;

/**
 * Created by xiaowei.wang on 2016/4/26.
 */
public class PayrightConfig {
    //Payright服务器地址
    public static final String API_BASE_URL = "http://172.30.21.20:9001/";
//    public static final String API_BASE_URL = "http://127.0.0.1:8080/";
    //请求method
    public static final String CREATE_CHARGE = "v1/charges";
    //编码集
    public static final String CHARSET = "UTF-8";
    //签名后数据的key
    public static String SIGN = "sign";
    //返回数据的key
    public static String RESDATA = "res_data";
}
