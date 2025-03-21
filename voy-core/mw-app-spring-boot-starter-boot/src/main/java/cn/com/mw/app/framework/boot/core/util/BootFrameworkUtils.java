package cn.com.mw.app.framework.boot.core.util;

import cn.com.mw.app.framework.common.entity.LoginUser;
import cn.com.mw.app.framework.common.enums.UserTypeEnum;
import cn.com.mw.app.framework.security.core.util.SecurityFrameworkUtils;
import cn.com.mw.app.framework.web.core.util.WebFrameworkUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * boot工具类
 * 统一设置和获取Login_user
 */
public class BootFrameworkUtils {

    /**
     * 设置Login_user
     * @param loginUser
     */
    public static void setLoginUser(LoginUser loginUser) {
        HttpServletRequest request=WebFrameworkUtils.getRequest();
        String source=WebFrameworkUtils.getSource(request);
        UserTypeEnum userTypeEnum=UserTypeEnum.valueOfBySource(source);
        if (null!=userTypeEnum&&userTypeEnum.equals(UserTypeEnum.MEMBER)){
            WebFrameworkUtils.setLoginUser(loginUser);
            return;
        }
        SecurityFrameworkUtils.setLoginUser(loginUser,request);
        return;
    }

    /**
     * 获取Login_user
     * @return
     */
    public static LoginUser getLoginUser() {
        HttpServletRequest request=WebFrameworkUtils.getRequest();
        String source=WebFrameworkUtils.getSource(request);
        UserTypeEnum userTypeEnum=UserTypeEnum.valueOfBySource(source);
        if (null!=userTypeEnum&&userTypeEnum.equals(UserTypeEnum.MEMBER)){
            return WebFrameworkUtils.getLoginUser();

        }
        return SecurityFrameworkUtils.getLoginUser();
    }

}
