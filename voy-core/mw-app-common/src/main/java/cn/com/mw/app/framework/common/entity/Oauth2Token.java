package cn.com.mw.app.framework.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Oauth2Token implements Serializable {

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;
    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号")
    private Long userId;
    /**
     * 租户编号
     */
    private Long tenantId;
    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型")
    private Integer userType;
    /**
     * 访问令牌
     */
    @ApiModelProperty(value = "访问令牌")
    private String accessToken;
    /**
     * 刷新令牌
     */
    @ApiModelProperty(value = "刷新令牌")
    private String refreshToken;
    /**
     * 客户端编号
     */
    @ApiModelProperty(value = "客户端编号")
    private String clientId;
    /**
     * 授权范围
     */
    @ApiModelProperty(value = "授权范围")
    private List<String> scopes;
    /**
     * 访问令牌过期时间
     */
    @ApiModelProperty(value = "访问令牌过期时间")
    private Date accessExpiresTime;
    /**
     * 刷新令牌过期时间
     */
    @ApiModelProperty(value = "刷新令牌过期时间")
    private Date refreshExpiresTime;
    /**
     * 访问令牌创建时间
     */
    @ApiModelProperty(value = "访问令牌创建时间")
    private Date accessCreateTime;
    /**
     * 刷新令牌创建时间
     */
    @ApiModelProperty(value = "刷新令牌创建时间")
    private Date refreshCreateTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 最后更新时间
     */
    @ApiModelProperty(value = "最后更新时间")
    private Date updateTime;

    /**
     * 创建者，目前使用 SysUser 的 id 编号
     */
    @ApiModelProperty(value = "创建者")
    private Long creator;

    /**
     * 更新者，目前使用 SysUser 的 id 编号
     */
    @ApiModelProperty(value = "更新者")
    private Long updater;

    /**
     * 状态[1:正常]
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    private Boolean deleted;
}
