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

/**
 * A filter that supports JSON marshalling and unmarshalling.
 * 
 * @author Mike D Pilsbury
 */
public class JsonFilter extends BaseFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonFilter.class);

    @Override
    public NextAction handleRead(final FilterChainContext context) throws IOException {
        Object jsonString = context.getMessage();
        LOGGER.info("read : " + jsonString);
        
        context.write(jsonString + "++");
        
        return context.getInvokeAction();
    }
}
