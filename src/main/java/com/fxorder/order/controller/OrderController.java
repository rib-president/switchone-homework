package com.fxorder.order.controller;

import com.fxorder.order.dto.OrderListItemResponse;
import com.fxorder.order.dto.OrderRequest;
import com.fxorder.order.dto.OrderResponse;
import com.fxorder.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/list")
    public List<OrderListItemResponse> getOrderList() {
        return orderService.getOrderList();
    }
}