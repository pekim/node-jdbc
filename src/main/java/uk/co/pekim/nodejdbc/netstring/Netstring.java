/**
 *
 */
package uk.co.pekim.nodejdbc.netstring;

import java.nio.charset.Charset;

/**
 * Netstring utilities.
 * 
 * @see http://en.wikipedia.org/wiki/Netstring
 * @author Mike D Pilsbury
 * 
 */
public final class Netstring {
    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final int MAXIMUM_LENGTH_BYTES = 16;
    private static final char LENGTH_DELIMITER = ':';
    private static final char STRING_DELIMITER = ',';

    private Netstring() {
    }

    /**
     * Package a string as a netstring.
     * 
     * @param payload
     *            the string to wrap.
     * 
     * @return a netstring.
     */
    public static String build(final String payload) {
        StringBuilder netstring = new StringBuilder();

        netstring.append(payload.length());
        netstring.append(LENGTH_DELIMITER);
        netstring.append(payload);
        netstring.append(STRING_DELIMITER);

        return netstring.toString();
    }

    /**
     * Extract the payload from a netstring.
     * 
     * @param buffer
     *            a netstring from which the payload is to be extracted.
     * @return the payload, or {@code null} if the netstring does not contain
     *         enough bytes.
     */
    public static String extract(final NetstringBuffer buffer) {
        int length = getLength(buffer);
        if (length < 0) {
            return null;
        }

        if (buffer.remaining() < length + 1) {
            return null;
        }

        byte[] payloadBytes = new byte[length];
        buffer.get(payloadBytes);

        if (buffer.get() != STRING_DELIMITER) {
            throw new IllegalArgumentException("Expected , to terminate string in " + buffer);
        }

        return new String(payloadBytes, CHARSET);
    }

    private static int getLength(final NetstringBuffer buffer) {
        final byte[] bytes = new byte[MAXIMUM_LENGTH_BYTES];
        int position = 0;

        boolean gotLength = false;
        while (buffer.remaining() > 0) {
            byte b = buffer.get();

            if (b == LENGTH_DELIMITER) {
                gotLength = true;
                break;
            } else {
                if (position == bytes.length) {
                    throw new IllegalArgumentException("Length longer than " + MAXIMUM_LENGTH_BYTES
                            + " bytes in netstring " + buffer);
                }

                bytes[position++] = b;
            }
        }

        if (gotLength) {
            String lengthString = new String(bytes, 0, position, CHARSET);

            try {
                return Integer.parseInt(lengthString.toString());
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("Cannot parse netstring length from '" + lengthString + "'",
                        exception);
            }
        } else {
            return -1;
        }
    }
}
