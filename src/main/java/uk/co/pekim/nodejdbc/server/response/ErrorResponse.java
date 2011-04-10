/**
 * 
 */
package uk.co.pekim.nodejdbc.server.response;

/**
 * A resposne representing an error.
 * 
 * @author Mike D Pilsbury
 */
public class ErrorResponse extends Response {
    private final String reason;

    /**
     * Create an error response.
     * 
     * @param reason
     *            the error.
     */
    public ErrorResponse(final String reason) {
        super("error");
        this.reason = reason;
    }

    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }
}
