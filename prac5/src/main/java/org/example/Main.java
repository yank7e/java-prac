package org.example;

public class Main {
    public static void main(String[] args) {
        FiniteAutomaton automaton = new FiniteAutomaton();

        String[] testInputs = {
                "abcTESTabc",
                "abcTES",
                "TTEST",
                "TETEST",
                "TEST",
                "test"
        };

        System.out.println("=== Finite Automaton Demo ===\n");

        for (String input : testInputs) {
            FiniteAutomaton.State result = automaton.process(input);
            System.out.printf("Input: %-15s -> State: %s%n",
                    "\"" + input + "\"", result);
        }

        System.out.println("\n=== Expected Results ===");
        System.out.println("State F means 'TEST' was found");
        System.out.println("Other states mean 'TEST' was not found");
    }
}