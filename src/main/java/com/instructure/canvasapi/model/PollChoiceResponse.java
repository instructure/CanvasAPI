package com.instructure.canvasapi.model;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by brady on 5/27/14.
 */
public class PollChoiceResponse extends CanvasComparable<PollChoiceResponse> implements android.os.Parcelable {

    List<PollChoice> poll_choices = new ArrayList<PollChoice>();

    public List<PollChoice> getPollChoices() {
        return poll_choices;
    }

    public void setPollChoices(List<PollChoice> poll_choices) {
        this.poll_choices = poll_choices;
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
    public int compareTo(PollChoiceResponse another) {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(poll_choices);
    }

    public PollChoiceResponse() {
    }

    private PollChoiceResponse(Parcel in) {
        in.readTypedList(poll_choices, PollChoice.CREATOR);
    }

    public static Creator<PollChoiceResponse> CREATOR = new Creator<PollChoiceResponse>() {
        public PollChoiceResponse createFromParcel(Parcel source) {
            return new PollChoiceResponse(source);
        }

        public PollChoiceResponse[] newArray(int size) {
            return new PollChoiceResponse[size];
        }
    };
}
