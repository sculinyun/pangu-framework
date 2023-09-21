package cn.com.hbscjt.app.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 行政区划级别
 */
@AllArgsConstructor
@Getter
public enum AreaLevelEnum {
	
	PROVINCE(1, "省"),
    CITY(2, "市"),
    AREA(3, "区"),
	STREET(4, "街道"),
    COMMUNITY(5, "社区"),
	GRID(6, "网格");
	
    private final Integer level;
    private final String desc;
}
