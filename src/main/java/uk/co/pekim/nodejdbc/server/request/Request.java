/**
 * 
 */
package uk.co.pekim.nodejdbc.server.request;

import uk.co.pekim.nodejdbc.server.response.Response;

/**
 * Base class for all requests (that are mapped from JSON).
 * 
 * @author Mike D Pilsbury
 */
public abstract class Request {
    private String type;

    protected abstract Response process();

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
