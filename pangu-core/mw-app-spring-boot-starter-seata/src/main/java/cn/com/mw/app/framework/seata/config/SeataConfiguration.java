package cn.com.mw.app.framework.seata.config;

import cn.com.mw.app.framework.common.factory.YamlPropertySourceFactory;
import cn.com.mw.app.framework.seata.core.SeataProperties;
import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * Seata配置
 * @EnableAutoDataSourceProxy 自动开启代理数据源
 */
@Configuration
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:core-seata.yml")
@EnableConfigurationProperties(SeataProperties.class)
@ConditionalOnBean(DataSource.class)
@EnableAutoDataSourceProxy
@Slf4j
public class SeataConfiguration {

}
