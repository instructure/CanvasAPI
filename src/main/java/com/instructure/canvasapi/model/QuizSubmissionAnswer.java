package com.instructure.canvasapi.model;

import android.os.Parcel;

import java.util.Date;

/**
 * Copyright (c) 2015 Instructure. All rights reserved.
 */
public class QuizSubmissionAnswer extends CanvasModel<QuizSubmissionAnswer> {

    //id of the answer
    private long id;
    private String text;
    private String html;
    private String comments;
    private int weight;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
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
    public int compareTo(QuizSubmissionAnswer quizSubmissionAnswer) {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.text);
        dest.writeString(this.html);
        dest.writeString(this.comments);
        dest.writeInt(this.weight);
    }

    public QuizSubmissionAnswer() {
    }

    private QuizSubmissionAnswer(Parcel in) {
        this.id = in.readLong();
        this.text = in.readString();
        this.html = in.readString();
        this.comments = in.readString();
        this.weight = in.readInt();
    }

    public static final Creator<QuizSubmissionAnswer> CREATOR = new Creator<QuizSubmissionAnswer>() {
        public QuizSubmissionAnswer createFromParcel(Parcel source) {
            return new QuizSubmissionAnswer(source);
        }

        public QuizSubmissionAnswer[] newArray(int size) {
            return new QuizSubmissionAnswer[size];
        }
    };
}
