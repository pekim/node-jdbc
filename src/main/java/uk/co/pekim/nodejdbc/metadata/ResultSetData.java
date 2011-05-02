/**
 * 
 */
package uk.co.pekim.nodejdbc.metadata;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A representation of a {@link ResultSet} that is suitable for marshalling in
 * to JSON.
 * 
 * @author Mike D Pilsbury
 */
final class ResultSetData {
    private final Map<String, Column> columns = new HashMap<String, Column>();
    private final List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();

    /**
     * Column data.
     * 
     * @author Mike D Pilsbury
     */
    public static final class Column {
        private final String type;

        private Column(final String type) {
            this.type = type;
        }

        /**
         * The column's type. For example "VARCHAR".
         * 
         * @return the type.
         */
        public String getType() {
            return type;
        }
    }

    /**
     * Create a result set.
     * 
     * @param resultSet
     *            the result set to extract data from
     * @throws SQLException
     *             if something went wrong extracting the data.
     */
    ResultSetData(final ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        for (int column = 1; column <= resultSetMetaData.getColumnCount(); column++) {
            String columnName = resultSetMetaData.getColumnName(column);
            String columnTypeName = resultSetMetaData.getColumnTypeName(column);

            columns.put(columnName, new Column(columnTypeName));
        }

        while (resultSet.next()) {
            Map<String, Object> row = new HashMap<String, Object>();

            for (int column = 1; column <= resultSetMetaData.getColumnCount(); column++) {
                String columnName = resultSetMetaData.getColumnName(column);
                Object columnValue = resultSet.getObject(column);

                row.put(columnName, columnValue);
            }

            rows.add(row);
        }
    }

    /**
     * @return the columns
     */
    public Map<String, Column> getColumns() {
        return columns;
    }

    /**
     * @return the rows
     */
    public List<Map<String, Object>> getRows() {
        return rows;
    }
}
