package cn.com.hbscjt.app.framework.cloud.core.aop;

import cn.com.hbscjt.app.framework.cloud.core.annotation.Inner;
import cn.com.hbscjt.app.framework.common.constant.SystemConstant;
import cn.com.hbscjt.app.framework.common.exception.AuthException;
import cn.com.hbscjt.app.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : ly
 * @date : 2023-10-08 10:34
 **/

@Slf4j
@Aspect
@RequiredArgsConstructor
public class InnerAspect {
    private final HttpServletRequest request;

    @Around("@annotation(inner)")
    public Object around(ProceedingJoinPoint point, Inner inner) throws Throwable {
        String header = request.getHeader(SystemConstant.FROM);
        if (inner.value() && !StrUtil.equals(SystemConstant.FROM, header)) {
            log.warn("访问接口{}没有权限", point.getSignature().getName());
            throw new AuthException(GlobalErrorCodeConstants.FORBIDDEN);
        }
        return point.proceed();
    }
}
