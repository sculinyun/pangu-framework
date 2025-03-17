package cn.com.mw.app.framework.check.config;

import cn.com.mw.app.framework.check.core.CheckUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;

/**
 * 配置类
 */
@Configuration
@RequiredArgsConstructor
public class CheckAutoConfiguration {
    private final AbstractEnvironment environment;

    @Bean
    public CheckUtil check() {
       return new CheckUtil(environment);
    }
}
