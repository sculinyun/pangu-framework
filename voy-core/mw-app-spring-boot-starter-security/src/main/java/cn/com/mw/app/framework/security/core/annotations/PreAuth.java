package cn.com.mw.app.framework.security.core.annotations;

import java.lang.annotation.*;

/**
 * 声明用户需要登录
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PreAuth {
    String value();
}
