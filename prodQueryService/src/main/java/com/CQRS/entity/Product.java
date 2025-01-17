package com.CQRS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PRODUCT_QUERY")
public class Product {
    @Id 
    /* @GeneratedValue */                       //@GeneratedValue shouldn't be used in id field as it will conflict with producer entity id field
    private long id;
    private String name;
    private double price;
    @Version
    private int version;                   //version is maintained to ensure how many times same row updated
}
