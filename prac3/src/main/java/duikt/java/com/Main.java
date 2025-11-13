package duikt.java.com;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        try {
            System.out.print("Введіть перше число: ");
            double num1 = scanner.nextDouble();

            System.out.print("Введіть операцію (+, -, *, /, sqrt): ");
            String operation = scanner.next();

            double num2 = 0;
            if (!operation.equals("sqrt")) {
                System.out.print("Введіть друге число: ");
                num2 = scanner.nextDouble();
            }

            double result = 0;

            switch (operation) {
                case "+":
                    result = calculator.add(num1, num2);
                    break;
                case "-":
                    result = calculator.subtract(num1, num2);
                    break;
                case "*":
                    result = calculator.multiply(num1, num2);
                    break;
                case "/":
                    result = calculator.divide(num1, num2);
                    break;
                case "sqrt":
                    result = calculator.sqrt(num1);
                    break;
                default:
                    System.out.println("Невідома операція. Завершення роботи.");
                    return;
            }

            System.out.println("Результат: " + result);

        } catch (InputMismatchException e) {
            System.err.println("Помилка вводу: Ви ввели не число. Будь ласка, вводьте тільки числа.");

        } catch (ArithmeticException e) {
            System.err.println("Арифметична помилка: " + e.getMessage());

        } catch (InvalidInputException e) {
            System.err.println("Помилка вхідних даних: " + e.getMessage());

        } catch (Exception e) {
            System.err.println("Сталася неочікувана помилка: " + e.getMessage());

        } finally {
            System.out.println("\n===== Обробка запиту завершена. =====");
            scanner.close();
        }
    }
}
