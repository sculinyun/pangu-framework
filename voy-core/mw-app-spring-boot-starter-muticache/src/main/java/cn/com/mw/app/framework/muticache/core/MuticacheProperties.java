package cn.com.mw.app.framework.muticache.core;

import cn.com.mw.app.framework.common.factory.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;

/**
 * mybatis配置
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties("boot.web.muticache")
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:jetcache.yml")
public class MuticacheProperties {
	/**
	 * 是否开启二级缓存
	 */
	private boolean enabled = false;

    /**
     * redis地址
     */
    private String uri;

    /**
     * 全局前缀
     */
    private String prefix;
}
