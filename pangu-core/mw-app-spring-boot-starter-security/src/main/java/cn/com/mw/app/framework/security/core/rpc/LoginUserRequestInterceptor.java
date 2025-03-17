package cn.com.mw.app.framework.security.core.rpc;

import cn.com.mw.app.framework.common.entity.LoginUser;
import cn.com.mw.app.framework.security.core.util.SecurityFrameworkUtils;
import cn.com.mw.app.framework.web.core.util.FeignUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * LoginUser 的 RequestInterceptor 实现类：Feign 请求时，将 {@link LoginUser} 设置到 header 中，继续透传给被调用的服务
 */
public class LoginUserRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        LoginUser user = SecurityFrameworkUtils.getLoginUser();
        if (user != null) {
            FeignUtils.createJsonHeader(requestTemplate, SecurityFrameworkUtils.LOGIN_USER_HEADER, user);
        }
    }

}
