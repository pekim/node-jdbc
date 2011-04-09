/**
 * 
 */
package uk.co.pekim.nodejdbc.server;

import java.io.IOException;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.MemoryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.pekim.nodejdbc.netstring.GrizzlyNetstringBuffer;
import uk.co.pekim.nodejdbc.netstring.Netstring;
import uk.co.pekim.nodejdbc.netstring.NetstringBuffer;

/**
 * A filter that supports netstring formatting.
 * 
 * @see http://en.wikipedia.org/wiki/Netstring
 * @author Mike D Pilsbury
 */
public class NetStringFilter extends BaseFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(NetStringFilter.class);

    @Override
    public NextAction handleRead(final FilterChainContext context) throws IOException {
        final Buffer buffer = context.getMessage();
        final NetstringBuffer netstringBuffer = new GrizzlyNetstringBuffer(buffer);
        final String string = Netstring.extract(netstringBuffer);

        if (string == null) {
            // Not enough bytes in message to extract a netstring from.
            buffer.rewind();
            buffer.position(0);
            return context.getStopAction(buffer);
        }

        // Create a buffer with the remaining bytes (following the netstring),
        // if any.
        Buffer remainder = null;
        if (buffer.position() < buffer.capacity()) {
            remainder = buffer.split(buffer.position());
        }

        context.setMessage(string);

        buffer.tryDispose();

        return context.getInvokeAction(remainder);
    }

    @Override
    public NextAction handleWrite(final FilterChainContext context) throws IOException {
        // Create the netstring.
        final String netstring = Netstring.build((String) context.getMessage());
        LOGGER.info("write : " + netstring);

        // Put the netstring in a buffer.
        @SuppressWarnings("unchecked")
        MemoryManager<Buffer> memoryManager = context.getConnection().getTransport().getMemoryManager();
        Buffer buffer = Buffers.wrap(memoryManager, netstring);

        context.setMessage(buffer);

        return context.getInvokeAction();
    }
}
