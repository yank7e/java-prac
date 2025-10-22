package com.example.finance;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class TransactionAnalyzer {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private TransactionAnalyzer() {}

    public static double calculateTotalBalance(List<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public static int countTransactionsByMonth(List<Transaction> transactions, String monthYear) {
        return (int) transactions.stream()
                .filter(t -> {
                    try {
                        LocalDate date = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                        String transactionMonthYear = date.format(DateTimeFormatter.ofPattern("MM-yyyy"));
                        return transactionMonthYear.equals(monthYear);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .count();
    }

    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparing(Transaction::getAmount))
                .limit(10)
                .collect(Collectors.toList());
    }

    public static Map<String, Double> findMinMaxExpensesInPeriod(List<Transaction> transactions, LocalDate start, LocalDate end) {
        List<Double> expenses = transactions.stream()
                .filter(t -> {
                    try {
                        LocalDate date = LocalDate.parse(t.getDate(), DATE_FORMATTER);
                        return !date.isBefore(start) && !date.isAfter(end) && t.getAmount() < 0;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .map(Transaction::getAmount)
                .toList();

        double minExpense = expenses.stream().min(Comparator.naturalOrder()).orElse(0.0);
        double maxExpense = expenses.stream().max(Comparator.naturalOrder()).orElse(0.0);

        return Map.of("min", minExpense, "max", maxExpense);
    }

    public static Map<String, Double> getExpensesByCategory(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        Transaction::getDescription,
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }

    public static Map<String, Double> getExpensesByMonth(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        t -> {
                            try {
                                return LocalDate.parse(t.getDate(), DATE_FORMATTER)
                                        .format(DateTimeFormatter.ofPattern("MM-yyyy"));
                            } catch (Exception e) {
                                return "Invalid Date";
                            }
                        },
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }
}