package cn.com.mw.app.framework.boot.core.resolver;

import cn.com.mw.app.framework.common.entity.LoginUser;
import cn.com.mw.app.framework.web.core.util.WebFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Token转化LoginUser
 */
@Slf4j
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {

	/**
	 * 入参筛选
	 */
	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterType().equals(LoginUser.class);
	}

	/**
	 * 出参设置
	 */
	@Override
	public Object resolveArgument(MethodParameter methodParameter,
								  ModelAndViewContainer modelAndViewContainer,
								  NativeWebRequest nativeWebRequest,
								  WebDataBinderFactory webDataBinderFactory) {
		return WebFrameworkUtils.getLoginUser();
	}

}
