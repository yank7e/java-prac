package com.example.finance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class TransactionCSVReader {

    private TransactionCSVReader() {}

    public static List<Transaction> readTransactions(String filePath) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            URL url = new URL(filePath);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                String line;

                br.readLine();

                while ((line = br.readLine()) != null) {
                    try {
                        String[] values = line.split(",");
                        if (values.length >= 3) {
                            String date = values[0].trim();
                            double amount = Double.parseDouble(values[1].trim());
                            String description = values[2].trim();

                            Transaction transaction = new Transaction(date, amount, description);
                            transactions.add(transaction);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Помилка парсингу числа в рядку: " + line);
                    } catch (Exception e) {
                        System.err.println("Помилка обробки рядка: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}