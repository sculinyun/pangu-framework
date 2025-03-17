package cn.com.mw.app.framework.mybatis.core.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * mybatis配置
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties("boot.mybatis.info")
public class MybatisProperties {
	/**
	 * 是否打印可执行sql
	 */
	private boolean sql = false;
    /**
     *实体扫描，多个package用逗号或者分号分隔,微服务项目中,一般只写一个basePackage即可
     */
    private String basePackage;
    /**
     *JDBC驱动名称
     */
    private String driverClassName;
}
