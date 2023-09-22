package cn.com.hbscjt.app.framework.develop.core.pojo;

import cn.com.hbscjt.app.framework.mybatis.core.base.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 代码生成 table 表定义
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CodegenTableDO extends BaseDO {

    /**
     * ID 编号
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 数据源编号
     */
    private Long dataSourceConfigId;
    /**
     * 生成场景
     */
    private Integer scene;

    // ========== 表相关字段 ==========

    /**
     * 表名称
     *
     * 关联 {@link TableInfo#getName()}
     */
    private String tableName;
    /**
     * 表描述
     *
     * 关联 {@link TableInfo#getComment()}
     */
    private String tableComment;
    /**
     * 备注
     */
    private String remark;

    // ========== 类相关字段 ==========

    /**
     * 模块名，即一级目录
     */
    private String moduleName;
    /**
     * 业务名，即二级目录
     */
    private String businessName;
    /**
     * 类名称（首字母大写）
     */
    private String className;
    /**
     * 类描述
     */
    private String classComment;
    /**
     * 作者
     */
    private String author;

    // ========== 生成相关字段 ==========
    /**
     * 模板类型
     */
    private Integer templateType;
}
