package cn.com.hbscjt.app.framework.common.enums;

/**
 * 日期类型
 */
import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DateTypeEnum {
	
	DAY("day", "日"),
	WEEK("week", "周"),
	MONTH("month", "月"),
	HALFYEAR("halfyear", "半年"),
	YEAR("year", "年");
	
    private final String code;
    private final String desc;

    public static DateTypeEnum getDateTypeEnum(String value) {
        return ArrayUtil.firstMatch(dateTypeEnum -> dateTypeEnum.getCode().equals(value), DateTypeEnum.values());
    }

}
