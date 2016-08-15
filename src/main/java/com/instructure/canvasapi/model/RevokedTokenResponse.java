package com.instructure.canvasapi.model;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright (c) 2016 Instructure. All rights reserved.
 *
 * This is a response body for use in the ParentErrorDelegate class in the parentApp
 */
public class RevokedTokenResponse {

    @SerializedName("student_id")
    public String studentId;

    @SerializedName("parent_id")
    public String parentId;

    @SerializedName("student_name")
    public String studentName;

    @SerializedName("student_domain")
    public String studentDomain;

    @SerializedName("domain_name")
    public String domainName;

    @SerializedName("sortable_name")
    public String sortableName;

    @SerializedName("short_name")
    public String shortName;

    @SerializedName("avatar_url")
    public String avatarUrl;

    @SerializedName("locale")
    public String locale;

}
