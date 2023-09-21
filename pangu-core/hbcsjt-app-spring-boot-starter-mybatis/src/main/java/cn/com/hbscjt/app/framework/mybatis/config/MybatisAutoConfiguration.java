package cn.com.hbscjt.app.framework.mybatis.config;

import cn.com.hbscjt.app.framework.common.context.TenantContextHolder;
import cn.com.hbscjt.app.framework.common.factory.YamlPropertySourceFactory;
import cn.com.hbscjt.app.framework.mybatis.core.handler.DefaultDBFieldHandler;
import cn.com.hbscjt.app.framework.mybatis.core.interceptor.SqlLogInterceptor;
import cn.com.hbscjt.app.framework.mybatis.core.props.MybatisProperties;
import cn.com.hbscjt.app.framework.mybatis.core.props.TenantProperties;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBaits 配置类
 */
@Configuration
@MapperScan(value = "cn.com.hbscjt.**.dal.mysql.**", annotationClass = Mapper.class)
@RequiredArgsConstructor
@EnableConfigurationProperties({MybatisProperties.class,TenantProperties.class})
@EnableTransactionManagement(proxyTargetClass = true)//启动事务管理
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:mb.yml")
public class MybatisAutoConfiguration {

    private final TenantProperties tenantProperties;

    /**
     * 单页分页条数限制(默认无限制,参见 插件#handlerLimit 方法)
     */
    private static final Long MAX_LIMIT = 1000L;

    /**
     * 覆盖原框架的mybatis-plus
     */
    @Bean
    public TenantLineInnerInterceptor tenantLineInnerInterceptor() {
        return new TenantLineInnerInterceptor(new TenantLineHandler() {
            /**
             * 获取租户ID
             * @return Expression
             */
            @Override
            public Expression getTenantId() {
                String tenant = TenantContextHolder.getTenantId();
                if (tenant != null) {
                    return new StringValue(TenantContextHolder.getTenantId());
                }
                return new NullValue();
            }

            /**
             * 获取多租户的字段名
             * @return String
             */
            @Override
            public String getTenantIdColumn() {
                return tenantProperties.getColumn();
            }

            /**
             * 过滤不需要根据租户隔离的表
             * 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
             * @param tableName 表名
             */
            @Override
            public boolean ignoreTable(String tableName) {
                return tenantProperties.getIgnoreTables().stream().anyMatch(
                        (t) -> t.equalsIgnoreCase(tableName)
                );
            }
        });
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(TenantLineInnerInterceptor tenantLineInnerInterceptor) {
        boolean enableTenant = tenantProperties.getEnable();
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        if (enableTenant) {
            interceptor.addInnerInterceptor(tenantLineInnerInterceptor);
        }
        //分页插件: PaginationInnerInterceptor
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setMaxLimit(MAX_LIMIT);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

    @Bean
    public MySqlInjector sqlInjector() {
        return new MySqlInjector();
    }

    /**
     * sql 日志
     */
    @Bean
    @ConditionalOnProperty(name = "boot.mybatis.info.sql", matchIfMissing = true)
    public SqlLogInterceptor sqlLogInterceptor() {
        return new SqlLogInterceptor();
    }

    @Bean
    public MetaObjectHandler defaultMetaObjectHandler(){
        return new DefaultDBFieldHandler(); // 自动填充参数类
    }
}
