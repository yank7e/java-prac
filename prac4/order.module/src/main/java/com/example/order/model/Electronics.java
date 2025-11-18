package com.example.order.model;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
public class Electronics extends Product {
    private int warrantyPeriod;
}