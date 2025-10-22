import com.example.finance.Transaction;
import com.example.finance.TransactionAnalyzer;
import com.example.finance.TransactionCSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class TransactionAnalyzerTest {

    private List<Transaction> testTransactions;

    @BeforeEach
    void setUp() {
        Transaction t1 = new Transaction("01-02-2023", 100.0, "Дохід");
        Transaction t2 = new Transaction("15-02-2023", -50.0, "Витрата 1");
        Transaction t3 = new Transaction("05-03-2023", 150.0, "Дохід");
        Transaction t4 = new Transaction("10-03-2023", -200.0, "Витрата 2");
        Transaction t5 = new Transaction("20-02-2023", -30.0, "Витрата 3");
        testTransactions = Arrays.asList(t1, t2, t3, t4, t5);
    }

    @Test
    public void testCalculateTotalBalance() {
        double expected = -30.0;
        double result = TransactionAnalyzer.calculateTotalBalance(testTransactions);
        Assertions.assertEquals(expected, result, "Розрахунок загального балансу неправильний");
    }

    @Test
    public void testCountTransactionsByMonth() {
        int countFeb = TransactionAnalyzer.countTransactionsByMonth(testTransactions, "02-2023");
        int countMar = TransactionAnalyzer.countTransactionsByMonth(testTransactions, "03-2023");
        int countApr = TransactionAnalyzer.countTransactionsByMonth(testTransactions, "04-2023");

        Assertions.assertEquals(3, countFeb, "Кількість транзакцій за лютий неправильна");
        Assertions.assertEquals(2, countMar, "Кількість транзакцій за березень неправильна");
        Assertions.assertEquals(0, countApr, "Кількість транзакцій за квітень неправильна");
    }

    @Test
    public void testFindTopExpenses() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("01-01-2023", -100.0, "1"),
                new Transaction("01-01-2023", -500.0, "5"),
                new Transaction("01-01-2023", -900.0, "9"),
                new Transaction("01-01-2023", 5000.0, "Дохід"),
                new Transaction("01-01-2023", -200.0, "2"),
                new Transaction("01-01-2023", -1000.0, "10"),
                new Transaction("01-01-2023", -800.0, "8"),
                new Transaction("01-01-2023", -1100.0, "11"),
                new Transaction("01-01-2023", -400.0, "4"),
                new Transaction("01-01-2023", -700.0, "7"),
                new Transaction("01-01-2023", -300.0, "3"),
                new Transaction("01-01-2023", -600.0, "6")
        );

        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);

        Assertions.assertEquals(10, topExpenses.size());
        Assertions.assertEquals(-1100.0, topExpenses.get(0).getAmount());
        Assertions.assertEquals(-200.0, topExpenses.get(9).getAmount());
    }

    @Test
    public void testReadTransactions_IntegrationTest() {
        String filePath = "https://informer.com.ua/dut/java/pr2.csv";
        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);

        Assertions.assertNotNull(transactions);
        Assertions.assertFalse(transactions.isEmpty(), "Список транзакцій не повинен бути порожнім");
    }
}