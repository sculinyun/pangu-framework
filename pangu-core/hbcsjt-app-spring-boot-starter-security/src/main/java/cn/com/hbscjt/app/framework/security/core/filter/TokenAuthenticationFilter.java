package cn.com.hbscjt.app.framework.security.core.filter;

import cn.com.hbscjt.app.framework.common.entity.LoginUser;
import cn.com.hbscjt.app.framework.common.util.JsonUtils;
import cn.com.hbscjt.app.framework.security.core.util.SecurityFrameworkUtils;
import cn.com.hbscjt.app.framework.web.core.util.WebFrameworkUtils;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token 过滤器，验证 token 的有效性
 * 验证通过后，获得 {@link LoginUser} 信息，并加入到 Spring Security 上下文
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // 情况一，基于 header[login-user] 获得用户，例如说来自 Gateway 或者其它服务透传
        LoginUser loginUser = buildLoginUserByHeader(request);

        // 情况二，基于 Token 获得用户
        // 注意，这里主要满足直接使用 Nginx 直接转发到 Spring Cloud 服务的场景。
        if (loginUser == null) {
            LoginUser webLoginUser = WebFrameworkUtils.getLoginUser();
            // 设置当前用户
            if (webLoginUser != null) {
                SecurityFrameworkUtils.setLoginUser(webLoginUser, request);
            }
        }
        // 继续过滤链
        chain.doFilter(request, response);
    }

    private LoginUser buildLoginUserByHeader(HttpServletRequest request) {
        String loginUserStr = request.getHeader(SecurityFrameworkUtils.LOGIN_USER_HEADER);
        return StrUtil.isNotEmpty(loginUserStr) ? JsonUtils.parseObject(loginUserStr, LoginUser.class) : null;
    }

}
