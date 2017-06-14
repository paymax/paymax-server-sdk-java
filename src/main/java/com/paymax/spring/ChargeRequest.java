package com.paymax.spring;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * <a href="https://github.com/paymax/paymax-doc/blob/master/API%E6%96%87%E6%A1%A3.md#%E5%8F%91%E8%B5%B7%E6%94%AF%E4%BB%98%E4%B8%8B%E5%8D%95">支付下单请求</a>
 *
 * @author CJ
 */
@Data
public class ChargeRequest {

    //
    /**
     * order_no
     * 商户订单号，在商户系统内唯一，8-20位数字或字母，不允许特殊字符
     */
    private String orderNumber = UUID.randomUUID().toString().replace("-", "");
    /**
     * 订单总金额，大于0的数字，单位是该币种的货币单位
     */
    private BigDecimal amount;
    /**
     * 购买商品的标题，最长32位
     */
    private String subject;
    /**
     * 购买商品的描述信息，最长128个字符
     */
    private String body;
    /**
     * client_ip
     * 发起支付的客户端IP
     */
    private String clientIpAddress;
    /**
     * 可选的订单备注，限制300个字符内
     */
    private String description;
    // time_expire	Long	false	订单失效时间，13位Unix时间戳，默认1小时，微信公众号支付对其的限制为3分钟
    // currency	String	false	三位ISO货币代码，只支持人民币cny，默认cny
    /**
     * 可选的用户自定义元数据
     */
    private Map<String, ?> metadata;
}
