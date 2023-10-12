package cn.com.hbscjt.app.framework.swagger.config;

import cn.com.hbscjt.app.framework.common.constant.SystemConstant;
import cn.com.hbscjt.app.framework.common.util.collection.CollectionUtils;
import cn.com.hbscjt.app.framework.swagger.core.props.SwaggerProperties;
import cn.hutool.core.util.ReflectUtil;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Swagger2 自动配置类
 */
@Configuration
@Slf4j
@EnableSwagger2
@EnableKnife4j
@ConditionalOnClass({Docket.class, ApiInfoBuilder.class})
@Import(BeanValidatorPluginsConfiguration.class)//导入其他的配置类 让配置生效
@ConditionalOnProperty(name = "boot.web.swagger.enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfiguration implements WebMvcConfigurer {

    /**
     * 解决与knife4j有兼容问题
     * https://github.com/xiaoymin/swagger-bootstrap-ui/issues/396
     * https://github.com/springfox/springfox/issues/3462
     *
     * @return
     */
    @Bean
    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                // 移除，只保留 patternParser
                List<T> copy = CollectionUtils.filterList(mappings, mapping -> mapping.getPatternParser() == null);
                // 添加到 mappings 中
                mappings.clear();
                mappings.addAll(copy);
            }

            @SuppressWarnings("unchecked")
            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                return (List<RequestMappingInfoHandlerMapping>)
                        ReflectUtil.getFieldValue(bean, "handlerMappings");
            }
        };
    }

    private static ApiInfo apiInfo(SwaggerProperties properties) {
        return new ApiInfoBuilder()
                .title(properties.getTitle())
                .description(properties.getDescription())
                .contact(new Contact(properties.getAuthor(), null, null))
                .version(properties.getVersion())
                .build();
    }

    /**
     * 安全模式，这里配置通过请求头 Authorization 传递 token 参数
     */
    private static List<SecurityScheme> securitySchemes() {
        return Collections.singletonList(new ApiKey(HttpHeaders.AUTHORIZATION, "Authorization", "header"));
    }

    /**
     * 安全上下文
     *
     * @see #securitySchemes()
     * @see #authorizationScopes()
     */
    private static List<SecurityContext> securityContexts() {
        return Collections.singletonList(SecurityContext.builder()
                .securityReferences(securityReferences())
                //通过 PathSelectors.regex("^(?!auth).*$")，排除包含 "auth" 的接口不需要使用securitySchemes
                .operationSelector(o -> o.requestMappingPattern().matches("^(?!auth).*$"))
                .build());
    }

    private static List<SecurityReference> securityReferences() {
        return Collections.singletonList(new SecurityReference(HttpHeaders.AUTHORIZATION, authorizationScopes()));
    }

    private static AuthorizationScope[] authorizationScopes() {
        return new AuthorizationScope[]{new AuthorizationScope("global", "accessEverything")};
    }

    // ========== globalRequestParameters ==========
    private static List<RequestParameter> globalRequestParameters() {
        RequestParameterBuilder tokenParameter = new RequestParameterBuilder()
                .name(SystemConstant.AUTH_TOKEN).description("系统token")
                .in(ParameterType.HEADER).example(new ExampleBuilder().value(1L).build());
        RequestParameterBuilder sourceParameter = new RequestParameterBuilder()
                .name(SystemConstant.SOURCE).description("来源")
                .in(ParameterType.HEADER).example(new ExampleBuilder().value("app").build());
        RequestParameterBuilder versionParameter = new RequestParameterBuilder()
                .name(SystemConstant.VERSION).description("版本号")
                .in(ParameterType.HEADER).example(new ExampleBuilder().value("1.0.0").build());
        List<RequestParameter> pars=new ArrayList<>();
        pars.add(tokenParameter.build());
        pars.add(sourceParameter.build());
        pars.add(versionParameter.build());
        return pars;
    }

    //解决knife4j 404访问问题
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/");
        log.info("-----初始化swagger配置完成-----");
    }

    @Bean
    public Docket createRestApi(SwaggerProperties properties) {
        ApiSelectorBuilder apiSelectorBuilder = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(properties))
                .select();
        if (properties.getBasePackage() == null) {
            apiSelectorBuilder.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));
        } else {
            apiSelectorBuilder.apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()));
        }

        return apiSelectorBuilder.paths(PathSelectors.any())
                .paths(PathSelectors.regex("/.*/error").negate())//错误路径不监控
                .build()
                .enable(properties.getEnabled())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .globalRequestParameters(globalRequestParameters());
    }
}
