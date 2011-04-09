/**
 * 
 */
package uk.co.pekim.nodejdbc.netstring;

import org.glassfish.grizzly.Buffer;

/**
 * A netstring buffer based on a Grizzly buffer.
 * 
 * @author Mike D Pilsbury
 */
public class GrizzlyNetstringBuffer implements NetstringBuffer {
    private final Buffer buffer;

    /**
     * Create a buffer using a Grizzly buffer.
     * 
     * @param buffer
     *            the base buffer.
     */
    public GrizzlyNetstringBuffer(final Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public byte get() {
        return buffer.get();
    }

    @Override
    public void get(final byte[] destination) {
        buffer.get(destination);
    }

    @Override
    public int remaining() {
        return buffer.remaining();
    }

    @Override
    public String toString() {
        return buffer.toStringContent();
    }

}
