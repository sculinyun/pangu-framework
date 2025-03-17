package cn.com.mw.app.framework.web.core.util;

import cn.com.mw.app.framework.common.constant.SystemConstant;
import cn.com.mw.app.framework.common.entity.LoginUser;
import cn.com.mw.app.framework.common.entity.Oauth2Token;
import cn.com.mw.app.framework.redis.core.RedisService;

import javax.servlet.http.HttpServletRequest;

/**
 * Token工具类
 */
public class TokenUtil {
    private static RedisService redisService;
    private static String BEARER = "bearer";
    private static Integer AUTH_LENGTH = 7;
    private static String ACCESS_TOKEN = "access_token:%s";

    public TokenUtil(RedisService redisService) {
        this.redisService=redisService;
    }

    /**
     * 获取用户信息
     */
    public  static LoginUser getUser(HttpServletRequest request) {
        String auth = request.getHeader(SystemConstant.AUTH_TOKEN);
        String token = getToken(auth);
        String redisKey=formatKey(token);
        Oauth2Token tokenDTO=(Oauth2Token)redisService.get(redisKey);
        if(null==tokenDTO)return null;
        return buildLoginUserByToken(tokenDTO);
    }

    private static LoginUser buildLoginUserByToken(Oauth2Token tokenDTO){
        LoginUser loginUser=new LoginUser();
        loginUser.setUserId(tokenDTO.getUserId());
        loginUser.setType(tokenDTO.getUserType());
        loginUser.setScopes(tokenDTO.getScopes());
        loginUser.setStatus(tokenDTO.getStatus());
        loginUser.setTenantId(tokenDTO.getTenantId());
        return loginUser;
    }


    private static String formatKey(String accessToken) {
        return String.format(ACCESS_TOKEN, accessToken);
    }

    /**
     * 获取token串
     *
     * @param auth token
     * @return String
     */
    public static String getToken(String auth) {
        if ((auth != null) && (auth.length() > AUTH_LENGTH)) {
            String headStr = auth.substring(0, 6).toLowerCase();
            if (headStr.compareTo(BEARER) == 0) {
                auth = auth.substring(7);
            }
            return auth;
        }
        return null;
    }

    public static String getTokenByHeader() {
        HttpServletRequest request = WebFrameworkUtils.getRequest();
        String auth = request.getHeader(SystemConstant.AUTH_TOKEN);
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
