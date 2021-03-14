package com.hqs.rediscache.service.impl;

import com.hqs.rediscache.entity.NullValueResultDO;
import com.hqs.rediscache.entity.Order;
import com.hqs.rediscache.entity.R;
import com.hqs.rediscache.filter.RedisBloomFilter;
import com.hqs.rediscache.mapper.OrderMapper;
import com.hqs.rediscache.service.OrderService;
import com.hqs.rediscache.template.CacheLoadble;
import com.hqs.rediscache.template.CacheTemplate;
import com.hqs.rediscache.util.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

//@Transactional
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ValueOperations valueOperations;

    @Autowired
    CacheTemplate cacheTemplate;

    @Autowired
    RedisBloomFilter bloomFilter;


    @Autowired
    private RedisLock redisLock;



    @Override
    public Integer insertOrder(Order order) {
        Integer integer = orderMapper.insertOrder(order);
        return integer;
    }

//    @Override
//    public R selectOrderById(Integer id) {
//        return cacheTemplate.redisFindCache(String.valueOf(id), 10, TimeUnit.MINUTES, new CacheLoadble<Order>() {
//            @Override
//            public Order load() {
//                return orderMapper.selectOrderById(id);
//            }
//        },false);
//    }



//    @Override
//    public  R selectOrderById(Integer id) {
//       if(!bloomFilter.isExist(String.valueOf(id))){
//           return new R().setMsg("查询无果").setData(new NullValueResultDO()).setCode(600);
//       }
//        //查询缓存
//        Object redisObj = valueOperations.get(String.valueOf(id));
//
//        //命中缓存
//        if(redisObj != null) {
//            //正常返回数据
//            return new R().setCode(200).setData(redisObj).setMsg("OK");
//        }
//        Order order = orderMapper.selectOrderById(id);
//        if (order != null) {
//            valueOperations.set(String.valueOf(id), order);  //加入缓存
//            return new R().setCode(200).setData(order).setMsg("OK");
//        }
//        return new R().setCode(500).setData(new NullValueResultDO()).setMsg("查询无果");
//    }


    @Override
    public  R selectOrderById(Integer id) {
        //查询缓存
        Object redisObj = valueOperations.get(String.valueOf(id));
        //命中缓存
        if(redisObj != null) {
            //正常返回数据
            return new R().setCode(200).setData(redisObj).setMsg("OK");
        }
        try {
                redisLock.lock(String.valueOf(id));
//                查询缓存
                redisObj = valueOperations.get(String.valueOf(id));
//                命中缓存
                if(redisObj != null) {
                    //正常返回数据
                    return new R().setCode(200).setData(redisObj).setMsg("OK");
                }
                Order order = orderMapper.selectOrderById(id); //查数据库
                if (order != null) {
                    valueOperations.set(String.valueOf(id), order);  //加入缓存
                    return new R().setCode(200).setData(order).setMsg("OK");
                }
        }finally {
            redisLock.unlock(String.valueOf(id));
        }
        return new R().setCode(500).setData(new NullValueResultDO()).setMsg("查询无果");
    }

    public R synchronizedSelectOrderById(Integer id) {
        return cacheTemplate.findCache(String.valueOf(id), 10, TimeUnit.MINUTES, new CacheLoadble<Order>() {
            @Override
            public Order load() {
                return orderMapper.selectOrderById(id);
            }
        });
    }


    @Override
    public List<Order> selectOrderyAll() {
        return orderMapper.selectOrderyAll();
    }
}
