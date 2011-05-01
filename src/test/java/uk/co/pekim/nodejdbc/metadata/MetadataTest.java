/**
 * 
 */
package uk.co.pekim.nodejdbc.metadata;

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
public class MetadataTest {
    private static final String HQQLDB_CONNECTION_URL = "jdbc:hsqldb:mem:test";

    private String connectionIdentifier;

    @Before
    public void createConnection() {
        CreateConnectionRequest request = new CreateConnectionRequest();
        request.setUrl(HQQLDB_CONNECTION_URL);
        CreateConnectionResponse response = new CreateConnectionHandler().handle(request);

        connectionIdentifier = response.getConnectionIdentifier();
    }

    @After
    public void closeConnection() {
        CloseConnectionRequest request = new CloseConnectionRequest();
        request.setConnectionIdentifier(connectionIdentifier);
        new CloseConnectionHandler().handle(request);
    }

    @Test(expected = NodeJavaException.class)
    public void testUnsupportedDataName() {
        MetadataRequest request = new MetadataRequest();
        request.setConnectionIdentifier(connectionIdentifier);
        request.addDataName("bad");

        new MetadataHandler().handle(request);
    }

    @Test
    public void testBoolean() {
        MetadataRequest request = new MetadataRequest();
        request.setConnectionIdentifier(connectionIdentifier);
        request.addDataName("allProceduresAreCallable");

        MetadataResponse response = new MetadataHandler().handle(request);

        assertTrue((Boolean) response.getData().get("allProceduresAreCallable"));
    }
}
