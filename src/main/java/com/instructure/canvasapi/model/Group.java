package com.instructure.canvasapi.model;


import java.util.Date;

/**
 * @author Josh Ruesch
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class Group extends CanvasContext {

    private long id;

    private String name;
    private String description;
    private String avatar_url;

    private boolean is_public;
    private boolean followed_by_user;

    private int members_count;

    // * If "parent_context_auto_join", anyone can join and will be
    //   automatically accepted.
    // * If "parent_context_request", anyone  can request to join, which
    //   must be approved by a group moderator.
    // * If "invitation_only", only those how have received an
    //   invitation my join the group, by accepting that invitation.
    private String join_level;

    //TODO:
    private String context_type;

    //At most, ONE of these will be set.
    private long course_id;
    private long account_id;

    // Certain types of groups have special role designations. Currently,
    // these include: "communities", "student_organized", and "imported".
    // Regular course/account groups have a role of null.
    private String role;

    private long group_category_id;

    private long storage_quota_mb;

    public enum JoinLevel {Automatic, Request, Invitation, Unknown}
    public enum GroupRole {Community, Student, Imported, Course}
    public enum GroupContext {Course,  Account, Other}
    private boolean isFacingForward = true;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public long getId() { return id; }

    @Override
    public Date getComparisonDate() {
        return null;
    }

    @Override
    public String getComparisonString() {
        return name;
    }

    @Override
    public Type getType() {return Type.GROUP;}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAvatarUrl() {
        return avatar_url;
    }

    public boolean isPublic() {
        return is_public;
    }

    public boolean isFollowedByUser() {
        return followed_by_user;
    }

    public int getMembersCount() {
        return members_count;
    }

    /**
     * Not linked or updated from the API, internal use only.
     * @return
     */
    public boolean isFacingForward() {
        return isFacingForward;
    }
    /**
     * Not linked or updated from the API, internal use only.
     * @return
     */
    public void setFacingForward(boolean isFacingForward) {
        this.isFacingForward = isFacingForward;
    }

    public JoinLevel getJoinLevel() {

        // * If "parent_context_auto_join", anyone can join and will be
        //   automatically accepted.
        // * If "parent_context_request", anyone  can request to join, which
        //   must be approved by a group moderator.
        // * If "invitation_only", only those how have received an
        //   invitation my join the group, by accepting that invitation.

        if("parent_context_auto_join".equalsIgnoreCase(join_level)){
            return JoinLevel.Automatic;
        } else if ("parent_context_request".equalsIgnoreCase(join_level)){
            return JoinLevel.Request;
        } else if ("invitation_only".equalsIgnoreCase(join_level)){
            return JoinLevel.Invitation;
        }

        return JoinLevel.Unknown;
    }

    public GroupContext getContextType() {

        if("course".equalsIgnoreCase(context_type)){
            return GroupContext.Course;
        } else if ("account".equalsIgnoreCase(context_type)){
            return GroupContext.Account;
        }
        return GroupContext.Other;
    }

    public long getCourseId() {
        return course_id;
    }

    public long getAccountId() {
        return account_id;
    }

    public GroupRole getRole() {
        // Certain types of groups have special role designations. Currently,
        // these include: "communities", "student_organized", and "imported".
        // Regular course/account groups have a role of null.

        if("communities".equalsIgnoreCase(role)){
            return GroupRole.Community;
        } else if ("student_organized".equals(role)){
            return GroupRole.Student;
        } else if ("imported".equals(role)){
            return GroupRole.Imported;
        }

        return GroupRole.Course;
    }

    public long getGroupCategoryId() {
        return group_category_id;
    }

    public long getStorageQuotaMB() {
        return storage_quota_mb;
    }
}