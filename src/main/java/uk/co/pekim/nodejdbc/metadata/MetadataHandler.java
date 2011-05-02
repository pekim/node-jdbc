/**
 * 
 */
package uk.co.pekim.nodejdbc.metadata;

import static uk.co.pekim.nodejdbc.connection.Connections.CONNECTIONS;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import uk.co.pekim.nodejava.NodeJavaException;
import uk.co.pekim.nodejava.nodehandler.NodeJavaHandler;
import uk.co.pekim.nodejdbc.metadata.function.AllProceduresAreCallable;
import uk.co.pekim.nodejdbc.metadata.function.Catalogs;
import uk.co.pekim.nodejdbc.metadata.function.DatabaseMajorVersion;
import uk.co.pekim.nodejdbc.metadata.function.MetadataFunction;
import uk.co.pekim.nodejdbc.metadata.function.Schemas;
import uk.co.pekim.nodejdbc.metadata.function.UserName;

/**
 * Handler for getting meta data for a connection.
 * 
 * @author Mike D Pilsbury
 */
public class MetadataHandler implements NodeJavaHandler<MetadataRequest, MetadataResponse> {
    private static final Map<String, MetadataFunction<?>> FUNCTIONS;

    static {
        FUNCTIONS = new HashMap<String, MetadataFunction<?>>();

        FUNCTIONS.put("allProceduresAreCallable", new AllProceduresAreCallable());
        FUNCTIONS.put("catalogs", new Catalogs());
        FUNCTIONS.put("userName", new UserName());
        FUNCTIONS.put("databaseMajorVersion", new DatabaseMajorVersion());
        FUNCTIONS.put("schemas", new Schemas());
    }

    @Override
    public Class<MetadataRequest> getRequestClass() {
        return MetadataRequest.class;
    }

    @Override
    public MetadataResponse handle(final MetadataRequest request) {
        MetadataResponse response = new MetadataResponse();

        try {
            UUID connectionIdentifier = UUID.fromString(request.getConnectionIdentifier());
            Connection connection = CONNECTIONS.get(connectionIdentifier);

            DatabaseMetaData metaData = connection.getMetaData();

            for (String dataName : request.getDataNames()) {
                if (!FUNCTIONS.containsKey(dataName)) {
                    throw new NodeJavaException("Unsupported metadata request : " + dataName);
                }

                response.addData(dataName, FUNCTIONS.get(dataName).execute(metaData));
            }

            return response;
        } catch (SQLException exception) {
            throw new NodeJavaException("Failed to get metadata, " + request.getDataNames(), exception);
        }
    }
}
