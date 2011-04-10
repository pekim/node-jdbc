/**
 * 
 */
package uk.co.pekim.nodejdbc.server.request;

import java.sql.Connection;
import java.util.Map;

import org.glassfish.grizzly.filterchain.FilterChainContext;

import uk.co.pekim.nodejdbc.server.response.Response;

/**
 * Base class for all requests (that are mapped from JSON).
 * 
 * @author Mike D Pilsbury
 */
public abstract class Request {
    private String type;

    /**
     * Process the request.
     * 
     * @param context
     *            the context
     * @param connections
     *            connections associated with contexts.
     * @return the generated response.
     */
    protected abstract Response process(FilterChainContext context, Map<FilterChainContext, Connection> connections);

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
