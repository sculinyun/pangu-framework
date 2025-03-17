package cn.com.mw.app.framework.web.core.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;

/**
 * Xss 配置属性
 * 主要场景:为非网关项目starter配置.
 */
@ConfigurationProperties(prefix = "boot.xss")
@Validated
@Data
public class XssProperties {

    /**
     * 是否开启，默认为 true
     */
    private boolean enabled = true;
    /**
     * 需要排除的 URL，默认为空
     */
    private List<String> excludeUrls = Collections.emptyList();

}
