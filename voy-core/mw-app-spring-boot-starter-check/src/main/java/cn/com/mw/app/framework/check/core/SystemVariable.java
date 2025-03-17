package cn.com.mw.app.framework.check.core;

/**
 * @author : ly
 * @date : 2024-03-08 09:50
 * 所有的关键核心变量
 **/
public interface SystemVariable {
    /**
     * 环境变量 主要为DEV,TEST,PROD等
     */
    String env="boot.core.env";
    String redis="boot.web.redis.enable";
    String swagger="boot.web.swagger.enable";
    String mybatis="boot.web.mybatis.enable";
    String appName="boot.core.appName";
    String maxFileSize="spring.servlet.multipart.max-file-size";
    String maxRequestSize="spring.servlet.multipart.max-request-size";
}
