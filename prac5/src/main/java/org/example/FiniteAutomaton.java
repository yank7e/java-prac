package org.example;

public class FiniteAutomaton {

    public enum State {
        S,     // Start state
        ONE,   // Found 'T'
        TWO,   // Found 'TE'
        THREE, // Found 'TES'
        F      // Final state - Found 'TEST'
    }

    private State currentState;

    public FiniteAutomaton() {
        this.currentState = State.S;
    }

    public State process(String input) {
        currentState = State.S;

        if (input == null) {
            return currentState;
        }

        for (char c : input.toCharArray()) {
            transition(c);

            // If we reached final state, we can stop early (optimization)
            if (currentState == State.F) {
                return currentState;
            }
        }

        return currentState;
    }

    private void transition(char c) {
        switch (currentState) {
            case S:
                if (c == 'T') {
                    currentState = State.ONE;
                }
                // else stay in S
                break;

            case ONE:
                if (c == 'E') {
                    currentState = State.TWO;
                } else if (c == 'T') {
                    // Stay in state ONE - this handles cases like "TTEST"
                    currentState = State.ONE;
                } else {
                    currentState = State.S;
                }
                break;

            case TWO:
                if (c == 'S') {
                    currentState = State.THREE;
                } else if (c == 'T') {
                    // Go back to state ONE - this handles cases like "TET"
                    currentState = State.ONE;
                } else {
                    currentState = State.S;
                }
                break;

            case THREE:
                if (c == 'T') {
                    currentState = State.F;
                } else {
                    currentState = State.S;
                }
                break;

            case F:
                // Once in final state, stay there
                break;
        }
    }

    public State getCurrentState() {
        return currentState;
    }
}