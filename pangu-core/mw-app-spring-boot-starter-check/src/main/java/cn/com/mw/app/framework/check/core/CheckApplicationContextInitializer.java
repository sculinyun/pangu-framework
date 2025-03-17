package cn.com.mw.app.framework.check.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author : ly
 * @date : 2024-03-07 16:49
 **/
@Slf4j
@RequiredArgsConstructor
public class CheckApplicationContextInitializer implements ApplicationContextInitializer {
    private final CheckUtil checkUtil;
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        log.info("[pangu-framework]开启参数检查");
        checkUtil.checkAll();
    }
}
