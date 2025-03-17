package cn.com.mw.app.framework.auth.core.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Token属性
 */
@Getter
@Setter
@ConfigurationProperties(AuthProperties.PREFIX)
public class AuthProperties {

	/**
	 * 前缀
	 */
	public static final String PREFIX = "boot.auth";

	/**
	 * 是否开启Login验证
	 */
	private Boolean appEnabled = Boolean.FALSE;

    /**
     * 是否开启preAuth验证
     */
    private Boolean webEnabled = Boolean.FALSE;

}
