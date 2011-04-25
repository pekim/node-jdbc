/**
 * 
 */
package uk.co.pekim.nodejdbc;

import uk.co.pekim.nodejava.nodehandler.NodeJavaRequest;

/**
 * 
 * 
 * @author Mike D Pilsbury
 */
public class ConnectionRequest implements NodeJavaRequest {
    private String text;
    private int number;

    /**
     * @param text
     *            the text to set
     */
    public void setText(final String text) {
        this.text = text;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param number
     *            the number to set
     */
    public void setNumber(final int number) {
        this.number = number;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }
}
