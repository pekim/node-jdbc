/**
 *
 */
package uk.co.pekim.nodejdbc.netstring;

/**
 * A buffer that may contain a netstring.
 * 
 * @author Mike D Pilsbury
 */
public interface NetstringBuffer {
    /**
     * Advance, and get the next byte in the buffer.
     * 
     * @return the next byte.
     */
    byte get();

    /**
     * Transfers bytes from the buffer into the destination array, starting at
     * the current position.
     * 
     * @param destination
     *            the array to transfer bytes into.
     */
    void get(byte[] destination);

    /**
     * How many bytes remain in the buffer.
     * 
     * @return the number of bytes remaining.
     */
    int remaining();
}
