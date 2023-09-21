package cn.com.hbscjt.app.framework.web.core.databind;

import cn.com.hbscjt.app.framework.web.core.convert.JsonDateConvert;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Date;

/**
 * @author : ly
 * @date : 2022-11-03 21:00
 **/
public class DateJsonDeSerializer extends JsonDeserializer {

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException{
        String time = jsonParser.readValueAs(String.class);
        Date date= JsonDateConvert.getDate(time);
        return date;
    }
}
