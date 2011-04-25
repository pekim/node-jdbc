/**
 * 
 */
package uk.co.pekim.nodejdbc.connection.create;

import uk.co.pekim.nodejava.nodehandler.NodeJavaResponse;

/**
 * 
 * 
 * @author Mike D Pilsbury
 */
public class CreateConnectionResponse implements NodeJavaResponse {
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
