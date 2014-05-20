package com.instructure.canvasapi.model;

import android.os.Parcel;

import com.instructure.canvasapi.utilities.APIHelpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Brady Larson
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class Submission extends CanvasModel<Submission>{

    private long id;
	private String grade;
	private double score;
	private String submitted_at;

	private ArrayList<SubmissionComment> submission_comments = new ArrayList<SubmissionComment>();
	private Date commentCreated;
	private String mediaContentType;
	private String mediaCommentUrl;
	private String mediaCommentDisplay;
	private ArrayList<Submission> submission_history = new ArrayList<Submission>();
	private ArrayList<Attachment> attachments = new ArrayList<Attachment>();
	private String body;
    private HashMap<String,RubricCriterionRating> rubric_assessment;
	private boolean grade_matches_current_submission;
	private String workflow_state;
	private String submission_type;
	private String preview_url;
	private String url;

    //Conversation Stuff
    private Assignment assignment;
    private long user_id;
    private long grader_id;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
	public ArrayList<SubmissionComment> getComments() {
		return submission_comments;
	}
	public void setComment(ArrayList<SubmissionComment> comment) {
		this.submission_comments = comment;
	}
	public Date getCommentCreated() {
		return commentCreated;
	}
	public void setCommentCreated(Date commentCreated) {
		this.commentCreated = commentCreated;
	}
	public String getMediaContentType() {
		return mediaContentType;
	}
	public void setMediaContentType(String mediaContentType) {
		this.mediaContentType = mediaContentType;
	}
	public String getMediaCommentUrl() {
		return mediaCommentUrl;
	}
	public void setMediaCommentUrl(String mediaCommentUrl) {
		this.mediaCommentUrl = mediaCommentUrl;
	}
	public String getMediaCommentDisplay() {
		return mediaCommentDisplay;
	}
	public void setMediaCommentDisplay(String mediaCommentDisplay) {
		this.mediaCommentDisplay = mediaCommentDisplay;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}

    public Assignment getAssignment(){
        return assignment;
    }

    public long getGraderID(){
        if(grader_id != 0){
            return grader_id;
        }
        else{
            return user_id;
        }
    }


	public Date getSubmitDate() {
        if(submitted_at == null) {
            return null;
        }
		return APIHelpers.stringToDate(submitted_at);
	}
	public void setSubmitDate(String submitDate) {
        if(submitDate == null) {
            this.submitted_at = null;
        }
        else {
		    this.submitted_at = submitDate;
        }
	}
	public void setSubmissionHistory(ArrayList<Submission> history) {
	    this.submission_history = history;
	}
	public ArrayList<Submission> getSubmissionHistory() {
	    return submission_history;
	}
	public ArrayList<Attachment> getAttachments() {
        return attachments;
    } 
    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public boolean isGradeMatchesCurrentSubmission() {
        return grade_matches_current_submission;
    }
    public void setGradeMatchesCurrentSubmission(
            boolean gradeMatchesCurrentSubmission) {
        this.grade_matches_current_submission = gradeMatchesCurrentSubmission;
    }
    public String getWorkflowState() {
        return workflow_state;
    }
    public void setWorkflowState(String workflowState) {
        this.workflow_state = workflowState;
    }
    public String getSubmissionType() {
        return submission_type;
    }
    public void setSubmissionType(String submissionType) {
        this.submission_type = submissionType;
    }
    public String getPreviewUrl() {
        return preview_url;
    }
    public void setPreviewUrl(String previewUrl) {
        this.preview_url = previewUrl;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public RubricAssessment getRubricAssessment() {
        RubricAssessment assessment = new RubricAssessment();
        ArrayList<RubricCriterionRating> ratings = new ArrayList<RubricCriterionRating>();
        if (rubric_assessment != null) {
            for (Map.Entry<String, RubricCriterionRating> entry : rubric_assessment.entrySet()) {
                RubricCriterionRating rating = entry.getValue();
                rating.setCriterionId(entry.getKey());
                ratings.add(rating);

            }
        }
        assessment.setRatings(ratings);
        return assessment;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Required Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public Date getComparisonDate() {
        return getSubmitDate();
    }

    @Override
    public String getComparisonString() {
        return getSubmissionType();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public Submission() {}

    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    public ArrayList<Long> getUserIds()
    {
        ArrayList<Long> ids = new ArrayList<Long>();

        for(int i = 0; i < submission_comments.size(); i++)
        {
            ids.add(submission_comments.get(i).getAuthorID());
        }

        return ids;
    }
    /*
     * Submissions will have dummy submissions if they grade an assignment with no actual submissions.
     * We want to see if any are not dummy submissions
     */
    public boolean hasRealSubmission(){

        for(Submission submission : submission_history){

            if(submission.getSubmissionType() != null){
                return true;
            }
        }

        return false;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.grade);
        dest.writeDouble(this.score);
        dest.writeString(this.submitted_at);
        dest.writeSerializable(this.submission_comments);
        dest.writeLong(commentCreated != null ? commentCreated.getTime() : -1);
        dest.writeString(this.mediaContentType);
        dest.writeString(this.mediaCommentUrl);
        dest.writeString(this.mediaCommentDisplay);
        dest.writeSerializable(this.submission_history);
        dest.writeSerializable(this.attachments);
        dest.writeString(this.body);
        dest.writeSerializable(this.rubric_assessment);
        dest.writeByte(grade_matches_current_submission ? (byte) 1 : (byte) 0);
        dest.writeString(this.workflow_state);
        dest.writeString(this.submission_type);
        dest.writeString(this.preview_url);
        dest.writeString(this.url);
        dest.writeParcelable(this.assignment, 0);
        dest.writeLong(this.user_id);
        dest.writeLong(this.grader_id);
    }

    private Submission(Parcel in) {
        this.id = in.readLong();
        this.grade = in.readString();
        this.score = in.readDouble();
        this.submitted_at = in.readString();
        this.submission_comments = (ArrayList<SubmissionComment>) in.readSerializable();
        long tmpCommentCreated = in.readLong();
        this.commentCreated = tmpCommentCreated == -1 ? null : new Date(tmpCommentCreated);
        this.mediaContentType = in.readString();
        this.mediaCommentUrl = in.readString();
        this.mediaCommentDisplay = in.readString();
        this.submission_history = (ArrayList<Submission>) in.readSerializable();
        this.attachments = (ArrayList<Attachment>) in.readSerializable();
        this.body = in.readString();
        this.rubric_assessment =(HashMap<String,RubricCriterionRating>) in.readSerializable();
        this.grade_matches_current_submission = in.readByte() != 0;
        this.workflow_state = in.readString();
        this.submission_type = in.readString();
        this.preview_url = in.readString();
        this.url = in.readString();
        this.assignment = in.readParcelable(((Object) assignment).getClass().getClassLoader());
        this.user_id = in.readLong();
        this.grader_id = in.readLong();
    }

    public static Creator<Submission> CREATOR = new Creator<Submission>() {
        public Submission createFromParcel(Parcel source) {
            return new Submission(source);
        }

        public Submission[] newArray(int size) {
            return new Submission[size];
        }
    };
}
