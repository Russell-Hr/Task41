package com.example.Task41.service;

import com.example.Task41.pojo.Order;
import org.springframework.stereotype.Service;

@Service("aService")
public class A {
    public Order process(Order order) {
        System.out.println("A is processing order: " + order.getPrice());
        return order;
    }
}
