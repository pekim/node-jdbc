/**
 * 
 */
package uk.co.pekim.nodejdbc.server.request;

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

    /**
     * Create a request processor for a context.
     * 
     * @param context
     *            the filter chain context
     */
    public RequestProcessor(final FilterChainContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        Request request = context.getMessage();
        Response response = request.process();

        context.setMessage(response);

        context.resume();
    }
}
