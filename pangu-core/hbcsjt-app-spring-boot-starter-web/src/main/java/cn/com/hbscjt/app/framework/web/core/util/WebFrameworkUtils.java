package cn.com.hbscjt.app.framework.web.core.util;

import cn.com.hbscjt.app.framework.common.entity.LoginUser;
import cn.com.hbscjt.app.framework.common.enums.UserTypeEnum;
import cn.com.hbscjt.app.framework.web.core.props.WebProperties;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * 专属于 web 包的工具类
 */
public class WebFrameworkUtils {

    public static final String HEADER_TENANT_ID = "tenant-id";
    private static final String REQUEST_ATTRIBUTE_LOGIN_USER_ID = "login_user_id";
    private static final String REQUEST_ATTRIBUTE_LOGIN_USER_TYPE = "login_user_type";
    private static final String REQUEST_ATTRIBUTE_LOGIN_USER = "login_user";
    private static TokenUtil tokenUtil;
    private static WebProperties properties;

    public WebFrameworkUtils(WebProperties webProperties, TokenUtil tokenUtil) {
        properties = webProperties;
        WebFrameworkUtils.tokenUtil = tokenUtil;
    }

    /**
     * 获得租户编号，从 header 中
     * 考虑到其它 framework 组件也会使用到租户编号，所以不得不放在 WebFrameworkUtils 统一提供
     *
     * @param request 请求
     * @return 租户编号
     */
    public static Long getTenantId(HttpServletRequest request) {
        String tenantId = request.getHeader(HEADER_TENANT_ID);
        return StrUtil.isNotEmpty(tenantId) ? Long.valueOf(tenantId) : null;
    }

    public static void setLoginUserId(ServletRequest request, Long userId) {
        request.setAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_ID, userId);
    }

    public static void setLoginUser(ServletRequest request, LoginUser loginUser) {
        request.setAttribute(REQUEST_ATTRIBUTE_LOGIN_USER, loginUser);
    }

    /**
     * 设置用户类型
     *
     * @param request  请求
     * @param userType 用户类型
     */
    public static void setLoginUserType(ServletRequest request, Integer userType) {
        request.setAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_TYPE, userType);
    }

    /**
     * 获得当前用户的编号，从请求中
     * 注意：该方法仅限于 framework 框架使用！！！
     *
     * @param request 请求
     * @return 用户编号
     */
    public static Long getLoginUserId(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return (Long) request.getAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_ID);
    }

    /**
     * 获得当前用户的类型
     * 注意：该方法仅限于 web 相关的 framework 组件使用！！！
     *
     * @param request 请求
     * @return 用户编号
     */
    public static Integer getLoginUserType(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Integer userType = (Integer) request.getAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_TYPE);
        if (userType != null) {
            return userType;
        }
        // 2. 其次，基于 URL 前缀的约定
        if (request.getRequestURI().startsWith(properties.getAdminApi().getPrefix())) {
            return UserTypeEnum.ADMIN.getValue();
        }
        if (request.getRequestURI().startsWith(properties.getAppApi().getPrefix())) {
            return UserTypeEnum.MEMBER.getValue();
        }
        if (request.getRequestURI().startsWith(properties.getDeveloperApi().getPrefix())) {
            return UserTypeEnum.MEMBER.getValue();
        }
        return null;
    }

    public static Integer getLoginUserType() {
        HttpServletRequest request = getRequest();
        return getLoginUserType(request);
    }

    public static Long getLoginUserId() {
        HttpServletRequest request = getRequest();
        return getLoginUserId(request);
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
