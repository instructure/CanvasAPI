package com.instructure.canvasapi.model;

import android.os.Parcel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CanvasColor extends CanvasModel<CanvasColor> implements android.os.Parcelable {

    private Map<String, String> custom_colors = new HashMap<>();

    public CanvasColor() {
    }

    public CanvasColor(Map<String, String> newColorMap) {
        custom_colors.putAll(newColorMap);
    }

    /**
     * Map is: Map<Context_ID, HexColor>
     * @return
     */
    public Map<String, String> getColors() {
        return custom_colors;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public Date getComparisonDate() {
        return null;
    }

    @Override
    public String getComparisonString() {
        return null;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.custom_colors.size());
        for(Map.Entry<String,String> entry : this.custom_colors.entrySet()){
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    private CanvasColor(Parcel in) {
        int size = in.readInt();
        for(int i = 0; i < size; i++){
            String key = in.readString();
            String value = in.readString();
            this.custom_colors.put(key,value);
        }
    }

    public static final Creator<CanvasColor> CREATOR = new Creator<CanvasColor>() {
        public CanvasColor createFromParcel(Parcel source) {
            return new CanvasColor(source);
        }

        public CanvasColor[] newArray(int size) {
            return new CanvasColor[size];
        }
    };
}
