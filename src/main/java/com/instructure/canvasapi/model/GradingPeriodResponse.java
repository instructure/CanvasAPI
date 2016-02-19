package com.instructure.canvasapi.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GradingPeriodResponse extends CanvasModel<GradingPeriodResponse>{

    @SerializedName("grading_periods")
    List<GradingPeriod> gradingPeriodList = new ArrayList<>();

    public List<GradingPeriod> getGradingPeriodList() {
        return gradingPeriodList;
    }

    public void setGradingPeriodList(List<GradingPeriod> gradingPeriodList) {
        this.gradingPeriodList = gradingPeriodList;
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
        dest.writeTypedList(gradingPeriodList);
    }

    public GradingPeriodResponse() {
    }

    protected GradingPeriodResponse(Parcel in) {
        this.gradingPeriodList = in.createTypedArrayList(GradingPeriod.CREATOR);
    }

    public static final Creator<GradingPeriodResponse> CREATOR = new Creator<GradingPeriodResponse>() {
        public GradingPeriodResponse createFromParcel(Parcel source) {
            return new GradingPeriodResponse(source);
        }

        public GradingPeriodResponse[] newArray(int size) {
            return new GradingPeriodResponse[size];
        }
    };
}
