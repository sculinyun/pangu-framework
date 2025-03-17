package cn.com.mw.app.framework.common.util;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * IP工具类
 *
 * @author pangu
 */
@Slf4j
public class IPUtil {

	private static final boolean ipLocal = false;
    private static final String UNKNOWN = "unknown";

	/**
	 * 根据ip获取详细地址
	 */
	public static String getCityInfo(String ip) {
		if (ipLocal) {
			//待开发
			return null;
		} else {
			return getHttpCityInfo(ip);
		}
	}

	/**
	 * 根据ip获取详细地址
	 * 临时使用，待调整
	 */
	public static String getHttpCityInfo(String ip) {
		String api = String.format("http://whois.pconline.com.cn/ipJson.jsp?ip=%s&json=true", ip);
        String result=(String) HttpUtil.getRequest(api, "gbk");
        JsonNode node=JsonUtils.parseTree(result);
		return node.get("addr").toString();
	}

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取请求IP
     *
     * @return String IP
     */
    public static String getHttpServletRequestIpAddress() {
        HttpServletRequest request = getHttpServletRequest();
        return getHttpServletRequestIpAddress(request);
    }


    public static String getHttpServletRequestIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            ip = ip.split(",")[0];
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
