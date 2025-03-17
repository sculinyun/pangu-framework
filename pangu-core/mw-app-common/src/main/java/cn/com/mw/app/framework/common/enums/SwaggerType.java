package cn.com.mw.app.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SwaggerType {
    MANAGER("admin-api", "管理端"),
    APP("app-api", "APP端");

    private String prefix;
    private String label;
}
