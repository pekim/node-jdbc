/**
 * 
 */
package uk.co.pekim.nodejdbc.server.request;

/**
 * A connection request.
 * 
 * @author Mike D Pilsbury
 */
public class ConnectRequest extends Request {
    private String url;

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
