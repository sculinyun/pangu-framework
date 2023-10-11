package cn.com.hbscjt.app.framework.web.config;

import cn.com.hbscjt.app.framework.common.enums.WebFilterOrderEnum;
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
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

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


    private static <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
        FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(order);
        return bean;
    }

    /**
     * 创建 CorsFilter Bean，解决跨域问题
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterBean() {
        // 创建 CorsConfiguration 对象
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*"); // 设置访问源地址
        config.addAllowedHeader("*"); // 设置访问源请求头
        config.addAllowedMethod("*"); // 设置访问源请求方法
        // 创建 UrlBasedCorsConfigurationSource 对象
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 对接口配置跨域设置
        return createFilterBean(new CorsFilter(source), WebFilterOrderEnum.CORS_FILTER);
    }
}
