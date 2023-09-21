package cn.com.hbscjt.app.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 路段类型
 */
@AllArgsConstructor
@Getter
public enum RoadTypeEnum {
	
	MAIN(1, "主路"),
	BRANCH(2, "支路");
	
    private final Integer type;
    private final String desc;
}
