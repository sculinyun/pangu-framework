package cn.com.mw.app.framework.boot.core.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 异步配置
 */
@Data
@ConfigurationProperties("boot.async")
public class BootAsyncProperties {
	/**
	 * 异步核心线程数，默认：2
	 */
	private int corePoolSize = 2;
	/**
	 * 异步最大线程数，默认：50
	 */
	private int maxPoolSize = 50;
	/**
	 * 队列容量，默认：10000
	 */
	private int queueCapacity = 10000;
	/**
	 * 线程存活时间，默认：300
	 */
	private int keepAliveSeconds = 300;
}
