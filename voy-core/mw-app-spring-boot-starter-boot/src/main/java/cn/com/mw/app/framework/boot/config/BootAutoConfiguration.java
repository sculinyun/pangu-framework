package cn.com.mw.app.framework.boot.config;

import cn.com.mw.app.framework.boot.core.props.BootCoreProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 配置类
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableScheduling
@EnableConfigurationProperties(BootCoreProperties.class)
public class BootAutoConfiguration {


}
