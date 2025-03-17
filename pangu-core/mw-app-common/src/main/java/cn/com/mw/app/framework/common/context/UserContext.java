package cn.com.mw.app.framework.common.context;

import cn.com.mw.app.framework.common.entity.LoginUser;
import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 用户上下文
 */
public class UserContext {

    private static final ThreadLocal<LoginUser> userHolder = new TransmittableThreadLocal<>();

    public static LoginUser getUser() {
        return userHolder.get();
    }

    public static void setUser(LoginUser loginUser) {
        userHolder.set(loginUser);
    }

    public void clear() {
        userHolder.remove();
    }
}
