package cn.com.hbscjt.app.framework.boot.core.util;

import cn.com.hbscjt.app.framework.common.entity.LoginUser;
import cn.com.hbscjt.app.framework.common.enums.UserTypeEnum;
import cn.com.hbscjt.app.framework.security.core.util.SecurityFrameworkUtils;
import cn.com.hbscjt.app.framework.web.core.util.WebFrameworkUtils;

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
        if (null==userTypeEnum){
            SecurityFrameworkUtils.setLoginUser(loginUser,request);
        }
        switch (userTypeEnum){
            case MEMBER:
                WebFrameworkUtils.setLoginUser(loginUser);
            default:
                SecurityFrameworkUtils.setLoginUser(loginUser,request);
        }
    }

    /**
     * 获取Login_user
     * @return
     */
    public static LoginUser getLoginUser() {
        HttpServletRequest request=WebFrameworkUtils.getRequest();
        String source=WebFrameworkUtils.getSource(request);
        UserTypeEnum userTypeEnum=UserTypeEnum.valueOfBySource(source);
        switch (userTypeEnum){
            case MEMBER:
                return WebFrameworkUtils.getLoginUser();
            default:
                return SecurityFrameworkUtils.getLoginUser();
        }
    }

}
