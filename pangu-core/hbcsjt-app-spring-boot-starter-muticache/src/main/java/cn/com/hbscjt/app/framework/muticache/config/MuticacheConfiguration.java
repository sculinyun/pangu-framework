package cn.com.hbscjt.app.framework.muticache.config;

import cn.com.hbscjt.app.framework.muticache.core.MuticacheProperties;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * JetCache配置
 */
@Configuration
@EnableMethodCache(basePackages = { "cn.com.hbscjt.app" })
@ConditionalOnProperty(name = "boot.web.muticache.enabled", havingValue = "true")
@EnableConfigurationProperties(MuticacheProperties.class)
public class MuticacheConfiguration {

}
