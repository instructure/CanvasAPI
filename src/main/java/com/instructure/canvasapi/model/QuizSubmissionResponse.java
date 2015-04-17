package com.instructure.canvasapi.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Copyright (c) 2015 Instructure. All rights reserved.
 */
public class QuizSubmissionResponse extends CanvasModel<QuizSubmissionResponse> implements android.os.Parcelable {

    @SerializedName("quiz_submissions")
    List<QuizSubmission> quizSubmissions = new ArrayList<>();

    public List<QuizSubmission> getQuizSubmissions() {
        return quizSubmissions;
    }

    public void setQuizSubmissions(List<QuizSubmission> quizSubmissions) {
        this.quizSubmissions = quizSubmissions;
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
    public int compareTo(QuizSubmissionResponse another) {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.quizSubmissions);
    }

    public QuizSubmissionResponse() {
    }

    private QuizSubmissionResponse(Parcel in) {
        this.quizSubmissions = new ArrayList<>();
        in.readList(this.quizSubmissions, QuizSubmission.class.getClassLoader());
    }

    public static final Creator<QuizSubmissionResponse> CREATOR = new Creator<QuizSubmissionResponse>() {
        public QuizSubmissionResponse createFromParcel(Parcel source) {
            return new QuizSubmissionResponse(source);
        }

        public QuizSubmissionResponse[] newArray(int size) {
            return new QuizSubmissionResponse[size];
        }
    };
}
