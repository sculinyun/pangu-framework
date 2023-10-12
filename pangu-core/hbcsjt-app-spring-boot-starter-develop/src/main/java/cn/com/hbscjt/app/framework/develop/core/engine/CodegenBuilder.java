package cn.com.hbscjt.app.framework.develop.core.engine;

import cn.com.hbscjt.app.framework.common.util.collection.CollectionUtils;
import cn.com.hbscjt.app.framework.develop.config.CodegenProperties;
import cn.com.hbscjt.app.framework.develop.core.convert.CodegenConvert;
import cn.com.hbscjt.app.framework.develop.core.enums.CodegenColumnListConditionEnum;
import cn.com.hbscjt.app.framework.develop.core.enums.CodegenTemplateTypeEnum;
import cn.com.hbscjt.app.framework.develop.core.pojo.CodegenColumnDO;
import cn.com.hbscjt.app.framework.develop.core.pojo.CodegenTableDO;
import cn.com.hbscjt.app.framework.mybatis.core.base.BaseDO;
import cn.com.hbscjt.app.framework.mybatis.core.props.DatasourceProperties;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.query.SQLQuery;
import com.google.common.collect.Sets;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static cn.hutool.core.text.CharSequenceUtil.*;

/**
 * 代码生成器的 Builder，负责：
 * 1. 将数据库的表 {@link TableInfo} 定义
 * 2. 将数据库的列 {@link TableField} 构定义
 */
@Component
@Slf4j
public class CodegenBuilder {

    /**
     * 多租户编号的字段名
     */
    public static final String TENANT_ID_FIELD = "tenantId";
    /**
     * {@link BaseDO} 的字段
     */
    public static final Set<String> BASE_DO_FIELDS = new HashSet<>();
    /**
     * 字段名与 {@link CodegenColumnListConditionEnum} 的默认映射
     * 注意，字段的匹配以后缀的方式
     */
    private static final Map<String, CodegenColumnListConditionEnum> COLUMN_LIST_OPERATION_CONDITION_MAPPINGS =
            MapUtil.<String, CodegenColumnListConditionEnum>builder()
                    .put("name", CodegenColumnListConditionEnum.LIKE)
                    .put("time", CodegenColumnListConditionEnum.BETWEEN)
                    .put("date", CodegenColumnListConditionEnum.BETWEEN)
                    .build();
    /**
     * 新增操作，不需要传递的字段
     */
    private static final Set<String> CREATE_OPERATION_EXCLUDE_COLUMN = Sets.newHashSet("id");
    /**
     * 修改操作，不需要传递的字段
     */
    private static final Set<String> UPDATE_OPERATION_EXCLUDE_COLUMN = Sets.newHashSet();
    /**
     * 列表操作的条件，不需要传递的字段
     */
    private static final Set<String> LIST_OPERATION_EXCLUDE_COLUMN = Sets.newHashSet("id");
    /**
     * 列表操作的结果，不需要返回的字段
     */
    private static final Set<String> LIST_OPERATION_RESULT_EXCLUDE_COLUMN = Sets.newHashSet();

    static {
        Arrays.stream(ReflectUtil.getFields(BaseDO.class)).forEach(field -> BASE_DO_FIELDS.add(field.getName()));
        BASE_DO_FIELDS.add(TENANT_ID_FIELD);
        // 处理 OPERATION 相关的字段
        CREATE_OPERATION_EXCLUDE_COLUMN.addAll(BASE_DO_FIELDS);
        UPDATE_OPERATION_EXCLUDE_COLUMN.addAll(BASE_DO_FIELDS);
        LIST_OPERATION_EXCLUDE_COLUMN.addAll(BASE_DO_FIELDS);
        LIST_OPERATION_EXCLUDE_COLUMN.remove("createTime"); // 创建时间，还是可能需要传递的
        LIST_OPERATION_RESULT_EXCLUDE_COLUMN.addAll(BASE_DO_FIELDS);
        LIST_OPERATION_RESULT_EXCLUDE_COLUMN.remove("createTime"); // 创建时间，还是需要返回的
    }

    @Resource
    private CodegenProperties codegenProperties;
    @Resource
    private CodegenEngine codegenEngine;
    @Resource
    private DatasourceProperties datasourceProperties;

