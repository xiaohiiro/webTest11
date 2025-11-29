package cn.edu.sju.ws.orderservice.service;

import cn.edu.sju.ws.orderservice.mapper.OrderMapper;
import cn.edu.sju.ws.orderservice.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        return order;
    }
}
