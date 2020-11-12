package com.laishiji.miaosha.config;

//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600) //设置session失效时间，默认30min，修改为1h
public class RedisConfig {
//    /**
//     * 自定义RedisTemplate,不使用RedisAutoConfiguration配置的默认RedisTemplate
//     * @param factory
//     * @return
//     */
//    @Bean
//    public RedisTemplate redisTemplate(RedisConnectionFactory factory){
//        RedisTemplate redisTemplate = new RedisTemplate();
//        redisTemplate.setConnectionFactory(factory);
//
//        //更改key的序列化方式
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        redisTemplate.setKeySerializer(stringRedisSerializer);
//
//        //更改Object的序列化方式，使用JSON
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//
//        return redisTemplate;
//    }
}
