package uk.co.pekim.nodejdbc.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;


public class ConfigurationTest {
    @Test
    public void test() throws Exception {
        Configuration configuration = Configuration.parseJson("{\"node\": {\"port\": 1234}, \"jdbc\": {}}");
        
        assertEquals(1234, configuration.getNode().getPort());
        assertNotNull(configuration.getJdbc());
    }
}
