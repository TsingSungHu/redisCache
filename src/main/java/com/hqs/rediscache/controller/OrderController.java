package com.hqs.rediscache.controller;

import com.hqs.rediscache.entity.NullValueResultDO;
import com.hqs.rediscache.entity.Order;
import com.hqs.rediscache.entity.R;
import com.hqs.rediscache.filter.RedisBloomFilter;
import com.hqs.rediscache.service.OrderService;
import com.hqs.rediscache.util.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCommands;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/test")
    public Integer insertOrder(Order order){
        return orderService.insertOrder(order);
    }


    @GetMapping("/selectid")
    public R selectOrderById(Integer id){
        return orderService.selectOrderById(id);
    }
}
