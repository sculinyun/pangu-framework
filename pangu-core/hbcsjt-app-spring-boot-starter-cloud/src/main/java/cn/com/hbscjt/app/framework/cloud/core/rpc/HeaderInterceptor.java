package cn.com.hbscjt.app.framework.cloud.core.rpc;

import cn.com.hbscjt.app.framework.cloud.core.util.HttpUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * @author : ly
 * 只向下游传递指定类型的header
 **/
public class HeaderInterceptor implements RequestInterceptor {

    private static final String[] ALLOW_HEADS = new String[]{
            "authorization", "inner","traceid","x-trace-id","source","version"
    };

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request= HttpUtils.getRequest();
        List<String> allowHeadsList = new ArrayList<>(Arrays.asList(ALLOW_HEADS));
        if (null != request) {
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement().toLowerCase();
                    String values = request.getHeader(name);
                    if(allowHeadsList.contains(name)&& StringUtils.isNotEmpty(values)){
                        requestTemplate.header(name, values);
                    }
                }
            }
        }
    }
}
