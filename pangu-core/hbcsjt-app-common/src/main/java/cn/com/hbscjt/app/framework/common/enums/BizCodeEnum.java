package cn.com.hbscjt.app.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务编码枚举
 *
 * @author yzs
 */
@Getter
@AllArgsConstructor
public enum BizCodeEnum {

    EVENT("BIZ001", "事件"),
    PROCESS("BIZ002", "工单"),
    ASSIST_DECISION("BIZ003","辅助决策"),
    MAINTENANCE_FEE("BIZ004","养护费用"),
    NEWS("BIZ005","新闻"),
    NEWS_CATEGORY("BIZ006","新闻分类"),

    KPI_TYPE("BIZ007","考评考核指标类型"),
    KPI_TYPE_PLAN("BIZ008","考评考核计划类型"),
    KPI_LIBRARY("BIZ009","考评考核类型指标库"),
    KPI_SCHEME("BIZ010","考评考核方案"),
    KPI_PLAN("BIZ011","考评考核计划"),
    WORK_PLAN("BIZ012","工作计划"),
    WORK_AREA("BIZ013","工作区域"),

    VIDEO_DEVICE("BIZ014","视频设备"),
    VIDEO_CATEGORY("BIZ015","视频设备分类"),

    ADMINISTRATION_APPROVE("BIZ016","行政审批"),
    ADMINISTRATION_APPROVE_TYPE("BIZ017","行政审批类型"),

    ASSET_OBJ_CATEGORY("BIZ018","部件分类"),
    MUNICIPALITY_PROJECT("BIZ019","市政工程"),
    MAINTAIN_PROJECT("BIZ020","养护项目"),
    WORK_RECORD("BIZ021","工作记录"),
    MAINTAIN_REPORT("BIZ022","养护报告"),
    
    STREET_COMMUNITY("BIZ023","街道社区"),
    ROAD("BIZ024","路段"),
    GRID("BIZ025","网格"),
    INSPECTION_RULE("BIZ026","云巡检规则"),
    INSPECTION_PLAN("BIZ027","云巡检计划")
    ;

    /**
     * 状态值
     */
    private final String code;
    /**
     * 状态名
     */
    private final String label;

}
