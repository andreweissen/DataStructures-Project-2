/**
 * OperatorNode.java - Class for Operator nodes
 * Begun 11/10/17
 * @author Andrew Eissen
 */

//package postfixexpressions;

final class OperatorNode implements Node {
    private Node left, right;
    private Operator operator;

    /**
     * Parameterized constructor
     * @param operator
     * @param left
     * @param right
     */
    public OperatorNode(Operator operator, Node left, Node right) {
        this.setOperator(operator);
        this.setLeftNode(left);
        this.setRightNode(right);
    }

    /**
     * Setter for operator
     * @param operator
     * @return void
     */
    private void setOperator(Operator operator) {
        this.operator = operator;
    }

    /**
     * Setter for left <code>Node</code>
     * @param left
     * @return void
     */
    private void setLeftNode(Node left) {
        this.left = left;
    }

    /**
     * Setter for right <code>Node</code>
     * @param right
     * @return void
     */
    private void setRightNode(Node right) {
        this.right = right;
    }

    /**
     * Implementation of method from weekly course text module, slight changes
     * @return String
     */
    @Override
    public String inOrderWalk() {
        String leftValue = this.left.inOrderWalk();
        String rightValue = this.right.inOrderWalk();
        return "(" + leftValue + " " + this.operator.getSymbol() + " " + rightValue + ")";
    }
}