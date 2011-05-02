/**
 * 
 */
package uk.co.pekim.nodejdbc.metadata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.pekim.nodejava.NodeJavaException;
import uk.co.pekim.nodejdbc.connection.close.CloseConnectionHandler;
import uk.co.pekim.nodejdbc.connection.close.CloseConnectionRequest;
import uk.co.pekim.nodejdbc.connection.create.CreateConnectionHandler;
import uk.co.pekim.nodejdbc.connection.create.CreateConnectionRequest;
import uk.co.pekim.nodejdbc.connection.create.CreateConnectionResponse;

/**
 * Test metadata retrieval.
 * 
 * @author Mike D Pilsbury
 */
public class MetadataHandlerTest {
    private static final String HQQLDB_CONNECTION_URL = "jdbc:hsqldb:mem:test";

    private String connectionIdentifier;
    private MetadataRequest request;

    @Before
    public void createConnection() {
        CreateConnectionRequest request = new CreateConnectionRequest();
        request.setUrl(HQQLDB_CONNECTION_URL);
        CreateConnectionResponse response = new CreateConnectionHandler().handle(request);

        this.connectionIdentifier = response.getConnectionIdentifier();
        this.request = new MetadataRequest();
        this.request.setConnectionIdentifier(this.connectionIdentifier);
    }

    @After
    public void closeConnection() {
        CloseConnectionRequest request = new CloseConnectionRequest();
        request.setConnectionIdentifier(connectionIdentifier);
        new CloseConnectionHandler().handle(request);
    }

    @Test(expected = NodeJavaException.class)
    public void testUnsupportedDataName() {
        request.addDataName("bad");

        new MetadataHandler().handle(request);
    }

    @Test
    public void testBoolean() {
        String dataName = "allProceduresAreCallable";
        request.addDataName(dataName);
        MetadataResponse response = new MetadataHandler().handle(request);

        assertTrue((Boolean) response.getData().get(dataName));
    }

    @Test
    public void testString() {
        String dataName = "userName";
        request.addDataName(dataName);
        MetadataResponse response = new MetadataHandler().handle(request);

        assertEquals("SA", (String) response.getData().get(dataName));
    }

    @Test
    public void testInteger() {
        String dataName = "databaseMajorVersion";
        request.addDataName(dataName);
        MetadataResponse response = new MetadataHandler().handle(request);

        assertEquals(Integer.valueOf(2), (Integer) response.getData().get(dataName));
    }

    @Test
    public void testResultSet() {
        String dataName = "catalogs";
        request.addDataName(dataName);
        MetadataResponse response = new MetadataHandler().handle(request);

        System.out.println(response.getData().get(dataName));
    }
}
