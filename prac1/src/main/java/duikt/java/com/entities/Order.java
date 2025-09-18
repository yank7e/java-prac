package duikt.java.com.entities;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {

    private static final AtomicInteger COUNTER = new AtomicInteger(1);

    @Getter
    private final int id;
    @Getter
    private final List<Product> products;
    @Getter
    private final double totalPrice;
    @Getter
    private final LocalDateTime createdAt;

    public Order(Cart cart) {
        this.id = COUNTER.getAndIncrement();
        this.products = new ArrayList<>(cart.getProducts());
        double sum = 0.0;
        for (Product p : products) sum += p.getPrice();
        this.totalPrice = sum;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        sb.append("Замовлення #").append(id).append(" від ").append(createdAt.format(fmt)).append("\n");
        for (Product p : products) sb.append(p).append("\n");
        sb.append("Сума: ").append(totalPrice);
        return sb.toString();
    }
}
