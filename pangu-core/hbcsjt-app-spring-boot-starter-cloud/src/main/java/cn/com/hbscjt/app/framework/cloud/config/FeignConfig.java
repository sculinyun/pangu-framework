package cn.com.hbscjt.app.framework.cloud.config;

import cn.com.hbscjt.app.framework.cloud.core.rpc.HeaderInterceptor;
import feign.Logger;
import feign.slf4j.Slf4jLogger;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : ly
 **/

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(CloudAutoConfiguration.class)
public class FeignConfig{

    @Bean
    Logger.Level feignLevel(){
        return Logger.Level.FULL;
    }

    @Bean
    public feign.Logger logger() {
        return new Slf4jLogger();
    }

    @Bean
    public HeaderInterceptor headerInterceptor() {
        return new HeaderInterceptor();
    }
}
