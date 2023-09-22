package cn.com.hbscjt.app.framework.develop.core.engine;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.ITypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;

/**
 * @author : ly
 * @date : 2023-09-06 10:32
 **/
public class MySqlTypeConvertCustom extends MySqlTypeConvert implements ITypeConvert {
    @Override
    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
        String t = fieldType.toLowerCase();
        if (t.contains("tinyint(1)")) {
            return DbColumnType.BOOLEAN;
        }
        if (t.contains("tinyint(4)")) {
            return DbColumnType.INTEGER;
        }
        return super.processTypeConvert(globalConfig, fieldType);
    }
}
