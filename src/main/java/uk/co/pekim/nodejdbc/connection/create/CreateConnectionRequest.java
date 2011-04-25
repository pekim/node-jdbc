/**
 * 
 */
package uk.co.pekim.nodejdbc.connection.create;

import uk.co.pekim.nodejava.nodehandler.NodeJavaRequest;

/**
 * 
 * 
 * @author Mike D Pilsbury
 */
public class CreateConnectionRequest implements NodeJavaRequest {
    private String url;
    private String driverClassname;

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * @return the driverClassname
     */
    public String getDriverClassname() {
        return driverClassname;
    }

    /**
     * @param driverClassname
     *            the driverClassname to set
     */
    public void setDriverClassname(final String driverClassname) {
        this.driverClassname = driverClassname;
    }

}
