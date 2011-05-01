/**
 * 
 */
package uk.co.pekim.nodejdbc.connection;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import uk.co.pekim.nodejdbc.connection.close.CloseConnectionHandler;
import uk.co.pekim.nodejdbc.connection.close.CloseConnectionRequest;
import uk.co.pekim.nodejdbc.connection.close.CloseConnectionResponse;
import uk.co.pekim.nodejdbc.connection.create.CreateConnectionHandler;
import uk.co.pekim.nodejdbc.connection.create.CreateConnectionRequest;
import uk.co.pekim.nodejdbc.connection.create.CreateConnectionResponse;

/**
 * Test connection creation and closing.
 * 
 * @author Mike D Pilsbury
 */
public class ConnectionTest {
    /**
     * 
     */
    private static final String HQQLDB_CONNECTION_URL = "jdbc:hsqldb:mem:test";

    @Test
    public void testConnectionCreation() {
        CreateConnectionRequest request = new CreateConnectionRequest();
        request.setUrl(HQQLDB_CONNECTION_URL);
        CreateConnectionResponse response = (CreateConnectionResponse) new CreateConnectionHandler().handle(request);

        assertNotNull(response.getConnectionIdentifier());
    }

    @Test
    public void testConnectionClosing() {
        CreateConnectionRequest createRequest = new CreateConnectionRequest();
        createRequest.setUrl(HQQLDB_CONNECTION_URL);
        CreateConnectionResponse createResponse = (CreateConnectionResponse) new CreateConnectionHandler()
                .handle(createRequest);

        CloseConnectionRequest closeRequest = new CloseConnectionRequest();
        closeRequest.setConnectionIdentifier(createResponse.getConnectionIdentifier());

        CloseConnectionResponse closeResponse = (CloseConnectionResponse) new CloseConnectionHandler()
                .handle(closeRequest);

        assertNotNull(closeResponse);
    }
}
