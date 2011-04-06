package uk.co.pekim.nodejdbc;

import org.junit.Test;


/**
 * Unit test for NodeJdbc.
 */
public class NodeJdbcTest {
    @Test(expected = NodeJdbcException.class)
    public void testMissingJsonArg() {
        NodeJdbc.main(new String[] {""});
    }

    @Test(expected = NodeJdbcException.class)
    public void testBadJsonArg() {
        NodeJdbc.main(new String[] {"", "bad"});
    }

    @Test
    public void testGoodJsonArg() {
        NodeJdbc.main(new String[] {"", "{}"});
    }
}
