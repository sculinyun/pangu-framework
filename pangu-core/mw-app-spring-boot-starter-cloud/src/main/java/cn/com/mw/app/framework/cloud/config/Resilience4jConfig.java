package cn.com.mw.app.framework.cloud.config;

import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.common.bulkhead.configuration.BulkheadConfigCustomizer;
import io.github.resilience4j.common.ratelimiter.configuration.RateLimiterConfigCustomizer;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author : ly
 **/
@Configuration(proxyBeanMethods = false)
public class Resilience4jConfig {

    @Bean
    public ReactiveResilience4JCircuitBreakerFactory defaultCustomizer() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom() //
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED) // 滑动窗口的类型为时间窗口
                .slidingWindowSize(60) // 时间窗口的大小为60秒
                .minimumNumberOfCalls(5) // 在单位时间窗口内最少需要5次调用才能开始进行统计计算
                .failureRateThreshold(50) // 在单位时间窗口内调用失败率达到50%后会启动断路器
                .enableAutomaticTransitionFromOpenToHalfOpen() // 允许断路器自动由打开状态转换为半开状态
                .permittedNumberOfCallsInHalfOpenState(5) // 在半开状态下允许进行正常调用的次数
                .waitDurationInOpenState(Duration.ofSeconds(60)) // 断路器打开状态转换为半开状态需要等待60秒
                .recordExceptions(Throwable.class) // 所有异常都当作失败来处理
                .build();
        ReactiveResilience4JCircuitBreakerFactory factory = new ReactiveResilience4JCircuitBreakerFactory();
        factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(60000)).build())
                .circuitBreakerConfig(circuitBreakerConfig).build());
        return factory;
    }

    //Bulkhead配置
    @Bean
    public Customizer<BulkheadConfigCustomizer> bulkheadConfigCustomizerCustomizer() {
        return provider -> provider.customize(new BulkheadConfig.Builder()
                .maxConcurrentCalls(10));
    }

    //限流配置
    @Bean
    public Customizer<RateLimiterConfigCustomizer> rateLimiterConfigCustomizerCustomizer() {
        return provider ->provider.customize(new RateLimiterConfig.Builder()
                .limitForPeriod(50)
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .timeoutDuration(Duration.ofSeconds(5))
                .writableStackTraceEnabled(true)
        );
    }
}
