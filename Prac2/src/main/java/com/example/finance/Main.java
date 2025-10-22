package com.example.finance;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        String filePath = "https://informer.com.ua/dut/java/pr2.csv";
        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);


        double totalBalance = TransactionAnalyzer.calculateTotalBalance(transactions);

        String monthYear = "12-2023";
        int transactionsCount = TransactionAnalyzer.countTransactionsByMonth(transactions, monthYear);

        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);


        TransactionReportGenerator.printBalanceReport(totalBalance);
        TransactionReportGenerator.printTransactionsCountByMonth(monthYear, transactionsCount);
        TransactionReportGenerator.printTopExpensesReport(topExpenses);


        LocalDate startDate = LocalDate.of(2023, 12, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        Map<String, Double> minMaxStats = TransactionAnalyzer.findMinMaxExpensesInPeriod(transactions, startDate, endDate);

        Map<String, Double> expensesByCategory = TransactionAnalyzer.getExpensesByCategory(transactions);
        Map<String, Double> expensesByMonth = TransactionAnalyzer.getExpensesByMonth(transactions);

        TransactionReportGenerator.printMinMaxExpensesReport(minMaxStats, startDate, endDate);
        TransactionReportGenerator.printExpenseReportWithVisualization(
                "Звіт: Витрати по категоріях", expensesByCategory);
        TransactionReportGenerator.printExpenseReportWithVisualization(
                "Звіт: Витрати по місяцях", expensesByMonth);
    }
}