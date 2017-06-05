package com.paymax.spring.event;

import com.paymax.model.Charge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统会抛出表示状态的改变；应用程序应当为此校验金额之类的数据有效性
 * @author CJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargeChangeEvent {
    private Charge data;
}
