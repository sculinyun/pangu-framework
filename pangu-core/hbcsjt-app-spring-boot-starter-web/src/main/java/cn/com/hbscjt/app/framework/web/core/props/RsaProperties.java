package cn.com.hbscjt.app.framework.web.core.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : ly
 **/
@ConfigurationProperties("boot.web.rsa")
@Data
public class RsaProperties {
    //私钥
    private String privateKey;
    //公钥
    private String publicKey;
}
