package com.instructure.canvasapi.model.kaltura;

import org.simpleframework.xml.Element;

/**
 * Created by nbutton on 5/29/14.
 */

@Element
public class Error {
    @Element (required = false)
    private String code;
    @Element (required = false)
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Error{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
