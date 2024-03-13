package com.orderservice.controller;

import com.orderservice.dto.OrderRequest;
import com.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
//        System.out.println(orderRequest);
        orderService.placeOrder(orderRequest);
        return "order placed ";
    }
}
