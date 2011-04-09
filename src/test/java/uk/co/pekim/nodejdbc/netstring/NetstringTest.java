/**
 * 
 */
package uk.co.pekim.nodejdbc.netstring;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test Netstring class.
 * 
 * @author Mike D Pilsbury
 */
public class NetstringTest {
    @Test
    public void buildEmptyString() {
        assertEquals("0:,", Netstring.build(""));
    }

    @Test
    public void buildOneCharacterString() {
        assertEquals("1:a,", Netstring.build("a"));
    }

    @Test
    public void buildTwoCharacterString() {
        assertEquals("2:ab,", Netstring.build("ab"));
    }

    @Test
    public void extractEmptyString() {
        assertEquals("", Netstring.extract(new Buffer("0:,")));
    }

    @Test
    public void extractOneCharacterString() {
        assertEquals("a", Netstring.extract(new Buffer("1:a,")));
    }

    @Test
    public void extractTwoCharacterString() {
        assertEquals("ab", Netstring.extract(new Buffer("2:ab,")));
    }

    @Test
    public void extractPartialLength() {
        assertEquals(null, Netstring.extract(new Buffer("12")));
    }

    @Test
    public void extractIncompleteString() {
        assertEquals(null, Netstring.extract(new Buffer("12:abc")));
    }

    @Test
    public void extractMissingStringTerminator() {
        assertEquals(null, Netstring.extract(new Buffer("1:a")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void extractWrongStringTerminator() {
        Netstring.extract(new Buffer("1:a?"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void extractLengthTooLong() {
        Netstring.extract(new Buffer("12345678901234567890"));
    }

    private class Buffer implements NetstringBuffer {
        private final byte[] content;
        private int position;

        Buffer(String content) {
            this.content = content.getBytes();
            this.position = 0;
        }

        @Override
        public byte get() {
            return content[position++];
        }

        @Override
        public void get(byte[] destination) {
            System.arraycopy(content, position, destination, 0, destination.length);
            position += destination.length;
        }

        @Override
        public int remaining() {
            return content.length - position;
        }
    }
}
