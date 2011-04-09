/**
 * 
 */
package uk.co.pekim.nodejdbc.notifynode;

/**
 * Message to Node ifnormaing that we've initialised.
 * 
 * @author Mike D Pilsbury
 */
public class NotifyInitialised {
    private int port;

    /**
     * Create an initialisation message.
     * 
     * @param port
     *            the port.
     */
    public NotifyInitialised(final int port) {
        this.port = port;
    }

    /**
     * The port.
     * 
     * @return the port
     */
    public int getPort() {
        return port;
    }
}
