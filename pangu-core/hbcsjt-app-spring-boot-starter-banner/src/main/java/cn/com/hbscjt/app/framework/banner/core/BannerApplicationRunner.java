package cn.com.hbscjt.app.framework.banner.core;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * 项目启动成功后
 * 1,打印成功标志
 * 2,打印文档路径
 */
@Slf4j
public class BannerApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        Environment environment=SpringUtil.getBean(Environment.class);
        String bootAppName=environment.getProperty("boot.core.appName");
        String bootPort =environment.getProperty("boot.core.port");
        String bootEnv=environment.getProperty("boot.core.env");
        if(ObjectUtil.isEmpty(bootAppName)){
            log.info("*****[基础检查]项目应用名称不能为空*****");
            return;
        }
        if(ObjectUtil.isEmpty(bootPort)){
            log.info("*****[基础检查]项目端口号不能为空*****");
            return;
        }
        String bootName = bootAppName.toUpperCase();
        String activeProfile = StringUtils.arrayToCommaDelimitedString(environment.getActiveProfiles());
        String profile=ObjectUtil.isNotEmpty(activeProfile)?activeProfile:bootEnv;

        ThreadUtil.execute(() -> {
            ThreadUtil.sleep(1, TimeUnit.SECONDS); // 延迟 1 秒，保证输出到结尾
            log.info("---[{}]---启动完成，当前使用的端口:[{}]，环境变量:[{}]---", bootName, bootPort, profile);
        });
    }

}
