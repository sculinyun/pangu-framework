package cn.com.hbscjt.app.framework.common.enums;

/**
 * 用户入口来源
 */
import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SourceEnum {
	
	APP(1, "app"),
	WEB(2, "web"),
	H5(3, "h5");

    private final Integer code;
    private final String desc;

    public static SourceEnum getSourceEnum(String value) {
        return ArrayUtil.firstMatch(sourceEnum -> sourceEnum.getCode().equals(value), SourceEnum.values());
    }

}
