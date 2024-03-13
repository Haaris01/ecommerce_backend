package com.orderservice.service;

import com.orderservice.dto.InventoryResponse;
import com.orderservice.dto.OrderLineItemsDto;
import com.orderservice.dto.OrderRequest;
import com.orderservice.model.Order;
import com.orderservice.model.OrderLineItems;
import com.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service

public class OrderService
{
    @Autowired
    private  OrderRepository orderRepository;
    @Autowired
    private WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems =  orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(orderLineItem -> {
                    return orderLineItem.getSkuCode();
                }).toList();

        InventoryResponse[] inventoryResponseArray = webClient.get()
                                    .uri("http://localhost:8081/api/inventory",
                                            uriBuilder ->
                                                    uriBuilder.queryParam("skuCode", skuCodes)
                                                            .build())
                                    .retrieve()
                                    .bodyToMono(InventoryResponse[].class)
                                    .block();

        Boolean result = Arrays.stream(inventoryResponseArray)
                .allMatch(inventoryResponse -> inventoryResponse.getIsInStock());

        if(result)
            orderRepository.save(order);
        else{
            throw new IllegalArgumentException("The item is not in stock please try again later");
        }
    }
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto)
    {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;

    }

}
