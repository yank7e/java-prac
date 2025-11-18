package com.example.order.processing;
import com.example.order.model.Product;

public class OrderProcessor<T extends Product> {
    private final T product;

    public OrderProcessor(T product) {
        this.product = product;
    }

    public void process() {
        System.out.println("Processing order: " + product.toString());
    }

    public T getProduct() {
        return product;
    }
}
