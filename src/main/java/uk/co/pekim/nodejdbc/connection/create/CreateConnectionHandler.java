/**
 * 
 */
package uk.co.pekim.nodejdbc.connection.create;

import static uk.co.pekim.nodejdbc.connection.Connections.CONNECTIONS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import uk.co.pekim.nodejava.NodeJavaException;
import uk.co.pekim.nodejava.nodehandler.NodeJavaHandler;

/**
 * Handler for creating a connection.
 * 
 * @author Mike D Pilsbury
 */
public class CreateConnectionHandler implements NodeJavaHandler<CreateConnectionRequest, CreateConnectionResponse> {
    @Override
    public Class<CreateConnectionRequest> getRequestClass() {
        return CreateConnectionRequest.class;
    }

    @Override
    public CreateConnectionResponse handle(final CreateConnectionRequest request) {
        CreateConnectionResponse response = new CreateConnectionResponse();

        try {
            if (StringUtils.isNotEmpty(request.getDriverClassname())) {
                Class.forName(request.getDriverClassname());
            }

            Connection connection = DriverManager.getConnection(request.getUrl());
            UUID id = UUID.randomUUID();

            CONNECTIONS.put(id, connection);

            response.setConnectionIdentifier(id.toString());

            return response;
        } catch (ClassNotFoundException exception) {
            throw new NodeJavaException("Failed to load driver class, " + request.getDriverClassname(), exception);
        } catch (SQLException exception) {
            throw new NodeJavaException("Failed get connection from url, " + request.getUrl(), exception);
        }
    }
}
