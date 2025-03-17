package cn.com.mw.app.framework.common.service;

import org.springframework.core.Ordered;

/**
 * Permission扩展 用于权限发现
 *
 */
public interface PermissionService extends Ordered, Comparable<PermissionService> {

    public boolean hasPermission(String permission);

    public boolean hasAnyPermissions(String... permissions);

    public boolean hasRole(String role);

    public boolean hasAnyRoles(String... roles);

    public boolean hasScope(String scope);

    public boolean hasAnyScopes(String... scope);

	/**
	 * 获取排列顺序
	 */
	@Override
	default int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;

	}

	/**
	 * 对比排序
	 */
	@Override
	default int compareTo(PermissionService o) {
		return Integer.compare(this.getOrder(), o.getOrder());
	}

}
