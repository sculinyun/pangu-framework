package cn.com.mw.app.framework.develop.config;

import cn.com.mw.app.framework.develop.core.enums.CodegenSceneEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "boot.develop.codegen")
@Validated
@Data
public class CodegenProperties {

    /**
     * 生成的 Java 代码的基础包
     */
    @NotNull(message = "Java代码的基础包不能为空")
    private String basePackage;

    /**
     * 表名称
     */
    @NotNull(message = "表名称不能为空")
    private String tableName;
    /**
     * 表描述
     */
    private String tableComment;

    /**
     * 模块名，即一级目录
     */
    private String moduleName;
    /**
     * 业务名，即二级目录
     */
    private String businessName;

    /**
     * 本地文件目录
     */
    private String directory;

    /**
     * 作者
     */
    private String author;
    /**
     * 生成场景
     */
    private Integer scene= CodegenSceneEnum.APP.getScene();
}
