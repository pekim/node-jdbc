/**
 * 
 */
package uk.co.pekim.nodejdbc.server.response;

/**
 * Base class for all responses.
 * 
 * @author Mike D Pilsbury
 */
public abstract class Response {
    private final String type;

    /**
     * Create a response.
     * 
     * @param type
     *            the type of the response.
     */
    public Response(final String type) {
        this.type = type;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
}
