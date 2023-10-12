package cn.com.hbscjt.app.framework.cloud.core.annotation;

import java.lang.annotation.*;

/**
 * feign内部调用注解
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Inner {

    /**
     * 是否AOP统一处理(可以理解为是否仅允许Feign之间调用)
     *
     * @return false, true
     */
    boolean value() default true;

    /**
     * 需要特殊判空的字段(预留)
     *
     * @return {}
     */
    String[] field() default {};
}
