package cn.com.hbscjt.app.framework.redis.core;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * redis配置
 */
@Data
@ConfigurationProperties("boot.web.redis")
public class RedisProperties {
    /**
     * 是否开启Lettuce
     */
    private Boolean enabled = false;
    /**
     * redis地址,暂时支持单机版本
     */
    private String host;
    /**
     * redis 端口
     */
    private String port;
    /**
     * redis 数据库号
     */
    private Integer database;
}
