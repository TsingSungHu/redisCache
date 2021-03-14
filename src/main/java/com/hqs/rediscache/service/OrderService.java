package com.hqs.rediscache.service;

import com.hqs.rediscache.entity.Order;
import com.hqs.rediscache.entity.R;

import java.util.List;

public interface OrderService {
    Integer insertOrder(Order order);

    R selectOrderById(Integer id);

    List<Order> selectOrderyAll();

    R synchronizedSelectOrderById(Integer id);
}
