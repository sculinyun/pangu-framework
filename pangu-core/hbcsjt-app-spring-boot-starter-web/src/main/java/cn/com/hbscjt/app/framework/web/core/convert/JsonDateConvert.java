package cn.com.hbscjt.app.framework.web.core.convert;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : ly
 * @date : 2022-11-02 17:42
 **/
public class JsonDateConvert {
    //静态初始化final，共享
    public static final JsonDateConvert instance = new JsonDateConvert();
    public static final Integer BEGIN=1970;
    public static final List<String> formats = new ArrayList<>(7);

    static{
        formats.add("yyyy-MM");
        formats.add("yyyy-MM-dd");
        formats.add("yyyy-MM-dd HH:mm");
        formats.add("yyyy-MM-dd HH:mm:ss");
        formats.add("HH:mm:ss");
        formats.add("HH:mm");
        formats.add("^\\d{13}$");
    }

    public static Date getDate(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        if(source.matches("^\\d{4}-\\d{1,2}$")){
            return parseDate(source, formats.get(0));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")){
            return parseDate(source, formats.get(1));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")){
            return parseDate(source, formats.get(2));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")){
            return parseDate(source, formats.get(3));
        }else if(source.matches("^\\d{1,2}:\\d{1,2}:\\d{1,2}$")){
            return parseDate(source, formats.get(4));
        }else if(source.matches("^\\d{1,2}:\\d{1,2}$")){
            return parseDate(source, formats.get(5));
        }else {
            return null;
        }
    }

    public  static Date parseDate(String source, String format) {
        Date date=null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            date = dateFormat.parse(source);
        } catch (Exception e) {
            return null;
        }
        return date;
    }
}
