package com.instructure.canvasapi.model;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by brady on 5/27/14.
 */
public class PollSubmissionResponse extends CanvasComparable<PollSubmissionResponse> {

    private List<PollSubmission> poll_submissions = new ArrayList<PollSubmission>();

    public List<PollSubmission> getPollSubmissions() {
        return poll_submissions;
    }

    public void setPollSubmissions(List<PollSubmission> poll_submissions) {
        this.poll_submissions = poll_submissions;
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
    public int compareTo(PollSubmissionResponse another) {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(poll_submissions);
    }

    public PollSubmissionResponse() {
    }

    private PollSubmissionResponse(Parcel in) {
        in.readTypedList(poll_submissions, PollSubmission.CREATOR);
    }

    public static Creator<PollSubmissionResponse> CREATOR = new Creator<PollSubmissionResponse>() {
        public PollSubmissionResponse createFromParcel(Parcel source) {
            return new PollSubmissionResponse(source);
        }

        public PollSubmissionResponse[] newArray(int size) {
            return new PollSubmissionResponse[size];
        }
    };
}
