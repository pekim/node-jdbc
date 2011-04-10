/**
 * 
 */
package uk.co.pekim.nodejdbc.server.request;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.pekim.nodejdbc.server.response.ConnectResponse;
import uk.co.pekim.nodejdbc.server.response.ErrorResponse;
import uk.co.pekim.nodejdbc.server.response.Response;

/**
 * A connection request.
 * 
 * @author Mike D Pilsbury
 */
public class ConnectRequest extends Request {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectRequest.class);

    private String url;

    @Override
    protected Response process() {
        try {
            LOGGER.info("url : " + url);
            Connection connection = DriverManager.getConnection(url);

            return new ConnectResponse();
        } catch (SQLException exception) {
            LOGGER.error("Failed to connect to database with : " + url, exception);
            return new ErrorResponse(exception.getMessage());
        }
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
}
