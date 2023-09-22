package cn.com.hbscjt.app.framework.common.util;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;

/**
 * IP工具类
 *
 * @author pangu
 */
@Slf4j
public class IPUtil {

	private final static boolean ipLocal = false;

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

//	public static void main(String[] args) {
//		System.err.println(getCityInfo("220.248.12.158"));
//	}
}
