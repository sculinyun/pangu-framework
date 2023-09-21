package cn.com.hbscjt.app.framework.security.config;

import cn.com.hbscjt.app.framework.security.core.aop.PreAuthAspect;
import cn.com.hbscjt.app.framework.security.core.context.TransmittableThreadLocalSecurityContextHolderStrategy;
import cn.com.hbscjt.app.framework.security.core.filter.TokenAuthenticationFilter;
import cn.com.hbscjt.app.framework.security.core.handler.AccessDeniedHandlerImpl;
import cn.com.hbscjt.app.framework.security.core.handler.AuthenticationEntryPointImpl;
import cn.com.hbscjt.app.framework.security.core.props.SecurityProperties;
import cn.com.hbscjt.app.framework.security.core.service.SecurityFrameworkService;
import cn.com.hbscjt.app.framework.security.core.service.SecurityFrameworkServiceImpl;
import cn.com.hbscjt.app.framework.web.core.handler.GlobalExceptionHandler;
import cn.com.hbscjt.app.module.member.api.oauth2.OAuth2TokenApi;
import cn.com.hbscjt.app.module.member.api.permission.PermissionApi;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.annotation.Resource;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SecurityProperties.class)
@ConditionalOnProperty(name = "boot.auth.webEnabled",havingValue = "true")
public class SecurityAutoConfiguration {

    @Resource
    private SecurityProperties securityProperties;
    @Resource
    private PermissionApi permissionApi;
    @Resource
    private OAuth2TokenApi oAuth2TokenApi;

    /**
     * 处理用户未登录拦截的切面的 Bean
     */
    @Bean
    public PreAuthAspect preAuthenticatedAspect(ApplicationContext applicationContext,SecurityFrameworkService securityFrameworkService) {
        return new PreAuthAspect(applicationContext,securityFrameworkService);
    }

    /**
     * 认证失败处理类 Bean
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPointImpl();
    }

    /**
     * 权限校验失败处理器 Bean
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }

    /**
     * Token 认证过滤器 Bean
     */
    @Bean
    @ConditionalOnProperty
    public TokenAuthenticationFilter authenticationTokenFilter(GlobalExceptionHandler globalExceptionHandler) {
        return new TokenAuthenticationFilter(securityProperties, globalExceptionHandler, oAuth2TokenApi);
    }

    @Bean("ss")
    public SecurityFrameworkService securityFrameworkService() {
        return new SecurityFrameworkServiceImpl(permissionApi);
    }

    /**
     * 声明调用 {@link SecurityContextHolder#setStrategyName(String)} 方法，
     * 设置使用 {@link TransmittableThreadLocalSecurityContextHolderStrategy} 作为 Security 的上下文策略
     */
    @Bean
    public MethodInvokingFactoryBean securityContextHolderMethodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
        methodInvokingFactoryBean.setTargetMethod("setStrategyName");
        methodInvokingFactoryBean.setArguments(TransmittableThreadLocalSecurityContextHolderStrategy.class.getName());
        return methodInvokingFactoryBean;
    }

}
