package cn.com.hbscjt.app.framework.boot.core.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 核心配置
 */
@Data
@ConfigurationProperties("boot.core")
public class BootCoreProperties {
    //当前系统环境 比如dev,test,prod
	private String env;
    private String version="1.0.0";
    //应用名称
    private String appName;
    //应用端口
    private String port;
}
