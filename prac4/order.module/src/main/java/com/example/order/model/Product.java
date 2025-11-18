package com.example.order.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class Product {
    private String name;
    private double price;
}