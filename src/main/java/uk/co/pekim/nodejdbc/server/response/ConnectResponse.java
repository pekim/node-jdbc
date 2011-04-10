/**
 * 
 */
package uk.co.pekim.nodejdbc.server.response;

/**
 * A successful connection.
 * 
 * @author Mike D Pilsbury
 */
public class ConnectResponse extends Response {
    /**
     * Create a connect response.
     */
    public ConnectResponse() {
        super("connect");
    }
}
