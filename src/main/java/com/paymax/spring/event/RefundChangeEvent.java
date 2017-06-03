package com.paymax.spring.event;

import com.paymax.model.Refund;
import lombok.Data;

/**
 * 系统会抛出表示状态的改变；应用程序应当为此校验金额之类的数据有效性
 *
 * @author CJ
 */
@Data
public class RefundChangeEvent {
    private Refund data;
}
