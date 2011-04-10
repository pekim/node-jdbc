/**
 * 
 */
package uk.co.pekim.nodejdbc.server.request;

import java.sql.Connection;
import java.util.Map;

import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.pekim.nodejdbc.server.response.Response;

/**
 * Processes a Request.
 * 
 * @author Mike D Pilsbury
 */
public class RequestProcessor implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestProcessor.class);

    private final FilterChainContext context;
    private final Map<FilterChainContext, Connection> connections;

    /**
     * Create a request processor for a context.
     * 
     * @param context
     *            the filter chain context
     * @param connections
     *            connections associated with contexts.
     */
    public RequestProcessor(final FilterChainContext context, final Map<FilterChainContext, Connection> connections) {
        this.context = context;
        this.connections = connections;
    }

    @Override
    public void run() {
        Request request = context.getMessage();
        Response response = request.process(context, connections);

        context.setMessage(response);

        context.resume();
    }
}
