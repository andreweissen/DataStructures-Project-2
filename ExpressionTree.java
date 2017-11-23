/**
 * ExpressionTree.java - Constructs the expression tree
 * Begun 11/10/17
 * @author Andrew Eissen
 */

//package postfixexpressions;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

final class ExpressionTree {

    // Stores <code>OperatorNode</code> and <code>OperandNode</code> instances as per rubric
    private final Stack<Node> nodeStack = new Stack<>();

    // Stores register locations
    private final Stack<String> registerStack = new Stack<>();

    // ArrayList stores completed String lines of 3-address assembly code
    private final ArrayList<String> assemblyArrayList = new ArrayList<>();

    // <code>int</code> field is used for numeric register count later in the program
    private int operatorCount = 0;

    // Default constructor
    public ExpressionTree() {}

    /**
     * Creates the expression tree based on the algorithm found at...
     * http://tajendrasengar.blogspot.com/2011/09/postfix-to-infix-algorithm.html
     * @param expression
     * @return String
     */
    public String createExpressionTree(String expression) {
        String assembly;

        /**
         * The decision to split by whitespace was made as per the rubric's instructions; "the
         * tokens are separated by spaces..." The author attempted a few regex-heavy alternatives
         * but eventually decided to go with this simple application.
         */
        String[] postfixArray = expression.split("\\s+");

        try {
            // To make text file more readable, include the expression first
            assemblyArrayList.add("Input: " + expression);

            for (String item : postfixArray) {
                if (this.isOperand(item)) {
                    nodeStack.push(new OperandNode(Integer.parseInt(item)));
                } else if (this.isOperator(item)) {
                    if (nodeStack.size() < 2) {
                        this.displayErrorPopup("Error: Insufficient operands.", "Error");
                        throw new RuntimeException();
                    } else {
                        Operator operator = this.createNewOperator(item);
                        Node right = nodeStack.pop();
                        Node left = nodeStack.pop();

                        nodeStack.push(new OperatorNode(operator, left, right));
                        assemblyArrayList.add(this.createAssemblyLine(operator, left, right));
                        this.operatorCount++;
                    }
                } else {
                    // As per rubric, non-operand/operator characters are prohibited.
                    throw new InvalidTokenException();
                }
            }
        } catch (InvalidTokenException e) {
            this.displayErrorPopup("Error: Improper input detected.", "InvalidTokenException");
            return ""; // Ensures half-baked solutions are not posted to the results field
        }

        /**
         * In this case, we need to find a way to separate lines of assembly in such a way that
         * simple text editors can respect line breaks. <code.\n</code> doesn't work in a
         * <code>.txt</code> file, so <code>System.lineSeparator()</code> was used instead.
         *
         * stackoverflow.com/questions/19084352/how-to-write-new-line-character-to-a-file-in-java
         */
        assembly = String.join(System.lineSeparator(), assemblyArrayList) + System.lineSeparator();

        // For testing purposes, the assembly is logged in the console
        System.out.println(assembly);

        // The assembly is written to the proper <code>.txt</code> file
        this.writeAssemblyToFile(assembly, "ExpressionTreeAssembly.txt");

        // Final remaining stack entry will be desired element
        return nodeStack.pop().inOrderWalk();
    }

    /**
     * Method uses basic algorithm presented in the weekly module reading and updates the assembly
     * equivalent of the operation input. Constructs a single line of assembly code equivalent, with
     * fields for the instruction itself (i.e. "Add") and for the register and the 2 associated
     * source operands. The terms operation/instruction, destination register, and source operands
     * were taken from the course text description of the 3-address assembly components.
     * @param operator
     * @param left
     * @param right
     * @return String
     */
    private String createAssemblyLine(Operator operator, Node left, Node right) {
        String opInstruction, destination, sourceOne, sourceTwo;

        // Grabs operation instruction String ("Add", "Sub", "Mul", "Div")
        opInstruction = operator.getInstruction();

        // Sets the destination register based on the value of the global counter variable
        destination = "R" + this.operatorCount;

        // Default definition of source operands as empty Strings
        sourceOne = sourceTwo = "";

        /**
         * At first, the author was using a String field in both the <code>OperatorNode</code> and
         * <code>OperandNode</code> classes that simply stated what the name of the class was. This
         * was used to distinguish between classes. However, the author discovered the
         * <code>instanceof</code> keyword instead, which worked significantly more efficiently.
         */
        if (left instanceof OperandNode) {
            sourceOne = left.inOrderWalk();
            if (right instanceof OperandNode) {
                sourceTwo = right.inOrderWalk();
            } else {
                sourceTwo = registerStack.pop();
            }
        } else if (left instanceof OperatorNode) {
            if (right instanceof OperatorNode) {
                sourceTwo = registerStack.pop();
            } else {
                sourceTwo = right.inOrderWalk();
            }
            sourceOne = registerStack.pop();
        }

        // Add to the register stack
        registerStack.push(destination);

        // Joins the four major String components into a single line of assembly
        return opInstruction + " " + destination + " " + sourceOne + " " + sourceTwo;
    }

    /**
     * Method simply creates an instance of the appropriate operator class based on the operation in
     * question. In contrast to the implementation suggested in the weekly reading, the author saw
     * fit to abide by the rubric requirement to "avoid the duplication of code" by using a single
     * <code>Operator</code> class that accepts an instruction operation keyword and associated
     * symbol.
     *
     * As the grading rubric did not <em>explicitly</em> require an <code>Operator</code>
     * abstract class and four class implementations, the author went with the current single-class
     * operator creation method.
     * @param operator
     * @return Operator
     */
    private Operator createNewOperator(String operator) {
        switch (operator) {
            case "+":
                return new Operator("Add", operator);
            case "-":
                return new Operator("Sub", operator);
            case "*":
                return new Operator("Mul", operator);
            case "/":
                return new Operator("Div", operator);
            default: // Should be unreachable, but Google styleguide requires <code>default</code>
                this.displayErrorPopup("Error: Illegitimate operator.", "Error");
                throw new RuntimeException();
        }
    }

    /**
     * This method fulfills the project rubric requirement to write the assembly equivalent to a
     * <code>.txt</code> file. As part of the try-with-resources, the FileWriter is automatically
     * closed. See:
     * http://stackoverflow.com/questions/27093607/how-to-create-and-write-a-txt-file-in-java
     * @param content
     * @param fileName
     * @return void
     */
    private void writeAssemblyToFile(String content, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(content + System.lineSeparator());
        } catch(IOException e) {
            this.displayErrorPopup("Error: File-writing operation failed.", "Error");
        }
    }

    /**
     * Method returns boolean value depending on whether the input String exists in the array of
     * predefined legal operators. The Java equivalent of my favorite jQuery method to date,
     * <code>.inArray()</code>
     * @param input
     * @return boolean
     */
    private boolean isOperator(String input) {
        String[] legalOperators = {"+", "-", "*", "/"};
        return Arrays.asList(legalOperators).contains(input);
    }

    /**
     * Method checks input to determine if it is numeric. This could have also been done with regex,
     * but this way is more readable and intuitive. Idea modified from...
     * http://javaconceptoftheday.com/java-program-to-check-user-input-is-number-or-not/
     * @param input
     * @return boolean
     */
    private boolean isOperand(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Method simply displays an error pop-up depending on improper input, too few operands, or an
     * unknown operator.
     * @param message
     * @param title
     * @return void
     */
    private void displayErrorPopup(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}