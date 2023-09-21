package cn.com.hbscjt.app.framework.common.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 登录用户对象
 */
@Data
@RequiredArgsConstructor
public class LoginUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private Long userId;
    /**
     * 租户编号
     */
    private Long tenantId;
    /**
     * 用户状态
     */
    private Integer status;
    /**
     * 头像
     */
    private String avatar;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 昵称
	 */
	private String nickName;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
	 * 登录类型
	 */
	private int type;
    /**
     * 授权范围
     */
    private List<String> scopes;
}
