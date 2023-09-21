package cn.com.hbscjt.app.framework.auth.config;

import cn.com.hbscjt.app.framework.auth.core.aspect.LoginAspect;
import cn.com.hbscjt.app.framework.auth.core.props.AuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auth配置
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthProperties.class)
public class AuthConfiguration {

    @Bean
    @ConditionalOnProperty(name = "boot.auth.appEnabled",havingValue = "true")
    public LoginAspect loginAspect() {
       return new LoginAspect();
    }

}
