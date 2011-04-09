/**
 * 
 */
package uk.co.pekim.nodejdbc.notifynode;

/**
 * Base message to Node. All messages to Node must extend this class.
 * 
 * @author Mike D Pilsbury
 */
public class NotifyMessage {
    private final String type;

    /**
     * Create a message.
     * 
     * @param type
     *            the type of the message. Used in Node to identify the message
     *            type.
     */
    NotifyMessage(final String type) {
        this.type = type;
    }

    /**
     * The message type.
     * 
     * @return the type.
     */
    public String getType() {
        return type;
    }
}
