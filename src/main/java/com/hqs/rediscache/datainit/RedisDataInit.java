package com.hqs.rediscache.datainit;

import com.hqs.rediscache.entity.Order;
import com.hqs.rediscache.filter.RedisBloomFilter;
import com.hqs.rediscache.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class RedisDataInit {

    @Autowired
    OrderService orderService;

    @Autowired
    RedisBloomFilter redisBloomFilter;

    @PostConstruct
    public void init(){
        List<Order> orders = orderService.selectOrderyAll();
        for (Order order : orders) {
            redisBloomFilter.put(String.valueOf(order.getId()));
        }
    }
}
