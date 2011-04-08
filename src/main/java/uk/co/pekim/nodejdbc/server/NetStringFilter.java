/**
 * 
 */
package uk.co.pekim.nodejdbc.server;

import java.io.IOException;
import java.nio.charset.Charset;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.MemoryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A filter that supports netstring formatting.
 * 
 * @see http://en.wikipedia.org/wiki/Netstring
 * @author Mike D Pilsbury
 */
public class NetStringFilter extends BaseFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(NetStringFilter.class);

    private static final Charset CHARSET = Charset.forName("UTF-8");

    private static final char STRING_DELIMITER = ',';
    private static final char LENGTH_DELIMITER = ':';

    @Override
    public NextAction handleRead(final FilterChainContext context) throws IOException {
        final Buffer sourceBuffer = context.getMessage();
        final int sourceBufferLength = sourceBuffer.remaining();

        // Find end of length digits.
        boolean foundLengthDelimiter = false;
        while (sourceBuffer.position() < sourceBufferLength) {
            if (sourceBuffer.get() == LENGTH_DELIMITER) {
                foundLengthDelimiter = true;
                break;
            }
        }

        // Not found.
        if (!foundLengthDelimiter) {
            sourceBuffer.rewind();
            sourceBuffer.position(0);
            return context.getStopAction(sourceBuffer);
        }

        // Get string length.
        int lengthLength = sourceBuffer.position() - 1;
        final int stringLength = getStringLength(sourceBuffer, lengthLength);
        final int requiredBufferLength = lengthLength + 1 + stringLength + 1;

        // Not enough buffer to contain the full string.
        if (sourceBufferLength < requiredBufferLength) {
            sourceBuffer.rewind();
            sourceBuffer.position(0);
            return context.getStopAction(sourceBuffer);
        }

        // Create a buffer with the remaining byte (following this string), if any.
        Buffer remainder = null;
        if (sourceBufferLength > requiredBufferLength) {
            remainder = sourceBuffer.split(requiredBufferLength);
        }

        // Read the string payload.
        final byte[] stringBytes = new byte[stringLength];
        sourceBuffer.get(stringBytes);
        final String string = new String(stringBytes, CHARSET);
        
        // Consume the delimiting comma.
        byte stringDelimiter = sourceBuffer.get();
        if (stringDelimiter != STRING_DELIMITER) {
            LOGGER.warn("Expected '" + STRING_DELIMITER + "' delimiting netstring but found '" + ((char) stringDelimiter) + "'");
        }

        LOGGER.info("JSON received : " + string);

        // The extracted string is the for the next filter.
        context.setMessage(string);
        
        sourceBuffer.tryDispose();

        return context.getInvokeAction(remainder);
    }
    
    @Override
    public NextAction handleWrite(FilterChainContext context) throws IOException {
        // Create the netstring.
        final String stringToWrap = context.getMessage();
        final String wrappedString = "" + stringToWrap.length() + LENGTH_DELIMITER + stringToWrap + STRING_DELIMITER;
        LOGGER.info("write : " + stringToWrap);

        // Put the netstirng in a buffer.
        @SuppressWarnings("unchecked")
        MemoryManager<Buffer> memoryManager = context.getConnection().getTransport().getMemoryManager();
        Buffer buffer = Buffers.wrap(memoryManager, wrappedString);
        
        context.setMessage(buffer);

        return context.getInvokeAction();
    }

    private int getStringLength(final Buffer buffer, final int byteCount) {
        final int oldPosition = buffer.position();
        buffer.position(0);
        
        final byte[] bytes = new byte[byteCount];
        
        buffer.get(bytes);
        
        buffer.position(oldPosition);
        return Integer.parseInt(new String(bytes, CHARSET));
    }
}
