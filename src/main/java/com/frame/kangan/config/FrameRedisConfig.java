package com.frame.kangan.config;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/** 
* @ClassName: FrameRedisConfig 

* @Description: TODO(这里用一句话描述这个类的作用) 

* @author kang.an@ele.me

* @date 2016年5月31日 下午3:18:47 
*  
*/
@Configuration
@PropertySource("classpath:application.properties")
public class FrameRedisConfig extends CachingConfigurerSupport{
	
	@Value("${redis.host}")
    private String redisHostName;

    @Value("${redis.port}")
    private int redisPort;

    @Bean  
    public KeyGenerator wiselyKeyGenerator(){  
        return new KeyGenerator() {  
            @Override  
            public Object generate(Object target, Method method, Object... params) {  
                StringBuilder sb = new StringBuilder();  
                sb.append(target.getClass().getName());  
                sb.append(method.getName());  
                for (Object obj : params) {  
                    sb.append(obj.toString());  
                }  
                return sb.toString();  
            }  
        };  
  
    }  
    
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer    propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(redisHostName);
        factory.setPort(redisPort);
        factory.setUsePool(true);
        return factory;
    }

    @Bean
    RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
	public  RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate());
        return redisCacheManager;
    }

    
    
}