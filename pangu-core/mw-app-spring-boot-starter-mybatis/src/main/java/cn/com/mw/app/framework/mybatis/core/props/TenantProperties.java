package cn.com.mw.app.framework.mybatis.core.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 租户属性
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties(prefix = "boot.mybatis.tenant")
public class TenantProperties {

    /**
     * 是否开启租户模式
     */
    private Boolean enable = false;

    /**
     * 需要排除的多租户的表
     */
    private List<String> ignoreTables = Arrays.asList("sys_menu","sys_dict","sys_client",
            "sys_tenant", "sys_role_permission","sys_config","sys_data_source","sys_attachment");

    /**
     * 多租户字段名称
     */
    private String column = "tenant_id";

    /**
     * 排除不进行租户隔离的sql
     */
    private List<String> ignoreSqls = new ArrayList<>();
}
