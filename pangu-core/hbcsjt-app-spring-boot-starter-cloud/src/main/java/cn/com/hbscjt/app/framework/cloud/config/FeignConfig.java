package cn.com.hbscjt.app.framework.cloud.config;

import cn.com.hbscjt.app.framework.cloud.core.rpc.HeaderInterceptor;
import feign.*;
import feign.codec.ErrorDecoder;
import feign.slf4j.Slf4jLogger;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static feign.FeignException.errorStatus;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author : ly
 **/

@AutoConfigureAfter(CloudAutoConfiguration.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
@Configuration(proxyBeanMethods = false)
public class FeignConfig{

    @Bean
    public OkHttpClient okHttpClient(){
        return new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                //自动重连
                .retryOnConnectionFailure(true)
                .connectionPool(pool())
                .build();
    }


    public ConnectionPool pool() {
        // 最大连接数、连接存活时间、存活时间单位（分钟）
        return new ConnectionPool(200, 5, TimeUnit.MINUTES);
    }


    @Bean
    Logger.Level feignLevel(){
        return Logger.Level.FULL;
    }

    @Bean
    public feign.Logger logger() {
        return new Slf4jLogger();
    }

    @Bean
    public HeaderInterceptor headerInterceptor() {
        return new HeaderInterceptor();
    }

    @Bean
    public ErrorDecoder feignError() {
        return (key, response) -> {
            FeignException exception = errorStatus(key, response);
            if (response.request().httpMethod() == Request.HttpMethod.GET) {
                return new RetryableException(
                        response.status(),
                        exception.getMessage(),
                        response.request().httpMethod(),
                        exception,
                        new Date(),
                        response.request());
            }
            return new ErrorDecoder.Default().decode(key, response);
        };
    }

    @Bean
    public Retryer retryer(){
        return new Retryer.Default(200,SECONDS.toMillis(1),3);
    }
}
