package cn.com.mw.app.framework.common.enums;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : ly
 * @date : 2022-11-07 16:26
 **/
@Getter
@AllArgsConstructor
public enum MicoServiceEnum {
    GATEWAY("gateway-server", "网关","gateway"),
    BPM_ADMIN("bpm-admin-api","管理端-工作流","bpm"),
    BPM_APP("bpm-app-api","APP端-工作流","bpm"),
    DISPATCHING_ADMIN("dispatching-admin-api","管理端-指挥调度","dispatching"),
    DISPATCHING_APP("dispatching-app-api","APP端-指挥调度","dispatching"),
    INFRA_ADMIN("infra-admin-api","管理端-基础设置","infra"),
    INFRA_APP("infra-app-api","APP端-基础设置","infra"),
    INTELLIGENCE_ADMIN("intelligence-admin-api","管理端-智慧运营","intelligence"),
    INTELLIGENCE_APP("intelligence-app-api","APP端-智慧运营","intelligence"),
    IOC_ADMIN("ioc-admin-api","管理端-大屏接口","iot"),
    IOT_ADMIN("iot-admin-api","管理端-智慧物联","iot"),
    IOT_APP("iot-app-api","APP端-智慧物联","iot"),
    SYSTEM_ADMIN("system-admin-api", "管理端-系统管理","system"),
    SYSTEM_APP("system-app-api", "APP端-系统管理","system"),
    UNKNOWN("unknown", "未知服务","unknown");
    /**
     * 微服务code
     */
    private final String code;
    /**
     * 微服务名称
     */
    private final String label;

    /**
     * 服务分组概念
     */
    private final String group;

    public static MicoServiceEnum getMicoService(String code) {
        return Optional.ofNullable(ArrayUtil.firstMatch(micoServiceEnum -> micoServiceEnum.getCode().equals(code), MicoServiceEnum.values())).orElse(UNKNOWN);
    }

    /**
     * 去掉group中重复的元素
     * @param enumList
     * @return
     */
    public static List<MicoServiceEnum> filterGroup(List<MicoServiceEnum> enumList){
        List<MicoServiceEnum> uniqueList = enumList.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(MicoServiceEnum::getGroup))), ArrayList::new));
        return uniqueList;
    }
}
