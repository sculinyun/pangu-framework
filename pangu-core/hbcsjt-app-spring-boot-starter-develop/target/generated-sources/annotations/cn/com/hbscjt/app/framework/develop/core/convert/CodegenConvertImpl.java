package cn.com.hbscjt.app.framework.develop.core.convert;

import cn.com.hbscjt.app.framework.develop.core.pojo.CodegenColumnDO;
import cn.com.hbscjt.app.framework.develop.core.pojo.CodegenTableDO;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableField.MetaInfo;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.apache.ibatis.type.JdbcType;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-22T09:34:55+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_291 (Oracle Corporation)"
)
public class CodegenConvertImpl implements CodegenConvert {

    @Override
    public CodegenTableDO convert(TableInfo bean) {
        if ( bean == null ) {
            return null;
        }

        CodegenTableDO codegenTableDO = new CodegenTableDO();

        codegenTableDO.setTableName( bean.getName() );
        codegenTableDO.setTableComment( bean.getComment() );

        return codegenTableDO;
    }

    @Override
    public List<CodegenColumnDO> convertList(List<TableField> list) {
        if ( list == null ) {
            return null;
        }

        List<CodegenColumnDO> list1 = new ArrayList<CodegenColumnDO>( list.size() );
        for ( TableField tableField : list ) {
            list1.add( convert( tableField ) );
        }

        return list1;
    }

    @Override
    public CodegenColumnDO convert(TableField bean) {
        if ( bean == null ) {
            return null;
        }

        CodegenColumnDO codegenColumnDO = new CodegenColumnDO();

        codegenColumnDO.setColumnName( bean.getName() );
        codegenColumnDO.setDataType( getDataType( beanMetaInfoJdbcType( bean ) ) );
        codegenColumnDO.setColumnComment( bean.getComment() );
        codegenColumnDO.setNullable( beanMetaInfoNullable( bean ) );
        codegenColumnDO.setPrimaryKey( bean.isKeyFlag() );
        codegenColumnDO.setAutoIncrement( bean.isKeyIdentityFlag() );
        codegenColumnDO.setJavaType( beanColumnTypeType( bean ) );
        codegenColumnDO.setJavaField( bean.getPropertyName() );

        return codegenColumnDO;
    }

    private JdbcType beanMetaInfoJdbcType(TableField tableField) {
        if ( tableField == null ) {
            return null;
        }
        MetaInfo metaInfo = tableField.getMetaInfo();
        if ( metaInfo == null ) {
            return null;
        }
        JdbcType jdbcType = metaInfo.getJdbcType();
        if ( jdbcType == null ) {
            return null;
        }
        return jdbcType;
    }

    private Boolean beanMetaInfoNullable(TableField tableField) {
        if ( tableField == null ) {
            return null;
        }
        MetaInfo metaInfo = tableField.getMetaInfo();
        if ( metaInfo == null ) {
            return null;
        }
        boolean nullable = metaInfo.isNullable();
        return nullable;
    }

    private String beanColumnTypeType(TableField tableField) {
        if ( tableField == null ) {
            return null;
        }
        IColumnType columnType = tableField.getColumnType();
        if ( columnType == null ) {
            return null;
        }
        String type = columnType.getType();
        if ( type == null ) {
            return null;
        }
        return type;
    }
}
