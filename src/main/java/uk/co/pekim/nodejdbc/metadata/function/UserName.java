/**
 * 
 */
package uk.co.pekim.nodejdbc.metadata.function;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * Expose metadata.
 * 
 * @author Mike D Pilsbury
 */
public final class UserName implements MetadataFunction<String> {
    @Override
    public String execute(final DatabaseMetaData metaData) throws SQLException {
        return metaData.getUserName();
    }
}
