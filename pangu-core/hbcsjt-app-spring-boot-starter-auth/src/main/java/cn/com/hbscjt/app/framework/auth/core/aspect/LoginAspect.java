package cn.com.hbscjt.app.framework.auth.core.aspect;

import cn.com.hbscjt.app.framework.auth.core.annotation.Login;
import cn.com.hbscjt.app.framework.common.entity.LoginUser;
import cn.com.hbscjt.app.framework.common.exception.AuthException;
import cn.com.hbscjt.app.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.com.hbscjt.app.framework.common.util.ClassUtil;
import cn.com.hbscjt.app.framework.web.core.util.WebFrameworkUtils;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * APP登录态验证
 */
@Slf4j
@Aspect
public class LoginAspect {

	@Around("@annotation(cn.com.hbscjt.app.framework.auth.core.annotation.Login)")
	public Object around(ProceedingJoinPoint point) throws Throwable {
        //后续可能对Login添加另外的业务逻辑
        Login preLogin = ClassUtil.getClassAnnotation(point, Login.class);
        LoginUser loginUser = WebFrameworkUtils.getLoginUser();
        if(ObjectUtil.isEmpty(loginUser)){
            throw new AuthException(GlobalErrorCodeConstants.UNAUTHORIZED);
        }else {
            return point.proceed();
        }
	}

}
