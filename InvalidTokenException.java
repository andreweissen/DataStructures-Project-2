/**
 * InvalidTokenException.java - User-defined checked exception
 * Begun 11/10/17
 * @author Andrew Eissen
 */

//package postfixexpressions;

class InvalidTokenException extends Exception {

    // Default constructor
    public InvalidTokenException() {
        super();
    }

    /**
     * Parameterized constructor
     * @param message
     */
    public InvalidTokenException(String message) {
       super(message);
    }

    /**
     * Parameterized constructor
     * @param message
     * @param cause
     */
    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Parameterized constructor
     * @param cause
     */
    public InvalidTokenException(Throwable cause) {
        super(cause);
    }
}