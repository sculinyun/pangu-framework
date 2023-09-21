package cn.com.hbscjt.app.framework.security.core.service;


import cn.com.hbscjt.app.module.member.api.permission.PermissionApi;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import static cn.com.hbscjt.app.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;


/**
 * 默认的 {@link SecurityFrameworkService} 实现类
 */
@RequiredArgsConstructor
public class SecurityFrameworkServiceImpl implements SecurityFrameworkService {

    private final PermissionApi permissionApi;

    @Override
    public boolean hasPermission(String permission) {
        return hasAnyPermissions(permission);
    }

    @Override
    @SneakyThrows
    public boolean hasAnyPermissions(String... permissions) {
        return permissionApi.hasAnyPermissions(getLoginUserId(),permissions).getCheckedData();
    }

    @Override
    public boolean hasRole(String role) {
        return hasAnyRoles(role);
    }

    @Override
    @SneakyThrows
    public boolean hasAnyRoles(String... roles) {
        return true;
    }

    @Override
    public boolean hasScope(String scope) {
        return hasAnyScopes(scope);
    }

    @Override
    public boolean hasAnyScopes(String... scope) {
        return true;
    }

}
