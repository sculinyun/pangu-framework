package cn.com.mw.app.framework.common.enums;

import cn.hutool.core.date.Week;
import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WeekEnum {
    MONDAY(1, "星期一", Week.MONDAY),
    TUESDAY(2, "星期二",Week.TUESDAY),
    WEDNESDAY(3, "星期三",Week.WEDNESDAY),
    THURSDAY(4, "星期四",Week.THURSDAY),
    FRIDAY(5, "星期五",Week.FRIDAY),
    SATURDAY(6, "星期六",Week.SATURDAY),
    SUNDAY(7,"星期日",Week.SUNDAY);

    private final Integer code;
    private final String label;
    private final Week week;

    public static WeekEnum getWeekEnum(Integer code) {
        return ArrayUtil.firstMatch(weekEnum -> weekEnum.getCode().equals(code), WeekEnum.values());
    }

}
