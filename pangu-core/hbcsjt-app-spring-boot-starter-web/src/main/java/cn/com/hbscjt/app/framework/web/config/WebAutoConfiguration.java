package cn.com.hbscjt.app.framework.web.config;

import cn.com.hbscjt.app.framework.redis.core.RedisService;
import cn.com.hbscjt.app.framework.web.core.convert.MvcDateConvert;
import cn.com.hbscjt.app.framework.web.core.handler.GlobalExceptionHandler;
import cn.com.hbscjt.app.framework.web.core.props.RsaProperties;
import cn.com.hbscjt.app.framework.web.core.props.WebProperties;
import cn.com.hbscjt.app.framework.web.core.props.XssProperties;
import cn.com.hbscjt.app.framework.web.core.util.TokenUtil;
import cn.com.hbscjt.app.framework.web.core.util.WebFrameworkUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : ly
 * @date : 2023-08-15 11:11
 **/
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties({RsaProperties.class, WebProperties.class, XssProperties.class})
public class WebAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    @ConditionalOnBean(RedisService.class)
    public TokenUtil tokenUtil(RedisService redisService){
        return new TokenUtil(redisService);
    }

    @Bean
    public WebFrameworkUtils webFrameworkUtils(WebProperties properties,TokenUtil tokenUtil){
        return new WebFrameworkUtils(properties,tokenUtil);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new MvcDateConvert());
    }
}
