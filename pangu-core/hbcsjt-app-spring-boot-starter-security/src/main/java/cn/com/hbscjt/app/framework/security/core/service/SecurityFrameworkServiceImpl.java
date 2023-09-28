package cn.com.hbscjt.app.framework.security.core.service;


import cn.com.hbscjt.app.framework.common.constant.SystemConstant;
import cn.com.hbscjt.app.framework.common.pojo.CommonResult;
import cn.com.hbscjt.app.framework.common.util.JsonUtils;
import cn.com.hbscjt.app.framework.security.core.props.SecurityProperties;
import cn.com.hbscjt.app.framework.web.core.util.OkHttpUtils;
import cn.com.hbscjt.app.framework.web.core.util.TokenUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import static cn.com.hbscjt.app.framework.common.constant.SystemConstant.CONTENT_TYPE;
import static cn.com.hbscjt.app.framework.common.constant.SystemConstant.CONTENT_TYPE_NAME;


/**
 * 默认的 {@link SecurityFrameworkService} 实现类
 */
@RequiredArgsConstructor
public class SecurityFrameworkServiceImpl implements SecurityFrameworkService {

    private final SecurityProperties securityProperties;
    private static final TypeReference<CommonResult<Boolean>> typeReference = new TypeReference<CommonResult<Boolean>>(){};

    @Override
    @SneakyThrows
    public boolean hasPermission(String permission) {
        String permissionApiUrl=securityProperties.getPermissionApiUrl();
        String token= TokenUtil.getTokenByHeader();
        String reStr=OkHttpUtils.builder().url(permissionApiUrl)
                .addParam("permission", permission)
                .addHeader(CONTENT_TYPE_NAME, CONTENT_TYPE)
                .addHeader(SystemConstant.AUTH_TOKEN,token)
                .get()
                .sync();
        CommonResult<Boolean> result=JsonUtils.parseObject(reStr,typeReference);
        return result.getCheckedData();
    }

    @Override
    public boolean hasAnyPermissions(String... permissions) {
         return true;
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
