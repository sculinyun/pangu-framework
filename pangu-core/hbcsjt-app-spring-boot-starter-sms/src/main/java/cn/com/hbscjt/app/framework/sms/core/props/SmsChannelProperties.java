package cn.com.hbscjt.app.framework.sms.core.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 短信渠道配置类
 */
@ConfigurationProperties("boot.sms")
@Data
public class SmsChannelProperties {
    /**
     *是否开启
     */
    private boolean enabled=Boolean.FALSE;
    /**
     * 渠道编号
     */
    private Integer channelId;
    /**
     * 短信签名
     */
    private String signature;
    /**
     * 渠道编码
     */

    private String code;
    /**
     * 短信API的账号
     */

    private String apiKey;
    /**
     * 短信API的密钥
     */
    private String apiSecret;
}
