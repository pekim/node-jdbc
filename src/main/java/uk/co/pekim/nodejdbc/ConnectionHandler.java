/**
 * 
 */
package uk.co.pekim.nodejdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import uk.co.pekim.nodejava.NodeJavaException;
import uk.co.pekim.nodejava.nodehandler.NodeJavaHandler;
import uk.co.pekim.nodejava.nodehandler.NodeJavaRequest;
import uk.co.pekim.nodejava.nodehandler.NodeJavaResponse;

/**
 * Handler for connection operations.
 * 
 * @author Mike D Pilsbury
 */
public class ConnectionHandler implements NodeJavaHandler {
    private final Map<UUID, Connection> connections;

    /**
     * Create connection handler.
     */
    public ConnectionHandler() {
        this.connections = new ConcurrentHashMap<UUID, Connection>();
    }

    @Override
    public Class<? extends NodeJavaRequest> getRequestClass() {
        return ConnectionRequest.class;
    }

    @Override
    public NodeJavaResponse handle(final NodeJavaRequest nodeJavaRequest) {
        ConnectionRequest request = (ConnectionRequest) nodeJavaRequest;
        ConnectionResponse response = new ConnectionResponse();

        try {
            if (StringUtils.isNotEmpty(request.getDriverClassname())) {
                Class.forName(request.getDriverClassname());
            }

            Connection connection = DriverManager.getConnection(request.getUrl());
            UUID id = UUID.randomUUID();

            connections.put(id, connection);

            response.setConnectionIdentifier(id.toString());

            return response;
        } catch (ClassNotFoundException exception) {
            throw new NodeJavaException("Failed to load driver class, " + request.getDriverClassname(), exception);
        } catch (SQLException exception) {
            throw new NodeJavaException("Failed get connection from url, " + request.getUrl(), exception);
        }
    }
}
