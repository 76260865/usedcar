package com.jason.usedcar.request;

/**
 * @author t77yq @2014-08-02.
 */
public class SuggestionRequest extends Request {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
