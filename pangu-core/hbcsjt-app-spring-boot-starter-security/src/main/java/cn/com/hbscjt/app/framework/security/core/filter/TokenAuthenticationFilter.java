package cn.com.hbscjt.app.framework.security.core.filter;

import cn.com.hbscjt.app.framework.common.entity.LoginUser;
import cn.com.hbscjt.app.framework.common.exception.AuthException;
import cn.com.hbscjt.app.framework.common.exception.ServiceException;
import cn.com.hbscjt.app.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.com.hbscjt.app.framework.common.pojo.CommonResult;
import cn.com.hbscjt.app.framework.common.util.JsonUtils;
import cn.com.hbscjt.app.framework.common.util.ServletUtils;
import cn.com.hbscjt.app.framework.security.core.props.SecurityProperties;
import cn.com.hbscjt.app.framework.security.core.util.SecurityFrameworkUtils;
import cn.com.hbscjt.app.framework.web.core.handler.GlobalExceptionHandler;
import cn.com.hbscjt.app.framework.web.core.util.WebFrameworkUtils;
import cn.com.hbscjt.app.module.member.api.oauth2.OAuth2TokenApi;
import cn.com.hbscjt.app.module.member.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
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
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;

    private final GlobalExceptionHandler globalExceptionHandler;

    private final OAuth2TokenApi oauth2TokenApi;

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // 情况一，基于 header[login-user] 获得用户，例如说来自 Gateway 或者其它服务透传
        LoginUser loginUser = buildLoginUserByHeader(request);

        // 情况二，基于 Token 获得用户
        // 注意，这里主要满足直接使用 Nginx 直接转发到 Spring Cloud 服务的场景。
        if (loginUser == null) {
            String token = SecurityFrameworkUtils.obtainAuthorization(request, securityProperties.getTokenHeader());
            if (StrUtil.isNotEmpty(token)) {
                Integer userType = WebFrameworkUtils.getLoginUserType(request);
                try {
                    //基于 token 构建登录用户
                    loginUser = buildLoginUserByToken(token, userType);
                } catch (Throwable ex) {
                    CommonResult<?> result = globalExceptionHandler.allExceptionHandler(request, ex);
                    ServletUtils.writeJSON(response, result);
                    return;
                }
            }
        }

        // 设置当前用户
        if (loginUser != null) {
            SecurityFrameworkUtils.setLoginUser(loginUser, request);
        }
        // 继续过滤链
        chain.doFilter(request, response);
    }

    private LoginUser buildLoginUserByToken(String token, Integer userType) {
        try {
            // 校验访问令牌
            OAuth2AccessTokenCheckRespDTO accessToken = oauth2TokenApi.checkAccessToken(token).getCheckedData();
            if (accessToken == null) {
                return null;
            }
            // 用户类型不匹配，无权限
            if (ObjectUtil.notEqual(accessToken.getUserType(), userType)) {
                throw new AccessDeniedException("错误的用户类型");
            }
            // 构建登录用户
            LoginUser loginUser=new LoginUser();
            loginUser.setUserId(accessToken.getUserId());
            loginUser.setType(accessToken.getUserType());
            loginUser.setTenantId(accessToken.getTenantId());
            loginUser.setScopes(accessToken.getScopes());
            return loginUser;
        } catch (ServiceException serviceException) {
            // 校验 Token 不通过时，考虑到一些接口是无需登录的，所以直接返回 null 即可
            //新增feign调用----token超时或者无效处理逻辑
            if(serviceException.getCode().equals(GlobalErrorCodeConstants.UNAUTHORIZED.getCode())){
                throw new AuthException(serviceException.getMessage());
            }
            return null;
        }
    }

    private LoginUser buildLoginUserByHeader(HttpServletRequest request) {
        String loginUserStr = request.getHeader(SecurityFrameworkUtils.LOGIN_USER_HEADER);
        return StrUtil.isNotEmpty(loginUserStr) ? JsonUtils.parseObject(loginUserStr, LoginUser.class) : null;
    }

}
