package com.inventoryservice.service;

import com.inventoryservice.dto.InventoryResponse;
import com.inventoryservice.repository.InventoryRepostitory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class InventoryService {
    @Autowired
    private InventoryRepostitory inventoryRepostitory;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode){
        return inventoryRepostitory.findBySkuCodeIn(skuCode)
                .stream()
                .map(inventory -> {
                    return InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity() > 0)
                            .build();
                }).toList();
    }
}
