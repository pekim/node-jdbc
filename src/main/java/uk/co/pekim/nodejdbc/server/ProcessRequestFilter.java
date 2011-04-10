/**
 * 
 */
package uk.co.pekim.nodejdbc.server;

import java.io.IOException;

import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.pekim.nodejdbc.server.request.Request;
import uk.co.pekim.nodejdbc.server.response.ConnectResponse;

/**
 * A filter that processes requests asynchronously.
 * 
 * @author Mike D Pilsbury
 */
public class ProcessRequestFilter extends BaseFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessRequestFilter.class);

    @Override
    public NextAction handleRead(final FilterChainContext context) throws IOException {
        Request request = context.getMessage();

        context.write(new ConnectResponse());

        return context.getInvokeAction();
    }
}
