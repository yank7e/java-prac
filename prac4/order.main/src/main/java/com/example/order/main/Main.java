package com.example.order.main;

import com.example.order.model.Clothing;
import com.example.order.model.Electronics;
import com.example.order.processing.OrderProcessor;
import com.example.order.storage.OrderRepository;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Faker faker = new Faker();
        OrderRepository repository = new OrderRepository();
        List<OrderProcessor<?>> processors = new ArrayList<>();

        System.out.println("Generating Orders");

        for (int i = 0; i < 5; i++) {
            Electronics electronics = Electronics.builder()
                    .name(faker.commerce().productName())
                    .price(Double.parseDouble(faker.commerce().price().replace(",", ".")))
                    .warrantyPeriod(faker.number().numberBetween(12, 36))
                    .build();

            Clothing clothing = Clothing.builder()
                    .name(faker.commerce().productName())
                    .price(Double.parseDouble(faker.commerce().price().replace(",", ".")))
                    .size(faker.options().option("S", "M", "L", "XL"))
                    .build();

            processors.add(new OrderProcessor<>(electronics));
            processors.add(new OrderProcessor<>(clothing));
        }

        System.out.println("Generated " + processors.size() + " orders ready for processing.\n");

        System.out.println("Processing in Threads");
        ExecutorService executor = Executors.newFixedThreadPool(3);

        processors.forEach(processor -> {
            executor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("[" + threadName + "] Processing: " + processor.getProduct().getName());

                processor.process();
                repository.add(processor);

                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        });

        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        System.out.println("\nFinal Report");
        repository.getOrders().stream()
                .map(OrderProcessor::getProduct)
                .forEach(System.out::println);
    }
}