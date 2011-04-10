/**
 * 
 */
package uk.co.pekim.nodejdbc.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.pekim.nodejdbc.NodeJdbcException;
import uk.co.pekim.nodejdbc.server.request.ConnectRequest;

/**
 * A filter that supports JSON marshalling and unmarshalling.
 * 
 * @author Mike D Pilsbury
 */
public class JsonFilter extends BaseFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonFilter.class);

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final Map<String, Class<?>> REQUEST_TYPES = new HashMap<String, Class<?>>();

    static {
        REQUEST_TYPES.put("connect", ConnectRequest.class);
    }

    @Override
    public NextAction handleRead(final FilterChainContext context) throws IOException {
        String json = context.getMessage();

        try {
            LOGGER.info("read : " + json);

            @SuppressWarnings("unchecked")
            Map<String, Object> simpleRequest = JSON_MAPPER.readValue(json, Map.class);
            String type = (String) simpleRequest.get("type");

            if (!REQUEST_TYPES.containsKey(type)) {
                throw new NodeJdbcException("Uncognised message type : " + type);
            }

            Object request = JSON_MAPPER.readValue(json, REQUEST_TYPES.get(type));
            context.setMessage(request);

        } catch (Throwable throwable) {
            LOGGER.error("Error processing request", throwable);
            context.write(createFailure(throwable));
        }

        return context.getInvokeAction();
    }

    @Override
    public NextAction handleWrite(final FilterChainContext context) throws IOException {
        String json = JSON_MAPPER.writeValueAsString(context.getMessage());

        LOGGER.info("write : " + json);

        context.setMessage(json);

        return context.getInvokeAction();
    }

    private String createFailure(final Throwable throwable) {
        try {
            final Error error = new Error(throwable);

            return JSON_MAPPER.writeValueAsString(error);
        } catch (Exception exception) {
            LOGGER.error("Failed to create JSON from" + throwable, exception);
            return "{}";
        }
    }

    private final class Error {
        private final Throwable error;

        private Error(final Throwable error) {
            this.error = error;
        }

        @SuppressWarnings("unused")
        public String getType() {
            return "error";
        }

        @SuppressWarnings("unused")
        public Throwable getError() {
            return error;
        }
    }
}
