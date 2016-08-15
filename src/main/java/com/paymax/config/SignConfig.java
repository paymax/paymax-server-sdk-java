package com.paymax.config;

/**
 * Created by xiaowei.wang on 2016/4/26.
 */
public class SignConfig {
    //商户自己的私钥【用com.Paymax.sign.RSAKeyGenerateUtil生成RSA秘钥对，公钥通过Paymax网站上传到Paymax，私钥设置到下面的变量中】
    public static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALh+04pzLGmCLIpTjUNGAIAeYkcndciAb1mClbaXdQHDeVQHwai0+uAwQHlLNEmvZtmdNDrBrJ1I2Fu09BjP+b4tiltvjpOTuB+8G25fS+BDCAqinTdVHbTEW+lCH72VP1b1Zv60R1ZYozDtOyAQmxHw2YlPXxEBsAKNVrLoMKcNAgMBAAECgYBlcjdXdaUCDvX2yaJvT1qPGCXqAiSdryGLEmbIE9fetGFOd0VhQsJ/64hIKbYCnlPrbKl/dWc4xQlSw9lEm3cy0Eu8B0/NlpQcHmvUtDiAMFyOoELR3x+VdAacBpdwTpZPVUiqpfAuR7O97KMj+34DorwRwfKbSyNIJnoE2EppAQJBANvAm9Mod0f8IlZp1DbBpINWrMkdP3QK0W2PthfMPZsVNi4rV9uk2E2NGYJLbEvharOFQr6HC01/WMTdyO2XDPECQQDW7W/MDcI6gI2BXw9ROSGSTqDKvd8kuNVjufwdbdlx5CAvNQ9I+LvDhTcKxNiwypht83y2YPqNaY6iH6n/fivdAkBSRZIRRB0kPik84PIqbeUBpvmZcfHHqCUwN5Wc40JNRWu7bU6/VMAGiMT8GvC4l/QysbmDS1vX7810JvSKvvqBAkEAwpCvZEoQSmWVtEgZ1a2idpA3f1Hjb5rjkiQL15haAIBDonimHakOUTGHYnhQsbq1wtNpUrD4IIwuUxXXDzNpAQJBAJzBK99thKns++zD5EYMYRlsPpNlyJj6QVShcoBPDMC6mvzvh5o4opn5J3uPdmbfrljpOYQW9oUqMQlq8+5fozo=";
    //Paymax提供给商户的SecretKey，登录网站后查看
    public static final String SECRET_KEY = "fc03d8dd040a41d49ef3d79ad28ea357";

    //Paymax提供给商户的公钥，登录网站后查看
    public static final String PAYMAX_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5XI6Ytfx34hDGhZhaiX6e0cM1VAJpVs+RPnRYI6hKcZJcR7rYTZwCXLsGRnw3iv+p/9zJevO5txjn7A+oLBaW3jdkqhOd4wVKU/mBHi76YX9OQmCXexvzqVVRrq0/vi112OkGxVy6cLjwNUhkKiArlAjJPo0zdY03JrjJpIOycwIDAQAB";
}
