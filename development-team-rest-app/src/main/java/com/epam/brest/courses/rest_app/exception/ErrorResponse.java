package com.epam.brest.courses.rest_app.exception;

import java.util.Arrays;
import java.util.List;

/**
 * ErrorResponse.
 */
public class ErrorResponse {

    /**
     * Constructor without arguments.
     */
    public ErrorResponse() {
        super();
    }

    /**
     * Constructor with arguments: String message, List<String> details.
     */
    public ErrorResponse(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }

    /**
     * String message.
     */
    private String message;


    /**
     * List<String> details.
     */
    private List<String> details;

    /**
     * Constructor with arguments: String message, Exception ex.
     */
    public ErrorResponse(String message, Exception ex) {
        super();
        this.message = message;
        if (ex != null) {
            this.details = Arrays.asList(ex.getMessage());
        }
    }
    /**
     * Returns <code>String</code> representation of this message.
     *
     * @return message.
     */
    public String getMessage() {
        return message;
    }


    /**
     * Sets the message.
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * Returns <code>List<String> details</></code> representation of this list of message.
     *
     * @return details ErrorResponse details .
     */
    public List<String> getDetails() {
        return details;
    }


    /**
     * Sets the list of details.
     *
     * @param details ErrorResponse details.
     */
    public void setDetails(List<String> details) {
        this.details = details;
    }
}
