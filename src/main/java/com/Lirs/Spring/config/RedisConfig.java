package com.Lirs.Spring.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Bean(name = "redisTemplate")
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        //配置连接工厂
        template.setConnectionFactory(factory);
        //使用jackson序列化和反序列value的值，
        Jackson2JsonRedisSerializer jacksonSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        //指定需要序列化的范围，All表示field、get和set，以及修饰符范围，ANY表示所有范围，包括private
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jacksonSerializer.setObjectMapper(mapper);
        //设置template中value使用Jackson2JsonRedisSerializer序列化
        template.setValueSerializer(jacksonSerializer);
        //设置template中key使用StringRedisSerializer序列化
        template.setKeySerializer(new StringRedisSerializer());
        //这是hash中key和value的序列化方式,key采用StringRedisSerializer，value采用Jackson2JsonRedisSerializer
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jacksonSerializer);
        //使template属性生效
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 自定义缓存key生成策略
     * @return
     */
    @Bean
    public KeyGenerator keyGenertor(){
        return new KeyGenerator(){
            public Object generate(Object target,java.lang.reflect.Method method, Object... params){
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for(Object obj: params){
                    sb.append(obj.toString());
                }
                return sb;
            }
        };
    }

    /**
     * 缓存管理器
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory){
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext
                    .SerializationPair
                    .fromSerializer(new GenericJackson2JsonRedisSerializer()));
        return RedisCacheManager.builder(factory).cacheDefaults(configuration).build();
    }


}
