package cn.com.mw.app.framework.redis.config;

import cn.com.mw.app.framework.redis.core.RedisProperties;
import cn.com.mw.app.framework.redis.core.RedisService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.text.SimpleDateFormat;
import java.util.Date;

import static cn.com.mw.app.framework.common.util.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * Redis基础配置类
 */
@Configuration
@ConditionalOnProperty(name = "boot.web.redis.enabled", havingValue = "true")
@EnableConfigurationProperties(RedisProperties.class)
@Slf4j
public class RedisConfiguration {

    @Bean
    public RedisSerializer<String> redisKeySerializer() {
        return RedisSerializer.string();
    }

    @Bean
    public Jackson2JsonRedisSerializer<Object> redisValueSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        //日期序列化处理
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(Date.class, new DateDeserializers.DateDeserializer(DateDeserializers.DateDeserializer.instance, new SimpleDateFormat(FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND), FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND));
        timeModule.addSerializer(Date.class,new DateSerializer(false,new SimpleDateFormat(FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)));
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(timeModule);
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

    @SuppressWarnings("all")
    @Bean(name = "redisTemplate")
    @ConditionalOnClass(RedisOperations.class)
    public RedisTemplate redisTemplate(LettuceConnectionFactory factory, RedisSerializer<String> redisKeySerializer, Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // key 序列化
        redisTemplate.setKeySerializer(redisKeySerializer);
        redisTemplate.setHashKeySerializer(redisKeySerializer);
        // value 序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setConnectionFactory(factory);
        log.info("-----初始化lettuce配置完成-----");
        return redisTemplate;
    }

    @Primary
    @Bean
    @ConditionalOnBean(RedisConnectionFactory.class)
    public LettuceConnectionFactory LettuceConnectionFactory(RedisConnectionFactory redisConnectionFactory){
        LettuceConnectionFactory lettuceConnectionFactory=(LettuceConnectionFactory)redisConnectionFactory;
        lettuceConnectionFactory.setValidateConnection(true);
        return lettuceConnectionFactory;
    }

    @Bean
    @ConditionalOnBean(name = "redisTemplate")
    public RedisService redisService(RedisTemplate<String, Object> redisTemplate) {
        return new RedisService(redisTemplate);
    }

}
