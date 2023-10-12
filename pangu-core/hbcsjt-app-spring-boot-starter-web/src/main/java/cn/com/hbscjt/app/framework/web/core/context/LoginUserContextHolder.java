package cn.com.hbscjt.app.framework.web.core.context;

import cn.com.hbscjt.app.framework.common.entity.LoginUser;
import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 用户上下文 Holder
 */
public class LoginUserContextHolder {
    /**
     * 当前用户
     */
    private static final ThreadLocal<LoginUser> LOGIN_USER = new TransmittableThreadLocal<>();

    /**
     * 获得当前用户
     */
    public static LoginUser getLoginUser() {
        return LOGIN_USER.get();
    }

    public static void setTenantId(LoginUser user) {
        LOGIN_USER.set(user);
    }

    public static void clear() {
        LOGIN_USER.remove();
    }

}
