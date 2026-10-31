package seedu.duke.games.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;

/**
 * Tests that generated problems stay inside their documented operand ranges and
 * always have a whole-number, non-negative answer where the user guide requires one.
 */
class ArithmeticProblemTest {

    /**
     * How many problems each range test generates. Generation is random, so a single
     * sample would pass even if the bounds were wrong; a few hundred makes it very
     * likely that every operator and both ends of each range are exercised.
     */
    private static final int SAMPLE_SIZE = 500;

    @Test
    public void generateAnswerAlwaysMatchesTheOperands() {
        Random random = new Random(42);
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            ArithmeticProblem problem = ArithmeticProblem.generate(random);
            int left = problem.getLeftOperand();
            int right = problem.getRightOperand();
            int expected = switch (problem.getOperator()) {
            case ADD -> left + right;
            case SUBTRACT -> left - right;
            case MULTIPLY -> left * right;
            case DIVIDE -> left / right;
            };
            assertEquals(expected, problem.getAnswer(),
                    "Answer disagrees with the operands for " + problem);
        }
    }

    @Test
    public void generateSubtractionAnswerIsNeverNegative() {
        Random random = new Random(7);
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            ArithmeticProblem problem = ArithmeticProblem.generate(random);
            if (problem.getOperator() == ArithmeticProblem.Operator.SUBTRACT) {
                assertTrue(problem.getAnswer() >= 0,
                        "Subtraction produced a negative answer: " + problem);
            }
        }
    }

    @Test
    public void generateDivisionAnswerIsAlwaysAWholeNumber() {
        Random random = new Random(13);
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            ArithmeticProblem problem = ArithmeticProblem.generate(random);
            if (problem.getOperator() == ArithmeticProblem.Operator.DIVIDE) {
                assertEquals(0, problem.getLeftOperand() % problem.getRightOperand(),
                        "Division does not divide exactly: " + problem);
            }
        }
    }

    @Test
    public void generateOperandsStayInTheirDocumentedRanges() {
        Random random = new Random(99);
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            ArithmeticProblem problem = ArithmeticProblem.generate(random);
            int left = problem.getLeftOperand();
            int right = problem.getRightOperand();
            switch (problem.getOperator()) {
            case ADD, SUBTRACT -> {
                assertInRange(left, 1, 99, problem);
                assertInRange(right, 1, 99, problem);
            }
            case MULTIPLY -> {
                assertInRange(left, 2, 12, problem);
                assertInRange(right, 2, 12, problem);
            }
            case DIVIDE -> {
                // The divisor is bounded directly; the dividend is divisor * quotient,
                // so it is bounded by the largest product of the two ranges.
                assertInRange(right, 2, 12, problem);
                assertInRange(problem.getAnswer(), 1, 12, problem);
                assertInRange(left, 2, 144, problem);
            }
            default -> throw new IllegalStateException(
                    "Unhandled operator: " + problem.getOperator());
            }
        }
    }

    @Test
    public void toStringRendersTheProblemAsAQuestion() {
        Random random = new Random(1);
        ArithmeticProblem problem = ArithmeticProblem.generate(random);
        String expected = problem.getLeftOperand() + " " + problem.getOperator().getSymbol()
                + " " + problem.getRightOperand() + " = ?";
        assertEquals(expected, problem.toString());
    }

    /**
     * Asserts that a value lies within an inclusive range.
     *
     * @param value   the value to check
     * @param min     the smallest acceptable value
     * @param max     the largest acceptable value
     * @param problem the problem under test, included in the failure message
     */
    private static void assertInRange(int value, int min, int max, ArithmeticProblem problem) {
        assertTrue(value >= min && value <= max,
                value + " is outside [" + min + ", " + max + "] for " + problem);
    }
}
