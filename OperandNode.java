/**
 * OperandNode.java - Class for Operand nodes
 * Begun 11/10/17
 * @author Andrew Eissen
 */

//package postfixexpressions;

final class OperandNode implements Node {
    private int operand;

    /**
     * Parameterized constructor
     * @param operand
     */
    public OperandNode(int operand) {
        this.setOperand(operand);
    }

    /**
     * Setter for operand
     * @param operand
     * @return void
     */
    private void setOperand(int operand) {
        this.operand = operand;
    }

    /**
     * Returns string value of integer
     * @return String
     */
    @Override
    public String inOrderWalk() {
        return String.valueOf(this.operand);
    }
}