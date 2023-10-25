package cn.com.hbscjt.app.framework.oss.config;

import cn.com.hbscjt.app.framework.oss.core.props.OssProperties;
import cn.com.hbscjt.app.framework.oss.core.utils.OssTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * OSS自动配置类
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "boot.oss.enabled", havingValue = "true")
@EnableConfigurationProperties(OssProperties.class)

public class OssConfiguration {

    private final OssProperties properties;

    @Bean
    public OssTemplate ossTemplate() {
        return new OssTemplate(properties);
    }
}
