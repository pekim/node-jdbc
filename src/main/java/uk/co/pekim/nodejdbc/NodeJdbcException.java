/**
 * 
 */
package uk.co.pekim.nodejdbc;


/**
 * A problem somewhere in the Node/Jdbc bridge.
 *
 * @author Mike D Pilsbury
 *
 */
public class NodeJdbcException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     */
    public NodeJdbcException(String message) {
        super(message);
    }

    /**
     * @param string
     * @param cause
     */
    public NodeJdbcException(String message, Throwable cause) {
        super(message, cause);
    }
}
