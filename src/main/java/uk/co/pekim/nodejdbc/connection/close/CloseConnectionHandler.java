/**
 * 
 */
package uk.co.pekim.nodejdbc.connection.close;

import static uk.co.pekim.nodejdbc.connection.Connections.CONNECTIONS;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import uk.co.pekim.nodejava.NodeJavaException;
import uk.co.pekim.nodejava.nodehandler.NodeJavaHandler;
import uk.co.pekim.nodejava.nodehandler.NodeJavaRequest;
import uk.co.pekim.nodejava.nodehandler.NodeJavaResponse;

/**
 * Handler for closing a connection.
 * 
 * @author Mike D Pilsbury
 */
public class CloseConnectionHandler implements NodeJavaHandler {
    @Override
    public Class<? extends NodeJavaRequest> getRequestClass() {
        return CloseConnectionRequest.class;
    }

    @Override
    public NodeJavaResponse handle(final NodeJavaRequest nodeJavaRequest) {
        CloseConnectionRequest request = (CloseConnectionRequest) nodeJavaRequest;
        CloseConnectionResponse response = new CloseConnectionResponse();

        try {
            UUID connectionIdentifier = UUID.fromString(request.getConnectionIdentifier());
            Connection connection = CONNECTIONS.get(connectionIdentifier);

            connection.close();

            CONNECTIONS.remove(connectionIdentifier);

            return response;
        } catch (SQLException exception) {
            throw new NodeJavaException("Failed to close connection", exception);
        }
    }
}
