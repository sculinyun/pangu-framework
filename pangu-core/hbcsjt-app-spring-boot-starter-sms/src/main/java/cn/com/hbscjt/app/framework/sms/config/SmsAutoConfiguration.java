package cn.com.hbscjt.app.framework.sms.config;

import cn.com.hbscjt.app.framework.sms.core.factory.SmsClientFactory;
import cn.com.hbscjt.app.framework.sms.core.factoryimpl.SmsClientFactoryImpl;
import cn.com.hbscjt.app.framework.sms.core.props.SmsChannelProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : ly
 * @date : 2023-09-22 15:23
 **/
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SmsChannelProperties.class)
@ConditionalOnProperty(name = "boot.sms.enabled",havingValue = "true")
public class SmsAutoConfiguration {

    @Bean
    public SmsClientFactory smsClientFactory(SmsChannelProperties properties) {
        return new SmsClientFactoryImpl(properties);
    }
}
