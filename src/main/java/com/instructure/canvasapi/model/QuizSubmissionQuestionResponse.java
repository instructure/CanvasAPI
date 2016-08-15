package com.instructure.canvasapi.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Copyright (c) 2015 Instructure. All rights reserved.
 */
public class QuizSubmissionQuestionResponse extends CanvasModel<QuizSubmissionQuestionResponse> {

    @SerializedName("quiz_submission_questions")
    private List<QuizSubmissionQuestion> quizSubmissionQuestions;

    public List<QuizSubmissionQuestion> getQuizSubmissionQuestions() {
        return quizSubmissionQuestions;
    }

    public void setQuizSubmissionQuestions(List<QuizSubmissionQuestion> quizSubmissionQuestions) {
        this.quizSubmissionQuestions = quizSubmissionQuestions;
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
    public int compareTo(QuizSubmissionQuestionResponse quizSubmissionQuestionResponse) {
        return 0;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.quizSubmissionQuestions);
    }

    public QuizSubmissionQuestionResponse() {
    }

    private QuizSubmissionQuestionResponse(Parcel in) {
        this.quizSubmissionQuestions = new ArrayList<>();
        in.readList(this.quizSubmissionQuestions, QuizSubmissionQuestion.class.getClassLoader());
    }

    public static final Creator<QuizSubmissionQuestionResponse> CREATOR = new Creator<QuizSubmissionQuestionResponse>() {
        public QuizSubmissionQuestionResponse createFromParcel(Parcel source) {
            return new QuizSubmissionQuestionResponse(source);
        }

        public QuizSubmissionQuestionResponse[] newArray(int size) {
            return new QuizSubmissionQuestionResponse[size];
        }
    };
}
