package cn.com.hbscjt.app.framework.seata.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Seata配置
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "boot.seata")

public class SeataProperties {

  private String applicationId;

  private String txServiceGroup;

}
