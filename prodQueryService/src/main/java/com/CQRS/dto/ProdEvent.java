package com.CQRS.dto;

import com.CQRS.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdEvent {
    private String eventType;
    private Product prod;
}
