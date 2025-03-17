package cn.com.mw.app.framework.cloud.config;

import cn.com.mw.app.framework.cloud.core.aop.InnerAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : ly
 * @date : 2023-08-14 14:32
 **/
@Configuration(proxyBeanMethods = false)
public class CloudAutoConfiguration {

    @Bean
    public InnerAspect innerAspect() {
        return new InnerAspect();
    }
}
