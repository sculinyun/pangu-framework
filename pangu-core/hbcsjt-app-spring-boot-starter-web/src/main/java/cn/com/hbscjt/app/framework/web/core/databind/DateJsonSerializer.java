package cn.com.hbscjt.app.framework.web.core.databind;

import cn.com.hbscjt.app.framework.web.core.convert.JsonDateConvert;
import cn.hutool.core.util.ReflectUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author : ly
 * @date : 2022-11-03 20:52
 **/
public class DateJsonSerializer extends JsonSerializer {

    @Override
    @SuppressWarnings("deprecation")
    public void serialize(Object object, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        //序列化优化,以时间上面注解为第一选择,没有的话采用自动处理逻辑
        //场景一 response最外层只需要2022-01展示
        Calendar c = Calendar.getInstance();
        Date date=(Date)object;
        c.setTime(date);
        Field field=ReflectUtil.getField(gen.getOutputContext().getCurrentValue().getClass(),gen.getOutputContext().getCurrentName());
        if(field==null){
            DateFormat specialSdf=new SimpleDateFormat(JsonDateConvert.formats.get(4));
            gen.writeString(specialSdf.format(date));
            return;
        }
        field.setAccessible(true);
        JsonFormat fieldAnnotation = field.getDeclaredAnnotation(JsonFormat.class);
        if(fieldAnnotation!=null){
            String pattern=fieldAnnotation.pattern();
            DateFormat dateFormat=new SimpleDateFormat(pattern);
            gen.writeString(dateFormat.format(date));
        }else {
            Integer year = c.get(Calendar.YEAR);
            DateFormat normalSdf=new SimpleDateFormat(JsonDateConvert.formats.get(3));
            DateFormat specialSdf=new SimpleDateFormat(JsonDateConvert.formats.get(4));
            if(JsonDateConvert.BEGIN.equals(year)){
                gen.writeString(specialSdf.format(date));
            }else {
                gen.writeString(normalSdf.format(date));
            }
        }
    }
}
