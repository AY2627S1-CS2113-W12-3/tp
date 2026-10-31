package seedu.duke.games.calculator;

import java.util.Random;

/**
 * One randomly generated arithmetic problem, such as {@code 14 + 27 = ?}, together
 * with its answer.
 *
 * <p>Instances are immutable: a problem is generated once and never changes, which
 * means the answer cannot drift out of step with the operands.
 */
public class ArithmeticProblem {

    /**
     * The operators a problem can use, each paired with the symbol shown to the player.
     *
     * <p>An enum is used rather than plain strings so that the set of operators is
     * fixed at compile time and a switch over them can be checked for completeness.
     */
    public enum Operator {
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("x"),
        DIVIDE("/");

        private final String symbol;

        Operator(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    /** Operand bounds for addition and subtraction. */
    private static final int ADDITIVE_MIN = 1;
    private static final int ADDITIVE_MAX = 99;

    /** Operand bounds for multiplication, and for the divisor and quotient of a division. */
    private static final int FACTOR_MIN = 2;
    private static final int FACTOR_MAX = 12;
    private static final int QUOTIENT_MIN = 1;
    private static final int QUOTIENT_MAX = 12;

    private final int leftOperand;
    private final int rightOperand;
    private final Operator operator;
    private final int answer;

    /**
     * Creates a problem with the given parts. Private because every problem is built
     * by {@link #generate(Random)}, which is what guarantees the answer matches.
     */
    private ArithmeticProblem(int leftOperand, Operator operator, int rightOperand, int answer) {
        this.leftOperand = leftOperand;
        this.operator = operator;
        this.rightOperand = rightOperand;
        this.answer = answer;
    }

    /**
     * Generates a random problem using one of the four operators.
     *
     * <p>Two operators need their operands constrained so the answer stays friendly
     * to mental arithmetic:
     * <ul>
     *   <li>subtraction puts the larger operand first, so the answer is never negative;</li>
     *   <li>division builds the dividend as {@code divisor * quotient}, so the answer
     *       is always a whole number and no rounding is ever involved.</li>
     * </ul>
     *
     * @param random the source of randomness, passed in so tests can seed it
     * @return a freshly generated problem
     */
    public static ArithmeticProblem generate(Random random) {
        Operator[] operators = Operator.values();
        Operator operator = operators[random.nextInt(operators.length)];

        switch (operator) {
        case ADD: {
            int left = randomBetween(random, ADDITIVE_MIN, ADDITIVE_MAX);
            int right = randomBetween(random, ADDITIVE_MIN, ADDITIVE_MAX);
            return new ArithmeticProblem(left, operator, right, left + right);
        }
        case SUBTRACT: {
            int first = randomBetween(random, ADDITIVE_MIN, ADDITIVE_MAX);
            int second = randomBetween(random, ADDITIVE_MIN, ADDITIVE_MAX);
            // Order the operands so the larger one comes first.
            int left = Math.max(first, second);
            int right = Math.min(first, second);
            return new ArithmeticProblem(left, operator, right, left - right);
        }
        case MULTIPLY: {
            int left = randomBetween(random, FACTOR_MIN, FACTOR_MAX);
            int right = randomBetween(random, FACTOR_MIN, FACTOR_MAX);
            return new ArithmeticProblem(left, operator, right, left * right);
        }
        case DIVIDE: {
            int divisor = randomBetween(random, FACTOR_MIN, FACTOR_MAX);
            int quotient = randomBetween(random, QUOTIENT_MIN, QUOTIENT_MAX);
            // Work backwards from the answer so the division comes out exactly.
            int dividend = divisor * quotient;
            return new ArithmeticProblem(dividend, operator, divisor, quotient);
        }
        default:
            // Unreachable while every Operator is handled above, but a switch over an
            // enum still needs a default to be a definite-return expression.
            throw new IllegalStateException("Unhandled operator: " + operator);
        }
    }

    /**
     * Returns a random integer between the given bounds.
     *
     * @param random the source of randomness
     * @param min    the smallest value that may be returned, inclusive
     * @param max    the largest value that may be returned, inclusive
     * @return a value in the range {@code [min, max]}
     */
    private static int randomBetween(Random random, int min, int max) {
        assert min <= max : "A range must not be inverted";
        return min + random.nextInt(max - min + 1);
    }

    public int getAnswer() {
        return answer;
    }

    public Operator getOperator() {
        return operator;
    }

    public int getLeftOperand() {
        return leftOperand;
    }

    public int getRightOperand() {
        return rightOperand;
    }

    /**
     * Renders the problem as the player sees it, for example {@code 14 + 27 = ?}.
     *
     * @return the problem as a question, without the answer
     */
    @Override
    public String toString() {
        return leftOperand + " " + operator.getSymbol() + " " + rightOperand + " = ?";
    }
}
