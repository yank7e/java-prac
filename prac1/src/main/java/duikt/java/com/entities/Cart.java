package duikt.java.com.entities;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cart {
    @Getter
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product != null) {
            products.add(product);
        }
    }

    public boolean removeProductById(int id) {
        Iterator<Product> it = products.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public double getTotalPrice() {
        double sum = 0.0;
        for (Product p : products) {
            sum += p.getPrice();
        }
        return sum;
    }

    @Override
    public String toString() {
        if (products.isEmpty()) {
            return "Кошик порожній.";
        }
        StringBuilder sb = new StringBuilder("Кошик містить:\n");
        for (Product product : products) {
            sb.append(product).append("\n");
        }
        sb.append("Загальна вартість: ").append(getTotalPrice());
        return sb.toString();
    }

    public void clear() {
        products.clear();
    }
}
