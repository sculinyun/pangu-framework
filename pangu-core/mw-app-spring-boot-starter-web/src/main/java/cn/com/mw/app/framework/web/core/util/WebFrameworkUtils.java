package cn.com.mw.app.framework.web.core.util;

import cn.com.mw.app.framework.common.constant.SystemConstant;
import cn.com.mw.app.framework.common.entity.LoginUser;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * 专属于 web 包的工具类
 */
public class WebFrameworkUtils {

    private static final String REQUEST_ATTRIBUTE_LOGIN_USER = "login_user";

    /**
     * framework层获取token
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        String auth = request.getHeader(SystemConstant.AUTH_TOKEN);
        String token = TokenUtil.getToken(auth);
        return token;
    }

    public static String getHeaderToken() {
        HttpServletRequest request = getRequest();
        String auth = request.getHeader(SystemConstant.AUTH_TOKEN);
        String token = TokenUtil.getToken(auth);
        return token;
    }

    /**
     * framework层获取version
     * @param request
     * @return
     */
    public static String getVersion(HttpServletRequest request) {
        String version = request.getHeader(SystemConstant.VERSION);
        return version;
    }

    public static String getHeaderVersion() {
        HttpServletRequest request = getRequest();
        String version = request.getHeader(SystemConstant.VERSION);
        return version;
    }

    /**
     * framework层获取source
     * @param request
     * @return
     */
    public static String getSource(HttpServletRequest request) {
        String source = request.getHeader(SystemConstant.SOURCE);
        return source;
    }

    public static String getHeaderSource() {
        HttpServletRequest request = getRequest();
        String source = request.getHeader(SystemConstant.SOURCE);
        return source;
    }

    public static void setLoginUser(ServletRequest request, LoginUser loginUser) {
        request.setAttribute(REQUEST_ATTRIBUTE_LOGIN_USER, loginUser);
    }

    public static LoginUser getLoginUser() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        // 优先从 request 中获取
        Object loginUser = request.getAttribute(REQUEST_ATTRIBUTE_LOGIN_USER);
        if (loginUser == null) {
            LoginUser redisLoginUser = TokenUtil.getUser(request);
            if (redisLoginUser != null) {
                // 设置到 request 中
                request.setAttribute(REQUEST_ATTRIBUTE_LOGIN_USER, redisLoginUser);
                return redisLoginUser;
            }
        }
        return (LoginUser) loginUser;
    }

    public static void setLoginUser(LoginUser loginUser) {
        HttpServletRequest request = getRequest();
        request.setAttribute(REQUEST_ATTRIBUTE_LOGIN_USER, loginUser);
    }

    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return null;
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest();
    }

}
