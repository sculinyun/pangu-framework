package cn.com.mw.app.framework.common.enums;

import cn.com.mw.app.framework.common.core.IntArrayValuable;
import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 全局用户类型枚举
 */
@AllArgsConstructor
@Getter
public enum UserTypeEnum implements IntArrayValuable {

    MEMBER(1, "app","会员"), // 面向 c 端，普通用户
    ADMIN(2, "web","管理员"), // 面向 b 端，管理后台
    DEVELOPER(3,"develop","开发者");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(UserTypeEnum::getValue).toArray();

    /**
     * 类型
     */
    private final Integer value;
    /**
     * 用户来源
     */
    private final String source;
    /**
     * 类型名
     */
    private final String name;

    public static UserTypeEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(userType -> userType.getValue().equals(value), UserTypeEnum.values());
    }

    public static UserTypeEnum valueOfBySource(String source) {
        return ArrayUtil.firstMatch(userType -> userType.getSource().equals(source), UserTypeEnum.values());
    }
    @Override
    public int[] array() {
        return ARRAYS;
    }
}
