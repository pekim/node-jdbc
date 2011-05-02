/**
 * 
 */
package uk.co.pekim.nodejdbc.metadata.function;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import uk.co.pekim.nodejdbc.metadata.ResultSetData;

/**
 * Expose metadata.
 * 
 * @author Mike D Pilsbury
 */
public final class Catalogs implements MetadataFunction<ResultSetData> {
    @Override
    public ResultSetData execute(final DatabaseMetaData metaData) throws SQLException {
        return new ResultSetData(metaData.getCatalogs());
    }
}
