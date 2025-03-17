package cn.com.mw.app.framework.security.core.filter;

import cn.com.mw.app.framework.common.constant.SystemConstant;
import cn.com.mw.app.framework.common.entity.LoginUser;
import cn.com.mw.app.framework.common.enums.UserTypeEnum;
import cn.com.mw.app.framework.common.pojo.CommonResult;
import cn.com.mw.app.framework.common.util.JsonUtils;
import cn.com.mw.app.framework.common.util.ServletUtils;
import cn.com.mw.app.framework.common.util.collection.CollectionUtils;
import cn.com.mw.app.framework.security.core.props.SecurityProperties;
import cn.com.mw.app.framework.security.core.util.SecurityFrameworkUtils;
import cn.com.mw.app.framework.web.core.util.WebFrameworkUtils;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.PathContainer;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static cn.com.mw.app.framework.common.constant.SystemConstant.SOURCE;
import static cn.com.mw.app.framework.common.exception.enums.GlobalErrorCodeConstants.UNAUTHORIZED;

/**
 * Token 过滤器，验证 token 的有效性
 * 验证通过后，获得 {@link LoginUser} 信息，并加入到 Spring Security 上下文
 */
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;

    private static final List<PathPattern> ingorePathList=new CopyOnWriteArrayList<>();

    @PostConstruct
    public void init(){
        PathPattern pattern01 = PathPatternParser.defaultInstance.parse("/swagger-resources/**");
        PathPattern pattern02 = PathPatternParser.defaultInstance.parse("/webjars/**");
        PathPattern pattern03 = PathPatternParser.defaultInstance.parse("/actuator/**");
        PathPattern pattern04 = PathPatternParser.defaultInstance.parse("/druid/**");
        PathPattern pattern05 = PathPatternParser.defaultInstance.parse("/*/api-docs");
        PathPattern pattern06 = PathPatternParser.defaultInstance.parse("/swagger-ui.html");
        PathPattern pattern07 = PathPatternParser.defaultInstance.parse("/doc.html");
        PathPattern pattern08 = PathPatternParser.defaultInstance.parse("/actuator");
        ingorePathList.add(pattern01);
        ingorePathList.add(pattern02);
        ingorePathList.add(pattern03);
        ingorePathList.add(pattern04);
        ingorePathList.add(pattern05);
        ingorePathList.add(pattern06);
        ingorePathList.add(pattern07);
        ingorePathList.add(pattern08);
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String uri=request.getRequestURI();
        securityProperties.getPermitAllUrls().forEach(permitUrl->{
            PathPattern pattern = PathPatternParser.defaultInstance.parse(permitUrl);
            ingorePathList.add(pattern);
        });
        PathPattern first=CollectionUtils.findFirst(ingorePathList, pahtPattern -> pahtPattern.matches(PathContainer.parsePath(uri)));
        if(ObjectUtil.isNotEmpty(first)){
            chain.doFilter(request, response);
            return;
        }
        //内部调用不需要鉴权,也避免各种平台的token校验。
        if(ignoreInner(request)){
            chain.doFilter(request, response);
            return;
        }
        // 情况一，基于 header[login-user] 获得用户，例如说来自 Gateway 或者其它服务透传
        LoginUser loginUser = buildLoginUserByHeader(request);
        // 情况二，基于 Token 获得用户
        // 注意，这里主要满足直接使用 Nginx 直接转发到 Spring Cloud 服务的场景。
        if (loginUser == null) {
            loginUser = WebFrameworkUtils.getLoginUser();
            if (loginUser != null) {
                //校验用户类型
                String source=request.getHeader(SOURCE);
                UserTypeEnum requestType=UserTypeEnum.valueOfBySource(source);
                if(null!=requestType&&loginUser.getType()!= requestType.getValue()){
                    throw new AccessDeniedException("错误的用户类型");
                }
            }else {
                CommonResult<?> result = CommonResult.error(UNAUTHORIZED);
                ServletUtils.writeJSON(response, result);
                return;
            }
        }
        // 设置当前用户
        if (loginUser != null) {
            SecurityFrameworkUtils.setLoginUser(loginUser, request);
        }
        // 继续过滤链
        chain.doFilter(request, response);
    }

    private LoginUser buildLoginUserByHeader(HttpServletRequest request) {
        String loginUserStr = request.getHeader(SecurityFrameworkUtils.LOGIN_USER_HEADER);
        return StrUtil.isNotEmpty(loginUserStr) ? JsonUtils.parseObject(loginUserStr, LoginUser.class) : null;
    }



    private boolean ignoreInner(HttpServletRequest request){
        String header = request.getHeader(SystemConstant.FROM);
        if(StringUtils.isNotEmpty(header)&&(SystemConstant.FROM.equals(header))){
            return true;
        }else{
            return false;
        }
    }
}
