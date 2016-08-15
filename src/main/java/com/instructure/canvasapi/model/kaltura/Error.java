package com.instructure.canvasapi.model.kaltura;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;

/**
 * Created by nbutton on 5/29/14.
 */

@Element
public class Error implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.message);
    }

    public Error() {
    }

    private Error(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
    }

    public static Parcelable.Creator<Error> CREATOR = new Parcelable.Creator<Error>() {
        public Error createFromParcel(Parcel source) {
            return new Error(source);
        }

        public Error[] newArray(int size) {
            return new Error[size];
        }
    };
}
