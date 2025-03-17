package cn.com.mw.app.framework.web.core.convert;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * @author : ly
 * @date : 2022-11-14 09:45
 * * 处理springmvc请求 主要是get请求 他的body不是json 无法用jackson处理
 * 支持常规字符串和时间搓,不需要DateTimeFormat只指定一个pattern
 **/

public class MvcDateConvert implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        if(source.matches("^\\d{4}-\\d{1,2}$")){
            return JsonDateConvert.parseDate(source, JsonDateConvert.formats.get(0));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")){
            return JsonDateConvert.parseDate(source, JsonDateConvert.formats.get(1));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")){
            return JsonDateConvert.parseDate(source, JsonDateConvert.formats.get(2));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")){
            return JsonDateConvert.parseDate(source, JsonDateConvert.formats.get(3));
        }else if(source.matches(JsonDateConvert.formats.get(6))){
            return new Date(Long.parseLong(source));
        }else {
            return null;
        }
    }
}