    private static void generateFile(String content, String filePath) {
        try {
            File file = new File(filePath);
            createFile(file);
            OutputStreamWriter outputStream = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            outputStream.write(content);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断文件是否存在，不存在就创建
     * @param file
     */
    @SneakyThrows
    public static void createFile(File file) {
        if (file.exists()) {
            //递归删除整个文件夹
            FileUtil.clean(file);
            Thread.sleep(1L);
            createMutiFiles(file);
        } else {
            createMutiFiles(file);
        }
    }

    private static void createMutiFiles(File file){
        if (!file.getParentFile().exists()) {
            //创建上级目录
            file.getParentFile().mkdirs();
        }
        try {
            //在上级目录里创建文件
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CodegenTableDO buildTable(TableInfo tableInfo, String author) {
        CodegenTableDO table = CodegenConvert.INSTANCE.convert(tableInfo);
        table.setAuthor(author);
        initTableDefault(table);
        return table;
    }

    /**
     * 初始化 Table 表的默认字段
     *
     * @param table 表定义
     */
    private void initTableDefault(CodegenTableDO table) {
        // 以 system_dept 举例子。moduleName 为 system、businessName 为 dept、className 为 Dept
        // 如果希望以 System 前缀，则可以手动在【代码生成 - 修改生成配置 - 基本信息】，将实体类名称改为 SystemDept 即可
        String tableName = table.getTableName().toLowerCase();
        // 第一步，_ 前缀的前面，作为 module 名字；第二步，moduleName 必须小写；
        table.setModuleName(subBefore(tableName, '_', false).toLowerCase());
        // 第一步，第一个 _ 前缀的后面，作为 module 名字; 第二步，可能存在多个 _ 的情况，转换成驼峰; 第三步，businessName 必须小写；
        table.setBusinessName(toCamelCase(subAfter(tableName, '_', false)).toLowerCase());
        // 驼峰 + 首字母大写；第一步，第一个 _ 前缀的后面，作为 class 名字；第二步，驼峰命名
        table.setClassName(upperFirst(toCamelCase(subAfter(tableName, '_', false))));
        // 去除结尾的表，作为类描述
        table.setClassComment(StrUtil.removeSuffixIgnoreCase(table.getTableComment(), "表"));
        table.setTemplateType(CodegenTemplateTypeEnum.CRUD.getType());
    }

    public List<CodegenColumnDO> buildColumns(List<TableField> tableFields) {
        List<CodegenColumnDO> columns = CodegenConvert.INSTANCE.convertList(tableFields);
        int index = 1;
        for (CodegenColumnDO column : columns) {
            column.setOrdinalPosition(index++);
            // 初始化 Column 列的默认字段
            processColumnOperation(column); // 处理 CRUD 相关的字段的默认值
        }
        return columns;
    }

    private void processColumnOperation(CodegenColumnDO column) {
        // 处理 createOperation 字段
        column.setCreateOperation(!CREATE_OPERATION_EXCLUDE_COLUMN.contains(column.getJavaField())
                && !column.getPrimaryKey()); // 对于主键，创建时无需传递
        // 处理 updateOperation 字段
        column.setUpdateOperation(!UPDATE_OPERATION_EXCLUDE_COLUMN.contains(column.getJavaField())
                || column.getPrimaryKey()); // 对于主键，更新时需要传递
        // 处理 listOperation 字段
        column.setListOperation(!LIST_OPERATION_EXCLUDE_COLUMN.contains(column.getJavaField())
                && !column.getPrimaryKey()); // 对于主键，列表过滤不需要传递
        // 处理 listOperationCondition 字段
        COLUMN_LIST_OPERATION_CONDITION_MAPPINGS.entrySet().stream()
                .filter(entry -> StrUtil.endWithIgnoreCase(column.getJavaField(), entry.getKey()))
                .findFirst().ifPresent(entry -> column.setListOperationCondition(entry.getValue().getCondition()));
        if (column.getListOperationCondition() == null) {
            column.setListOperationCondition(CodegenColumnListConditionEnum.EQ.getCondition());
        }
        // 处理 listOperationResult 字段
        column.setListOperationResult(!LIST_OPERATION_RESULT_EXCLUDE_COLUMN.contains(column.getJavaField()));
    }

    public TableInfo getTable(String name) {
        List<TableInfo> tableInfos = getTableList0(name);
        return CollectionUtils.filterList(tableInfos, tableInfo -> tableInfo.getName().equals(name)).get(0);
    }

    private List<TableInfo> getTableList0(String name) {
        //使用 MyBatis Plus Generator 解析表结构
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder(datasourceProperties.getGen().getUrl(), datasourceProperties.getGen().getUsername(), datasourceProperties.getGen().getPassword())
                .databaseQueryClass(SQLQuery.class)
                .dbQuery(new MySqlQuery())
                .typeConvert(new MySqlTypeConvertCustom()).build();
        StrategyConfig.Builder strategyConfig = new StrategyConfig.Builder();
        if (StrUtil.isNotEmpty(name)) {
            strategyConfig.addInclude(name);
        } else {
            // 移除工作流和定时任务前缀的表名 // TODO 未来做成可配置
            strategyConfig.addExclude("ACT_[\\S\\s]+|QRTZ_[\\S\\s]+|FLW_[\\S\\s]+");
        }
        GlobalConfig globalConfig = new GlobalConfig.Builder().dateType(DateType.ONLY_DATE).build(); // 只使用 LocalDateTime 类型，不使用 LocalDate
        ConfigBuilder builder = new ConfigBuilder(null, dataSourceConfig, strategyConfig.build(),
                null, globalConfig, null);

        // 按照名字排序
        List<TableInfo> tables = builder.getTableInfoList();
        tables.sort(Comparator.comparing(TableInfo::getName));
        return tables;
    }

    //本地生成代码
    public void generationCodes(String tableName) {
        log.info("-----代码生成开始-----");
        TableInfo tableInfo = getTable(tableName);
        String author = codegenProperties.getAuthor();
        CodegenTableDO tableDO = buildTable(tableInfo, author);
        tableDO.setScene(codegenProperties.getScene());
        List<CodegenColumnDO> columnDOS = buildColumns(tableInfo.getFields());
        Map<String, String> codes = codegenEngine.execute(tableDO, columnDOS);
        String prefix = codegenProperties.getDirectory();
        codes.forEach((path, content) -> {
            path = prefix + path;
            generateFile(content, path);
        });
        log.info("-----代码生成结束-----");
    }
}
