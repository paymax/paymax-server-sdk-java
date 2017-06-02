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
    //商户自己的私钥【用com.Paymax.sign.RSAKeyGenerateUtil生成RSA秘钥对，公钥通过Paymax网站上传到Paymax，私钥设置到下面的变量中】
    public static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAONMS7MhjNAEUd+JioKWQL375tYsL3LlrwHlXWmQe4BR+5LTmvpHxXYEsinNkTr5dlfm65QYPbDkf6/80e0HEhkG1ir0RnW/cPJy6f34NzLUgzqimYRmrcOFr1Wxyr1x0byyO40DHr/MXk7ea/DgE+ste3gTQB/B4j28Kfv5REclAgMBAAECgYBOJlxcsatdli6kQgEKlyiZabPbbYO+6HO8niT498FOxGFQAUtmxCiDRGgRcWl+smjbHj1fRNppKJcyZiWzblvs8s+4UmQd8KvNZtMmyZn8aVZfGHvpEoB6dbFaWxxj61/rhbBwRRISIzypiUgBp71JuCkFaGnV9YLfQmvKv52fXQJBAP4bViLjy7OUXh7dWQhe6tPDjB7nIA6YbypkKFm/yEZue8Ek90MvwFCXRdxBbuxXFViHsrrT01A7DUOWJL/1eocCQQDk/dOIaHF7VBYNw2Rol+XOHV80QoYsPAmKrtj+ZSc6rnz3irIuSqVOjRiYt6XA/PmUhrtXuizA/VrJrxUuyH/zAkEA6IKc83nax2wIH1fMgsNPPgudKB22EITcmz5gSZcZq5CmvlmTwq9r2pJAg0SAOdOJHaO1IAx5O918yo4U/Gyi+wJAZZnRf1aH82ZtmpG1PUsYJYmWskNJ8Np6iVPm54jODRVaUSLyx+NK0T19SlVBcA1OV34oJVZvgPlojM/oICfJzQJBANMbFW0/HtHQ5sZFncS/9/DFUy0f0Q4EYYD5oo7hx5vGNKMdOTvgFRppYw6z0RsKiHDoUnORxK4JIl+EhSMkbOs=";
    //Paymax提供给商户的SecretKey，登录网站后查看
    public static final String SECRET_KEY = "55970fdbbf10459f966a8e276afa86fa";

    //Paymax提供给商户的公钥，登录网站后查看
    public static final String PAYMAX_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDWhoZh6qWMbwk0uYdHQfCLrGNDUenxBwbqYtK3PhxTcIPh1eY5eNdK8pXPVtBqwg+1bHiXhc0oo2KuIQDj2CK+Ldq7TIa9gEFxvsotqjBaUCdwjjSlLtIT8cQjWGmxIxCE9X3EG5XzpodigkahHZG3a61XLipBAVvyWYrjlLV2iwIDAQAB";
}
