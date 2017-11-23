/**
 * Operator.java - General operator class
 * Begun 11/10/17
 * @author Andrew Eissen
 */

//package postfixexpressions;

final class Operator {
    private String instruction, symbol;

    /**
     * Parameterized constructor
     * @param instruction
     * @param symbol
     */
    public Operator (String instruction, String symbol) {
        this.setInstruction(instruction);
        this.setSymbol(symbol);
    }

    /**
     * Setter for instruction keyword
     * @param instruction
     * @return void
     */
    private void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    /**
     * Setter for symbol
     * @param symbol
     * @return void
     */
    private void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Getter for instruction keyword
     * @return String
     */
    public String getInstruction() {
        return this.instruction;
    }

    /**
     * Getter for symbol
     * @return String
     */
    public String getSymbol() {
        return this.symbol;
    }
}