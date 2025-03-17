package cn.com.mw.app.framework.sms.core.enums;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信渠道枚举
 */
@Getter
@AllArgsConstructor
public enum SmsChannelEnum {
    YUN_PIAN(1,"YUN_PIAN", "云片"),
    ALIYUN(2,"ALIYUN", "阿里云"),
    TENCENT(3,"TENCENT", "腾讯云"),
    HUA_WEI(4,"HUA_WEI", "华为云"),
    ;


    private final Integer id;
    /**
     * 编码
     */
    private final String code;
    /**
     * 名字
     */
    private final String name;

    public static SmsChannelEnum getByCode(String code) {
        return ArrayUtil.firstMatch(o -> o.getCode().equals(code), values());
    }

    public static SmsChannelEnum getById(Integer id) {
        return ArrayUtil.firstMatch(o -> o.getId().equals(id), values());
    }
}
