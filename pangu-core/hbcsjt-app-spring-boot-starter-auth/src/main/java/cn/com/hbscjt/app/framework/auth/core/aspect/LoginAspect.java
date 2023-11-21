package cn.com.hbscjt.app.framework.auth.core.aspect;

import cn.com.hbscjt.app.framework.auth.core.annotation.Login;
import cn.com.hbscjt.app.framework.common.entity.LoginUser;
import cn.com.hbscjt.app.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.com.hbscjt.app.framework.web.core.util.WebFrameworkUtils;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

import static cn.com.hbscjt.app.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * APP登录态验证
 */
@Slf4j
@Aspect
public class LoginAspect {

	@Around("@annotation(cn.com.hbscjt.app.framework.auth.core.annotation.Login)")
	public Object around(ProceedingJoinPoint point) throws Throwable {
        //后续可能对Login添加另外的业务逻辑
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Login preLogin = method.getAnnotation(Login.class);
        if(ObjectUtil.isNotEmpty(preLogin)){
            LoginUser loginUser = WebFrameworkUtils.getLoginUser();
            if(ObjectUtil.isEmpty(loginUser)){
                throw exception(GlobalErrorCodeConstants.UNAUTHORIZED);
            }
        }
        return point.proceed();
	}

}
