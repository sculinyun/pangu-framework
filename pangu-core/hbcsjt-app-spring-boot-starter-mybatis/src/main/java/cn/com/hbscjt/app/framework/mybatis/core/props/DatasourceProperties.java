package cn.com.hbscjt.app.framework.mybatis.core.props;

import cn.com.hbscjt.app.framework.common.factory.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;

/**
 * datasource配置
 */
@Data
@RefreshScope
@ConfigurationProperties("boot.mybatis.datasource")
@PropertySource(factory = YamlPropertySourceFactory.class,value = "classpath:druid.yml")
public class DatasourceProperties {
      //druid登录用户名
	  private String druidName;
      //druid登录密码
      private String druidPwd;
      //druid 允许登录地址
      private String allow;
      //主库配置
      private DbConfig master;
      //从库配置
      private DbConfig salve;
      //代码生成的库
      private DbConfig gen;
      @Data
      public static class DbConfig{
          private String url;
          private String username;
          private String password;
          private String driverClassName;
      }
}
