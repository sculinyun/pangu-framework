package cn.com.hbscjt.app.framework.security.core.aop;

import cn.com.hbscjt.app.framework.common.util.ClassUtil;
import cn.com.hbscjt.app.framework.common.util.StringUtil;
import cn.com.hbscjt.app.framework.security.core.annotations.PreAuth;
import cn.com.hbscjt.app.framework.security.core.service.SecurityFrameworkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.MethodParameter;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

import static cn.com.hbscjt.app.framework.common.exception.enums.GlobalErrorCodeConstants.UNAUTHORIZED;
import static cn.com.hbscjt.app.framework.common.exception.util.ServiceExceptionUtil.exception;

@Aspect
@Slf4j
@RequiredArgsConstructor
public class PreAuthAspect{

    private final ApplicationContext applicationContext;
    private final SecurityFrameworkService securityFrameworkService;

    private static final ExpressionParser SPEL_PARSER = new SpelExpressionParser();

    @Around("@annotation(preAuth)")
    public Object around(ProceedingJoinPoint point, PreAuth preAuth) throws Throwable {
        if (handleAuth(point)) {
            return point.proceed();
        }
        throw exception(UNAUTHORIZED);
    }


    private boolean handleAuth(ProceedingJoinPoint point) {
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        PreAuth preAuth = ClassUtil.getAnnotation(method, PreAuth.class);
        // 判断表达式
        String condition = preAuth.value();
        if (StringUtil.isNotBlank(condition)) {
            Expression expression = SPEL_PARSER.parseExpression(condition);
            // 方法参数值
            Object[] args = point.getArgs();
            StandardEvaluationContext context = getEvaluationContext(method, args);
            return expression.getValue(context, Boolean.class);
        }
        return false;
    }

    private StandardEvaluationContext getEvaluationContext(Method method, Object[] args) {
        // 初始化Sp el表达式上下文，并设置 AuthFun
        StandardEvaluationContext context = new StandardEvaluationContext(securityFrameworkService);
        // 设置表达式支持spring bean
        context.setBeanResolver(new BeanFactoryResolver(applicationContext));
        for (int i = 0; i < args.length; i++) {
            // 读取方法参数
            MethodParameter methodParam = ClassUtil.getMethodParameter(method, i);
            // 设置方法 参数名和值 为sp el变量
            context.setVariable(methodParam.getParameterName(), args[i]);
        }
        return context;
    }

}
