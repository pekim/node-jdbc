/**
 * 
 */
package uk.co.pekim.nodejdbc;

import uk.co.pekim.nodejava.nodehandler.NodeJavaResponse;

/**
 * 
 * 
 * @author Mike D Pilsbury
 */
public class ConnectionResponse implements NodeJavaResponse {
    private String text;
    private int incrementedNumber;

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
    public void setIncrementedNumber(final int number) {
        this.incrementedNumber = number;
    }

    /**
     * @return the number
     */
    public int getIncrementedNumber() {
        return incrementedNumber;
    }
}
