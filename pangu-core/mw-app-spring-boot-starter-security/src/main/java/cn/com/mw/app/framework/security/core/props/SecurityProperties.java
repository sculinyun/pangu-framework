package cn.com.mw.app.framework.security.core.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.List;

@ConfigurationProperties(prefix = "boot.security")
@Validated
@Data
public class SecurityProperties {

    /**
     *是否开启security
     */
    private boolean enabled=Boolean.FALSE;
    /**
     * HTTP 请求时，访问令牌的请求 Header
     */
    @NotEmpty(message = "Token Header 不能为空")
    private String tokenHeader = "Authorization";
    /**
     * HTTP 请求时，访问令牌的请求 Header
     */
    @NotEmpty(message = "来源不能为空")
    private String source = "APP";
    /**
     * 免登录的 URL 列表
     */
    private List<String> permitAllUrls = Collections.emptyList();

    /**
     * 权限接口URL
     */
    @NotEmpty(message = "权限接口Url不能为空")
    private String permissionApiUrl="http://scjt";
}
