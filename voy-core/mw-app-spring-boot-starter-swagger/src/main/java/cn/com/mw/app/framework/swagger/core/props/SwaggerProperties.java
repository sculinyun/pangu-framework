package cn.com.mw.app.framework.swagger.core.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotEmpty;

/**
 * Swagger 配置属性
 */
@ConfigurationProperties("boot.web.swagger")
@Data
public class SwaggerProperties {
    /**
     * 是否开启swagger
     */
    private Boolean enabled = false;
    /**
     * 标题
     */
    @NotEmpty(message = "标题不能为空")
    private String title;
    /**
     * 描述
     */
    @NotEmpty(message = "描述不能为空")
    private String description;
    /**
     * 作者
     */
    @NotEmpty(message = "作者不能为空")
    private String author;
    /**
     * 版本
     */
    @NotEmpty(message = "版本不能为空")
    private String version;
    /**
     * 扫描的包
     */
    @NotEmpty(message = "扫描的 package 不能为空")
    private String basePackage;

}
