import org.example.FiniteAutomaton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class FiniteAutomatonTest {

    @ParameterizedTest(name = "Input: \"{0}\" should result in state {1}")
    @CsvSource({
            // Basic positive cases
            "TEST, F",
            "abcTESTabc, F",
            "xyzTEST, F",
            "TESTxyz, F",

            // Basic negative cases
            "abcTES, THREE",
            "TES, THREE",
            "TE, TWO",
            "T, ONE",
            "'', S",
            "abc, S",

            // Edge cases - incomplete patterns
            "TESTEST, F",
            "TESTTEST, F",

            // Overlapping patterns - CRITICAL TEST CASES
            "TTEST, F",        // T-TEST (should recognize TEST)
            "TETEST, F",       // TE-TEST (should recognize TEST)
            "TESTEST, F",      // TES-TEST (should recognize TEST)
            "TTTEST, F",       // TT-TEST
            "TTTTEST, F",      // TTT-TEST

            // Multiple occurrences
            "TESTTESTTEST, F",
            "aTESTbTESTc, F",

            // Near misses
            "TEZT, S",
            "TAST, S",
            "TEST1, F",
            "1TEST, F",

            // Case sensitivity
            "test, S",
            "Test, S",
            "TeSt, S",

            // Special patterns
            "TTESTT, F",
            "TETESTEST, F",
            "TTES, TWO"
    })
    public void testAutomatonWithVariousInputs(String input, String expectedState) {
        FiniteAutomaton automaton = new FiniteAutomaton();
        FiniteAutomaton.State result = automaton.process(input);
        FiniteAutomaton.State expected = FiniteAutomaton.State.valueOf(expectedState);

        assertEquals(expected, result,
                String.format("For input '%s', expected state %s but got %s",
                        input, expected, result));
    }

    @Test
    public void testNullInput() {
        FiniteAutomaton automaton = new FiniteAutomaton();
        FiniteAutomaton.State result = automaton.process(null);
        assertEquals(FiniteAutomaton.State.S, result);
    }

    @Test
    public void testEmptyString() {
        FiniteAutomaton automaton = new FiniteAutomaton();
        FiniteAutomaton.State result = automaton.process("");
        assertEquals(FiniteAutomaton.State.S, result);
    }

    @ParameterizedTest(name = "String with TEST at position {0}: \"{1}\"")
    @CsvSource({
            "0, TEST",
            "1, xTEST",
            "5, helloTEST",
            "3, abcTESTxyz"
    })
    public void testTESTAtDifferentPositions(int position, String input) {
        FiniteAutomaton automaton = new FiniteAutomaton();
        FiniteAutomaton.State result = automaton.process(input);
        assertEquals(FiniteAutomaton.State.F, result,
                String.format("TEST at position %d should be recognized", position));
    }

    // CRITICAL TEST: Tests for the bug found in task 3*
    @ParameterizedTest(name = "Overlapping pattern: \"{0}\"")
    @ValueSource(strings = {
            "TTEST",      // The T before TEST shouldn't prevent recognition
            "TETEST",     // The TE before TEST shouldn't prevent recognition
            "TESTEST",    // The TES before TEST shouldn't prevent recognition
            "TTTEST",     // Multiple Ts before TEST
            "TTTTEST",    // Even more Ts
            "TETESTEST",  // Complex overlapping
            "TTESTTESTT"  // Multiple overlapping patterns
    })
    public void testOverlappingPatterns(String input) {
        FiniteAutomaton automaton = new FiniteAutomaton();
        FiniteAutomaton.State result = automaton.process(input);
        assertEquals(FiniteAutomaton.State.F, result,
                String.format("String '%s' contains TEST and should reach state F", input));
    }

    @Test
    public void testLongStringWithTEST() {
        String longString = "a".repeat(1000) + "TEST" + "b".repeat(1000);
        FiniteAutomaton automaton = new FiniteAutomaton();
        FiniteAutomaton.State result = automaton.process(longString);
        assertEquals(FiniteAutomaton.State.F, result);
    }

    @Test
    public void testMultipleResets() {
        FiniteAutomaton automaton = new FiniteAutomaton();

        // First run
        assertEquals(FiniteAutomaton.State.F, automaton.process("TEST"));

        // Second run - automaton should reset
        assertEquals(FiniteAutomaton.State.THREE, automaton.process("TES"));

        // Third run
        assertEquals(FiniteAutomaton.State.S, automaton.process("abc"));
    }
}
