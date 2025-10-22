package com.example.finance;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public abstract class TransactionReportGenerator {

    private TransactionReportGenerator() {}

    public static void printBalanceReport(double totalBalance) {
        System.out.println("--- Звіт: Загальний баланс ---");
        System.out.printf("Загальний баланс: %.2f грн%n%n", totalBalance);
    }

    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("--- Звіт: Кількість транзакцій за місяць ---");
        System.out.printf("Кількість транзакцій за %s: %d%n%n", monthYear, count);
    }

    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("--- Звіт: 10 найбільших витрат ---");
        for (Transaction expense : topExpenses) {
            System.out.printf("%s: %.2f грн%n", expense.getDescription(), expense.getAmount());
        }
        System.out.println();
    }

    public static void printMinMaxExpensesReport(Map<String, Double> minMaxStats, LocalDate start, LocalDate end) {
        System.out.printf("--- Звіт: Мін/макс витрати за період (%s - %s) ---%n", start, end);
        System.out.printf("Найбільша разова витрата: %.2f грн%n", minMaxStats.getOrDefault("min", 0.0));
        System.out.printf("Найменша разова витрата: %.2f грн%n%n", minMaxStats.getOrDefault("max", 0.0));
    }

    public static void printExpenseReportWithVisualization(String title, Map<String, Double> expenseMap) {
        final int SYMBOL_VALUE = 1000;

        System.out.printf("--- %s ---%n", title);
        System.out.printf("(Кожен символ '*' відповідає ~%d грн витрат)%n%n", SYMBOL_VALUE);

        for (Map.Entry<String, Double> entry : expenseMap.entrySet()) {
            double amount = entry.getValue();
            long starsCount = Math.round(Math.abs(amount) / SYMBOL_VALUE);
            String stars = "*".repeat((int) starsCount);

            System.out.printf("%-20s | %s (%.2f грн)%n", entry.getKey(), stars, amount);
        }
        System.out.println();
    }
}