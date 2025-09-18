package duikt.java.com;

import duikt.java.com.entities.Cart;
import duikt.java.com.entities.Category;
import duikt.java.com.entities.Order;
import duikt.java.com.entities.Product;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Category electronics = new Category(1, "Електроніка");
        Category smartphones = new Category(2, "Смартфони");
        Category accessories = new Category(3, "Аксесуари");

        Product product1 = new Product(1, "Ноутбук", 19999.99, "Високопродуктивний ноутбук для роботи та ігор", electronics);
        Product product2 = new Product(2, "Смартфон", 12999.50, "Смартфон з великим екраном…", smartphones);
        Product product3 = new Product(3, "Навушники", 2499.00, "Бездротові навушники з шумозаглушенням", accessories);

        List<Product> catalog = Arrays.asList(product1, product2, product3);

        Cart cart = new Cart();

        List<Order> orderHistory = new ArrayList<>();
        Path historyFile = Path.of("order_history.txt");

        while (true) {
            System.out.println("\nВиберіть опцію:");
            System.out.println("1 - Переглянути список товарів");
            System.out.println("2 - Додати товар до кошика");
            System.out.println("3 - Переглянути кошик");
            System.out.println("4 - Зробити замовлення");
            System.out.println("5 - Видалити товар з кошика");
            System.out.println("6 - Переглянути історію замовлень");
            System.out.println("7 - Пошук товарів (за назвою або категорією)");
            System.out.println("0 - Вийти");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println(product1);
                    System.out.println(product2);
                    System.out.println(product3);
                    break;
                case 2:
                    System.out.println("Введіть ID товару для додавання до кошика:");
                    int id = scanner.nextInt();
                    if (id == 1) cart.addProduct(product1);
                    else if (id == 2) cart.addProduct(product2);
                    else if (id == 3) cart.addProduct(product3);
                    else System.out.println("Товар з таким ID не знайдено");
                    break;
                case 3:
                    System.out.println(cart);
                    break;
                case 4:
                    if (cart.getProducts().isEmpty()) {
                        System.out.println("Кошик порожній. Додайте товари перед оформленням замовлення.");
                    } else {
                        Order order = new Order(cart);
                        orderHistory.add(order);
                        System.out.println("Замовлення оформлено:");
                        System.out.println(order);
                        try {
                            Files.writeString(
                                    historyFile,
                                    order.toString() + System.lineSeparator() + "------------------------" + System.lineSeparator(),
                                    StandardOpenOption.CREATE, StandardOpenOption.APPEND
                            );
                        } catch (Exception e) {
                            System.out.println("Не вдалося зберегти історію у файл: " + e.getMessage());
                        }
                        cart.clear();
                    }
                    break;
                case 5:
                    if (cart.getProducts().isEmpty()) {
                        System.out.println("Кошик порожній.");
                    } else {
                        System.out.println(cart);
                        System.out.println("Введіть ID товару для видалення з кошика:");
                        int removeId = scanner.nextInt();
                        boolean removed = cart.removeProductById(removeId);
                        if (removed) System.out.println("Товар видалено з кошика.");
                        else System.out.println("У кошику немає товару з таким ID.");
                    }
                    break;
                case 6:
                    if (orderHistory.isEmpty()) {
                        System.out.println("Історія замовлень порожня.");
                    } else {
                        System.out.println("Історія замовлень:");
                        for (Order o : orderHistory) {
                            System.out.println(o);
                            System.out.println("------------------------");
                        }
                    }
                    break;
                case 7:
                    System.out.println("Оберіть тип пошуку:");
                    System.out.println("1 - За назвою");
                    System.out.println("2 - За категорією");
                    int searchType = scanner.nextInt();
                    scanner.nextLine();

                    if (searchType == 1) {
                        System.out.println("Введіть частину назви товару:");
                        String q = scanner.nextLine().toLowerCase();
                        List<Product> found = new ArrayList<>();
                        for (Product p : catalog) {
                            if (p.getName().toLowerCase().contains(q)) {
                                found.add(p);
                            }
                        }
                        if (found.isEmpty()) {
                            System.out.println("Нічого не знайдено.");
                        } else {
                            System.out.println("Знайдені товари:");
                            for (Product p : found) System.out.println(p);
                        }
                    } else if (searchType == 2) {
                        java.util.LinkedHashMap<Integer, String> cats = new java.util.LinkedHashMap<>();
                        for (Product p : catalog) {
                            if (p.getCategory() != null) {
                                cats.put(p.getCategory().getId(), p.getCategory().getName());
                            }
                        }
                        if (cats.isEmpty()) {
                            System.out.println("Категорій поки немає.");
                            break;
                        }
                        System.out.println("Доступні категорії:");
                        for (var e : cats.entrySet()) {
                            System.out.println(e.getKey() + " - " + e.getValue());
                        }

                        System.out.println("Введіть назву або ID категорії:");
                        String input = scanner.nextLine().trim();

                        Integer idOrNull = null;
                        try { idOrNull = Integer.parseInt(input); } catch (NumberFormatException ignore) {}

                        java.util.List<Product> found = new java.util.ArrayList<>();
                        for (Product p : catalog) {
                            if (p.getCategory() == null) continue;
                            boolean byId = (idOrNull != null) && p.getCategory().getId() == idOrNull;
                            boolean byName = p.getCategory().getName() != null
                                    && p.getCategory().getName().equalsIgnoreCase(input);
                            if (byId || byName) found.add(p);
                        }

                        if (found.isEmpty()) {
                            System.out.println("У цій категорії товарів не знайдено.");
                        } else {
                            System.out.println("Знайдені товари:");
                            for (Product p : found) System.out.println(p);
                        } System.out.println("Невідомий тип пошуку.");
                    }
                    break;
                case 0:
                    System.out.println("Дякуємо, що використовували наш магазин!");
                    return;
                default:
                    System.out.println("Невідома опція. Спробуйте ще раз.");
                    break;
            }
        }

    }
}
