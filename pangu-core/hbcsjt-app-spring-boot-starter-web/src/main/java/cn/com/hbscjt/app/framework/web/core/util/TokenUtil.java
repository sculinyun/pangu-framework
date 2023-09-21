package cn.com.hbscjt.app.framework.web.core.util;

import cn.com.hbscjt.app.framework.common.constant.SystemConstant;
import cn.com.hbscjt.app.framework.common.entity.LoginUser;
import cn.com.hbscjt.app.framework.redis.core.RedisService;
import cn.com.hbscjt.app.module.member.api.oauth2.dto.Oauth2TokenDTO;
import cn.com.hbscjt.app.module.member.api.userbasic.UserApi;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;

/**
 * Token工具类
 */
@RequiredArgsConstructor
public class TokenUtil {
    private static UserApi userApi;
    private final RedisService redisService;
    private String BEARER = "bearer";
    private Integer AUTH_LENGTH = 7;
    private String ACCESS_TOKEN = "access_token:%s";

    /**
     * 获取用户信息
     */
    public  LoginUser getUser(HttpServletRequest request) {
        String auth = request.getHeader(SystemConstant.AUTH_TOKEN);
        String token = getToken(auth);
        String redisKey=formatKey(token);
        Oauth2TokenDTO tokenDTO=(Oauth2TokenDTO)redisService.get(redisKey);
        return userApi.getUserById(tokenDTO.getUserId()).getCheckedData();
    }

    private String formatKey(String accessToken) {
        return String.format(ACCESS_TOKEN, accessToken);
    }

    /**
     * 获取token串
     *
     * @param auth token
     * @return String
     */
    public String getToken(String auth) {
        if ((auth != null) && (auth.length() > AUTH_LENGTH)) {
            String headStr = auth.substring(0, 6).toLowerCase();
            if (headStr.compareTo(BEARER) == 0) {
                auth = auth.substring(7);
            }
            return auth;
        }
        return null;
    }


}
