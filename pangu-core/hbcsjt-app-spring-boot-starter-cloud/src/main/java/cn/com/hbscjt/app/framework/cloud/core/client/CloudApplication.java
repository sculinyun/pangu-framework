package cn.com.hbscjt.app.framework.cloud.core.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.*;

import static cn.com.hbscjt.app.framework.common.constant.ProjectConstant.BASE_PACKAGES;

/**
 * Cloud启动注解配置
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableDiscoveryClient
@EnableFeignClients(BASE_PACKAGES)
@SpringBootApplication
public @interface CloudApplication {

}
