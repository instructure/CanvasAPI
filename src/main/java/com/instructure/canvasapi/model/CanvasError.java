package com.instructure.canvasapi.model;

/**
 * Created by Nathan Button on 1/21/14.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class CanvasError {
    private String status;
    private Error error;
    private String message;
    private String formattedStatus;
    private String errorMessage;

    public static CanvasError createError(String status, String message) {
        CanvasError error = new CanvasError();
        error.status = status;
        error.message = message;

        return error;
    }

    public String getStatus(){
        if (formattedStatus == null) {
            if (status != null && status.length() > 1) {
                formattedStatus = status.substring(0, 1).toUpperCase() + status.substring(1);
            } else {
                formattedStatus = "";
            }
        }
        return formattedStatus;
    }

    public String getMessage(){
        if(message != null){
            return message;
        }

        return "";
    }

    public Error getError(){
        return error;
    }

    @Override
    public String toString() {
        if (errorMessage == null) {
            if (error != null) {
                errorMessage = getError().getMessage();
            } else {
                errorMessage = getMessage();
            }

            if (getStatus().length() > 0) {
                errorMessage = getStatus() + ": " + errorMessage;
            }
        }

        return errorMessage;
    }


    public class Error {
        private String message;
        private String formattedMessage;

        public String getMessage() {
            if (formattedMessage == null) {
                if (message != null && message.length() > 1) {
                    formattedMessage = message.substring(0, 1).toUpperCase() + message.substring(1) + ".";
                } else {
                    formattedMessage = "";
                }
            }
            return formattedMessage;
        }


        @Override
        public String toString() {
            return getMessage();
        }
    }
}
