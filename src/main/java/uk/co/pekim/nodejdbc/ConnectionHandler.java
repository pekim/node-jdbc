/**
 * 
 */
package uk.co.pekim.nodejdbc;

import uk.co.pekim.nodejava.nodehandler.NodeJavaHandler;
import uk.co.pekim.nodejava.nodehandler.NodeJavaRequest;
import uk.co.pekim.nodejava.nodehandler.NodeJavaResponse;

/**
 * Test handler, that echoes its request.
 * 
 * <p>
 * Useful for both unit and integration tests.
 * </p>
 * 
 * @author Mike D Pilsbury
 */
public class ConnectionHandler implements NodeJavaHandler {
    @Override
    public Class<? extends NodeJavaRequest> getRequestClass() {
        return ConnectionRequest.class;
    }

    @Override
    public NodeJavaResponse handle(final NodeJavaRequest nodeJavaRequest) {
        ConnectionRequest request = (ConnectionRequest) nodeJavaRequest;
        ConnectionResponse response = new ConnectionResponse();

        response.setText(request.getText());
        response.setIncrementedNumber(request.getNumber() + 1);

        return response;
    }
}
