/**
 * 
 */
package uk.co.pekim.nodejdbc.server.request;

/**
 * Base class for all requests (that are mapped from JSON).
 * 
 * @author Mike D Pilsbury
 */
public abstract class Request {
    private String type;

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(final String type) {
        this.type = type;
    }
}
