package com.laishiji.miaosha.config;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Component;

@Component
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600) //设置session失效时间，默认30min，修改为1h
public class RedisConfig {
}
