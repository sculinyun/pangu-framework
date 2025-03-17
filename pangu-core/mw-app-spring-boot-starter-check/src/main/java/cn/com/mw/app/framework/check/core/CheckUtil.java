package cn.com.mw.app.framework.check.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.AbstractEnvironment;


/**
 * @author : ly
 * @date : 2024-03-07 16:50
 **/
@Slf4j
@RequiredArgsConstructor
public class CheckUtil {
    /** 操作系统类别: 1是win, 2是其他 */
    private static Integer osType;
    private final AbstractEnvironment environment;

    public void InitCheck() {
        //获取操作系统类型
        String osName = System.getProperty("os.name");
        this.osType = osName.contains("windows")?1:2;
    }


    /**
     * 对外提供的调用方法,在 new 完InitCheck之后,就调用这个方法
     *
     * @return
     */
    public boolean checkAll(){
        return checkRedis();
    }


    /**
     * 检查Redis的配置是否正确
     * @return
     */
    private boolean checkRedis(){
        String redisValue=environment.getProperty(SystemVariable.redis);
        if(StringUtils.isEmpty(redisValue)){
            log.info("检查Redis配置失败!");
            return false;
        }
        log.info("检查Redis配置正常");
        return true;
    }

}
