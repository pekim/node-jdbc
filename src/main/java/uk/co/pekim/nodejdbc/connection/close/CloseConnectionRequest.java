/**
 * 
 */
package uk.co.pekim.nodejdbc.connection.close;

import uk.co.pekim.nodejava.nodehandler.NodeJavaRequest;

/**
 * 
 * 
 * @author Mike D Pilsbury
 */
public class CloseConnectionRequest implements NodeJavaRequest {
    private String connectionIdentifier;

    /**
     * @return the connectionIdentifier
     */
    public String getConnectionIdentifier() {
        return connectionIdentifier;
    }

    /**
     * @param connectionIdentifier
     *            the connectionIdentifier to set
     */
    public void setConnectionIdentifier(final String connectionIdentifier) {
        this.connectionIdentifier = connectionIdentifier;
    }
}
