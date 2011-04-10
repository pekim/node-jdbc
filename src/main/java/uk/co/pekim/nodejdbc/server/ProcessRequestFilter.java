/**
 * 
 */
package uk.co.pekim.nodejdbc.server;

import java.io.IOException;
import java.sql.Connection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.pekim.nodejdbc.server.request.Request;
import uk.co.pekim.nodejdbc.server.request.RequestProcessor;

/**
 * A filter that processes requests asynchronously.
 * 
 * @author Mike D Pilsbury
 */
public class ProcessRequestFilter extends BaseFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessRequestFilter.class);

    private ExecutorService pool = Executors.newFixedThreadPool(20);
    private Map<FilterChainContext, Connection> connections = Collections
            .synchronizedMap(new HashMap<FilterChainContext, Connection>());

    @Override
    public NextAction handleRead(final FilterChainContext context) throws IOException {
        LOGGER.info("message : " + context.getMessage().getClass().getCanonicalName());

        if (context.getMessage() instanceof Request) {
            final NextAction suspendAction = context.getSuspendAction();

            context.suspend();
            pool.execute(new RequestProcessor(context, connections));

            return suspendAction;
        }

        context.write(context.getMessage());

        return context.getInvokeAction();
    }
}
