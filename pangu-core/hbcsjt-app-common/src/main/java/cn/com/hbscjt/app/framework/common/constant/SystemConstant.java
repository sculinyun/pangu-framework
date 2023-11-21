package cn.com.hbscjt.app.framework.common.constant;

/**
 * @author : ly
 * @date : 2022-11-07 16:42
 **/
public interface SystemConstant {
    //微服务之间传递的唯一标识
    String FRAMEWORK_TRACE_ID = "x-trace-id";

    //日志链路追踪id日志标志
    String LOG_TRACE_ID = "traceId";

    //版本
    String VERSION = "version";

    //请求header
    String AUTH_TOKEN="Authorization";

    //业务状态[1:正常]
    int DB_STATUS_NORMAL = 1;

    String TOKEN_PREFIX="access_token:%s";

    //删除状态[0:正常,1:删除]
    Boolean DB_NOT_DELETED = false;
    Boolean DB_IS_DELETED = true;

    String CONTENT_TYPE_NAME = "Content-type";
    String CONTENT_TYPE = "application/json;charset=utf-8";

    String FROM="inner";

    String HEADER_FROM="inner=inner";

    String OUT_FROM="outInner";

    String SOURCE="source";
}
