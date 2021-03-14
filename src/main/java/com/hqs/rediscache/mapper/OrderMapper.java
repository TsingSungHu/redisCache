package com.hqs.rediscache.mapper;

import com.hqs.rediscache.entity.Order;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderMapper {

    @Select("insert into t_order (id,name) values (#{id},#{name})")
    Integer insertOrder(Order order);

    @Select("select * from t_order where id=#{id}")
    Order selectOrderById(Integer id);

    @Select("select * from t_order")
    List<Order> selectOrderyAll();
}
