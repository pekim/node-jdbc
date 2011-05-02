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

/**
 * Handler for getting meta data for a connection.
 * 
 * @author Mike D Pilsbury
 */
public class MetadataHandler implements NodeJavaHandler<MetadataRequest, MetadataResponse> {
    private static final Map<String, Function<?>> FUNCTIONS;

    static {
        FUNCTIONS = new HashMap<String, MetadataHandler.Function<?>>();

        FUNCTIONS.put("allProceduresAreCallable", new AllProceduresAreCallable());
        FUNCTIONS.put("catalogs", new Catalogs());
        FUNCTIONS.put("userName", new UserName());
        FUNCTIONS.put("databaseMajorVersion", new DatabaseMajorVersion());
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

    private interface Function<T> {
        T execute(DatabaseMetaData metaData) throws SQLException;
    }

    private static final class AllProceduresAreCallable implements Function<Boolean> {
        @Override
        public Boolean execute(final DatabaseMetaData metaData) throws SQLException {
            return metaData.allProceduresAreCallable();
        }
    }

    private static final class UserName implements Function<String> {
        @Override
        public String execute(final DatabaseMetaData metaData) throws SQLException {
            return metaData.getUserName();
        }
    }

    private static final class DatabaseMajorVersion implements Function<Integer> {
        @Override
        public Integer execute(final DatabaseMetaData metaData) throws SQLException {
            return metaData.getDatabaseMajorVersion();
        }
    }

    private static final class Catalogs implements Function<ResultSetData> {
        @Override
        public ResultSetData execute(final DatabaseMetaData metaData) throws SQLException {
            return new ResultSetData(metaData.getCatalogs());
        }
    }
}
