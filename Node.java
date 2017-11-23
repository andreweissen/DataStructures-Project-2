/**
 * Node.java - Node interface class
 * Begun 11/10/17
 * @author Andrew Eissen
 */

//package postfixexpressions;

/**
 * Interface for Nodes
 * @author Andrew Eissen
 */
public interface Node {

    /**
     * Perhaps important to note is the reason why the author included only the
     * <code>inOrderWalk</code> method and none of the others listed in the example code in the
     * module reading. Simply put, nothing was being evaluated so <code>evaluate</code> was unused
     * code, and the other methods served no purpose, given the implementation strategy the author
     * elected to use.
     */
    public String inOrderWalk();
}