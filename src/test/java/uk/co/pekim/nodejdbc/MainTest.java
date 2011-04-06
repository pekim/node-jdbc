package uk.co.pekim.nodejdbc;

import org.junit.Test;


/**
 * Unit test for NodeJdbc.
 */
public class MainTest {
    @Test(expected = NodeJdbcException.class)
    public void testMissingJsonArg() {
        Main.main(new String[] {""});
    }

    @Test(expected = NodeJdbcException.class)
    public void testBadJsonArg() {
        Main.main(new String[] {"", "bad"});
    }

    @Test
    public void testGoodJsonArg() {
        Main.main(new String[] {"", "{}"});
    }
}
