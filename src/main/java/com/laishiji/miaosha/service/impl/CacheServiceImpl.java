package com.laishiji.miaosha.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.laishiji.miaosha.service.CacheService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Service("cacheService")
public class CacheServiceImpl implements CacheService {

    private Cache<String,Object> commonCache = null;

    //cacheService生命周期中的初始化方法
    @PostConstruct
    public void init(){
        commonCache = CacheBuilder.newBuilder()
                .initialCapacity(10)//设置缓存容器的初始容量为10
                .maximumSize(100)//设置缓存最多100个key，超过后按照LRU算法移除缓存项
                .expireAfterWrite(30, TimeUnit.SECONDS)//设置写缓存后多少秒过期
                .build();
    }

    @Override
    public void setCommonCache(String key, Object value) {
        commonCache.put(key,value);
    }

    @Override
    public Object getFromCommonCache(String key) {
        return commonCache.getIfPresent(key);
    }
}
