package com.paymax.model;

import lombok.Data;

/**
 * 拉卡拉app信息
 *
 * @author CJ
 */
@Data
public class AppInfo {
    /**
     * 商户私钥
     */
    private String privateKey;
    /**
     * Paymax提供给商户的SecretKey，登录网站后查看
     */
    private String secretKey;
    /**
     * 平台公钥
     */
    private String publicKey;
    /**
     * 应用的appKey
     */
    private String app;
}
