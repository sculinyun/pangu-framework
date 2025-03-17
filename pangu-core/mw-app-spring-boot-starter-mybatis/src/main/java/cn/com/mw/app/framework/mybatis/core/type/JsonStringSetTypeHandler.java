package cn.com.mw.app.framework.mybatis.core.type;

import cn.com.mw.app.framework.common.util.JsonUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Set;

/**
 * 参考 {@link com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler} 实现
 *
 */
public class JsonStringSetTypeHandler extends AbstractJsonTypeHandler<Object> {

    private static final TypeReference<Set<String>> typeReference = new TypeReference<Set<String>>(){};

    @Override
    protected Object parse(String json) {
        return JsonUtils.parseObject(json, typeReference);
    }

    @Override
    protected String toJson(Object obj) {
        return JsonUtils.toJsonString(obj);
    }

}
