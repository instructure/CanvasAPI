package com.instructure.canvasapi.model;

/**
 * Created by Nathan Button on 1/21/14.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class CanvasError {
    private String status;
    private Error errors;
    private String message;
    private String formattedStatus;
    private String errorMessage;

    public String getStatus(){
        if (formattedStatus == null) {
            if (status != null & status.length() > 1) {

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

    public Error getErrors(){
        return errors;
    }

    @Override
    public String toString() {
        if (errorMessage == null) {
            if (getStatus().equals("") && errors != null && errors.getMessage().equals("")) {

                errorMessage = getStatus() + ": " + getErrors().getMessage();


            }
        } else {
            errorMessage = "";
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
