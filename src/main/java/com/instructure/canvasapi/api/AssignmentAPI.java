package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.Assignment;
import com.instructure.canvasapi.model.AssignmentGroup;
import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.RubricCriterion;
import com.instructure.canvasapi.model.ScheduleItem;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import com.instructure.canvasapi.utilities.ExhaustiveBridgeCallback;

import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Brady Larson on 9/5/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class AssignmentAPI {

    public enum ASSIGNMENT_BUCKET_TYPE { PAST, OVERDUE, UNDATED, UNGRADED, UPCOMING, FUTURE;
        public static String getEventTypeName(ASSIGNMENT_BUCKET_TYPE eventType) {
            switch (eventType) {
                case PAST:
                    return "past";
                case OVERDUE:
                    return "overdue";
                case UNDATED:
                    return "undated";
                case UNGRADED:
                    return "ungraded";
                case UPCOMING:
                    return "upcoming";
                case FUTURE:
                    return "future";

            }
            return "upcoming";
        }
    }


    private static String getAssignmentCacheFilename(long courseID, long assignmentID) {
        return "/courses/" + courseID + "/assignments/" + assignmentID;
    }

    private static String getAssignmentsListCacheFilename(long courseID) {
        return "/courses/" + courseID + "/assignments?include=submission";
    }

    private static String getAssignmentGroupsListCacheFilename(long courseID) {
        return  "/courses/" + courseID + "/assignments_groups";
    }
    private static String getAssignmentGroupsListWithAssignmentsCacheFilename(long courseID) {
        return  "/courses/" + courseID + "/assignments_groups?include=assignments";
    }

    private static String getAssignmentsListWithBucketCacheFilename(long courseID, ASSIGNMENT_BUCKET_TYPE bucket_type) {
        return "/courses/" + courseID + "/assignments?bucket=" + ASSIGNMENT_BUCKET_TYPE.getEventTypeName(bucket_type);
    }

    public interface AssignmentsInterface {
        @GET("/courses/{course_id}/assignments/{assignmentid}?include[]=submission&include[]=rubric_assessment&needs_grading_count_by_section=true&include[]=all_dates")
        void getAssignment(@Path("course_id") long course_id, @Path("assignmentid") long assignment_id, Callback<Assignment> callback);

        @GET("/courses/{course_id}/assignments?include[]=submission&include[]=rubric_assessment&needs_grading_count_by_section=true&include[]=all_dates")
        void getAssignmentsList(@Path("course_id") long course_id, Callback<Assignment[]> callback);

        @GET("/{next}")
        void getNextPageAssignmentsList(@Path(value = "next", encode = false) String nextURL, Callback<Assignment[]>callback);

        @GET("/courses/{course_id}/assignment_groups")
        void getAssignmentGroupList(@Path("course_id") long course_id, Callback<AssignmentGroup[]> callback);

        @GET("/courses/{course_id}/assignment_groups?include[]=assignments&include[]=discussion_topic&override_assignment_dates=true")
        void getAssignmentGroupListWithAssignments(@Path("course_id") long course_id, Callback<AssignmentGroup[]> callback);

        @GET("/calendar_events/{event_id}")
        void getCalendarEvent(@Path("event_id") long event_id, Callback<ScheduleItem> callback);

        @GET("/calendar_events?start_date=1990-01-01&end_date=2099-12-31")
        void getCalendarEvents(@Query("context_codes[]") String context_id, Callback<ScheduleItem[]> callback);

        @PUT("/courses/{course_id}/assignments/{assignment_id}")
        void editAssignment(@Path("course_id") long courseId, @Path("assignment_id") long assignmentId,
                            @Query("assignment[name]") String assignmentName,
                            @Query("assignment[assignment_group_id]") Long assignmentGroupId,
                            @Query(value = "assignment[submission_types][]", encodeValue = false) String submissionTypes,
                            @Query("assignment[peer_reviews]") Integer hasPeerReviews,
                            @Query("assignment[group_category_id]") Long groupId,
                            @Query("assignment[points_possible]") Double pointsPossible,
                            @Query("assignment[grading_type]") String gradingType,
                            @Query("assignment[due_at]") String dueAt,
                            @Query("assignment[description]") String description,
                            @Query("assignment[notify_of_update]") Integer notifyOfUpdate,
                            @Query("assignment[unlock_at]")String unlockAt,
                            @Query("assignment[lock_at]") String lockAt,
                            @Query(value = "assignment[html_url]", encodeValue = false) String htmlUrl,
                            @Query(value = "assignment[url]", encodeValue = false) String url,
                            @Query("assignment[quiz_id]") Long quizId,
                            @Query(value = "assignment[muted]", encodeValue = false) boolean isMuted,
                            Callback<Assignment> callback);

        @GET("/courses/{course_id}/assignments?include[]=submission&include[]=rubric_assessment&needs_grading_count_by_section=true&include[]=all_dates")
        void getAssignmentsWithBucket(@Path("course_id") long course_id, @Query("bucket") String bucket_type, Callback<Assignment[]> callback);

    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static AssignmentsInterface buildInterface(CanvasCallback<?> callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext);
        return restAdapter.create(AssignmentsInterface.class);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getAssignment(long courseID, long assignmentID, final CanvasCallback<Assignment> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        callback.readFromCache(getAssignmentCacheFilename(courseID, assignmentID));
        buildInterface(callback, null).getAssignment(courseID, assignmentID, callback);
    }

    public static void getAllAssignmentsWithoutPagination(long courseID, final CanvasCallback<Assignment[]> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        CanvasCallback<Assignment[]> bridge = new ExhaustiveBridgeCallback<>(callback, new ExhaustiveBridgeCallback.ExhaustiveBridgeEvents() {
            @Override
            public void performApiCallWithExhaustiveCallback(CanvasCallback bridgeCallback, String nextURL) {
                if(callback.isCancelled()) { return; }

                AssignmentAPI.getNextPageAssignmentsList(bridgeCallback, nextURL);
            }

            @Override
            public Class classType() {
                return Assignment.class;
            }
        });

        callback.readFromCache(getAssignmentsListCacheFilename(courseID));
        buildInterface(callback, null).getAssignmentsList(courseID, bridge);
    }

    public static void getAssignmentsList(long courseID, final CanvasCallback<Assignment[]> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        callback.readFromCache(getAssignmentsListCacheFilename(courseID));
        buildInterface(callback, null).getAssignmentsList(courseID, callback);
    }

    public static void getNextPageAssignmentsList(CanvasCallback<Assignment[]> callback, String nextURL){
        if (APIHelpers.paramIsNull(callback, nextURL)) return;

        callback.setIsNextPage(true);
        buildInterface(callback, null).getNextPageAssignmentsList(nextURL, callback);
    }

    public static void getAssignmentGroupsList(long courseID, final CanvasCallback<AssignmentGroup[]> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        callback.readFromCache(getAssignmentGroupsListCacheFilename(courseID));
        buildInterface(callback, null).getAssignmentGroupList(courseID, callback);
    }

    public static void getAssignmentGroupsListWithAssignments(long courseID, final CanvasCallback<AssignmentGroup[]> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        callback.readFromCache(getAssignmentGroupsListWithAssignmentsCacheFilename(courseID));
        buildInterface(callback, null).getAssignmentGroupListWithAssignments(courseID, callback);
    }

    /*
    * @deprecated Use editAssignment(Assignment editedAssignment, Boolean notifyOfUpdate, final CanvasCallback<Assignment> callback)
    * @param assignment (Required)
    * @param callback (Required)
    * @param assignmentName (Optional)
    * @param assignmentGroupId (Optional)
    * @param submissionTypes (Optional)
    * @param hasPeerReviews  (Optional)
    * @param groupId (Optional)
    * @param pointsPossible (Optional)
    * @param gradingType (Optional)
    * @param dateDueAt (Optional)
    * @param description (Optional)
    * @param notifyOfUpdate (Optional)
    * @param dateUnlockAt (Optional)
    * @param dateLockAt (Optional)
    *
     */
    @Deprecated
    public static void editAssignment(Assignment assignment, String assignmentName, Long assignmentGroupId, Assignment.SUBMISSION_TYPE[] submissionTypes,
                                       Boolean hasPeerReviews, Long groupId, Double pointsPossible, Assignment.GRADING_TYPE gradingType, Date dateDueAt, String description, boolean notifyOfUpdate,
                                       Date dateUnlockAt, Date dateLockAt, final CanvasCallback<Assignment> callback){

        if(APIHelpers.paramIsNull(callback, assignment)){return;}

        String dueAt = APIHelpers.dateToString(dateDueAt);
        String unlockAt = APIHelpers.dateToString(dateUnlockAt);
        String lockAt = APIHelpers.dateToString(dateLockAt);
        String newSubmissionTypes = submissionTypeArrayToAPIQueryString(submissionTypes);
        String newGradingType = Assignment.gradingTypeToAPIString(gradingType);

        Integer newHasPeerReviews = (hasPeerReviews == null) ? null : APIHelpers.booleanToInt(hasPeerReviews);
        Integer newNotifyOfUpdate = APIHelpers.booleanToInt(notifyOfUpdate);

        buildInterface(callback, null).editAssignment(assignment.getCourseId(), assignment.getId(), assignmentName, assignmentGroupId, newSubmissionTypes, newHasPeerReviews,
                                                        groupId, pointsPossible, newGradingType,dueAt,description,newNotifyOfUpdate,unlockAt,lockAt,null, null, null, assignment.isMuted(), callback );

    }
    public static void editAssignment(Assignment editedAssignment, Boolean notifyOfUpdate, final CanvasCallback<Assignment> callback){


      Assignment.SUBMISSION_TYPE[] arrayOfSubmissionTypes = editedAssignment.getSubmissionTypes().toArray(new Assignment.SUBMISSION_TYPE[editedAssignment.getSubmissionTypes().size()]);
      String[] arrayOfAllowedExtensions = editedAssignment.getAllowedExtensions().toArray(new String[editedAssignment.getAllowedExtensions().size()]);
        editAssignment(editedAssignment.getCourseId(), editedAssignment.getId(), editedAssignment.getName(), editedAssignment.getDescription(), arrayOfSubmissionTypes,
                editedAssignment.getDueDate(), editedAssignment.getPointsPossible(), editedAssignment.getGradingType(), editedAssignment.getHtmlUrl(), editedAssignment.getUrl(),
                editedAssignment.getQuizId(), editedAssignment.getRubric(), arrayOfAllowedExtensions, editedAssignment.getAssignmentGroupId(), editedAssignment.hasPeerReviews(),
                editedAssignment.getlockAtDate(), editedAssignment.getUnlockAt(), null, notifyOfUpdate, editedAssignment.isMuted(), callback);
    }
    private static void editAssignment(long courseId, long assignmentId, String name, String description, Assignment.SUBMISSION_TYPE[] submissionTypes,
                                       Date dueAt, double pointsPossible, Assignment.GRADING_TYPE gradingType, String htmlUrl, String url,
                                       Long quizId, List<RubricCriterion> rubric, String[] allowedExtensions, Long assignmentGroupId, Boolean hasPeerReviews,
                                       Date lockAt, Date unlockAt, Long groupCategoryId, boolean notifyOfUpdate, boolean isMuted, final CanvasCallback<Assignment> callback){

        String stringDueAt = APIHelpers.dateToString(dueAt);
        String stringUnlockAt = APIHelpers.dateToString(unlockAt);
        String stringLockAt = APIHelpers.dateToString(lockAt);
        String newSubmissionTypes = submissionTypeArrayToAPIQueryString(submissionTypes);
        String newGradingType = Assignment.gradingTypeToAPIString(gradingType);

        Integer newHasPeerReviews = (hasPeerReviews == null) ? null : APIHelpers.booleanToInt(hasPeerReviews);
        Integer newNotifyOfUpdate = APIHelpers.booleanToInt(notifyOfUpdate);

        buildInterface(callback, null).editAssignment(courseId, assignmentId, name, assignmentGroupId, newSubmissionTypes, newHasPeerReviews,
                groupCategoryId, pointsPossible, newGradingType, stringDueAt, description, newNotifyOfUpdate, stringUnlockAt, stringLockAt, htmlUrl, url, quizId, isMuted, callback);

    }

    public static void getAssignmentsWithBucket(long courseID, ASSIGNMENT_BUCKET_TYPE bucket_type, final CanvasCallback<Assignment[]> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        callback.readFromCache(getAssignmentsListWithBucketCacheFilename(courseID, bucket_type));
        buildInterface(callback, null).getAssignmentsWithBucket(courseID, ASSIGNMENT_BUCKET_TYPE.getEventTypeName(bucket_type), callback);
    }


    /*
    *Converts a SUBMISSION_TYPE[] to a queryString for the API
     */
    private static String submissionTypeArrayToAPIQueryString(Assignment.SUBMISSION_TYPE[] submissionTypes){
        if(submissionTypes == null || submissionTypes.length == 0){
            return null;
        }
        String submissionTypesQueryString =  "";

        for(int i =0; i < submissionTypes.length; i++){
            submissionTypesQueryString +=  Assignment.submissionTypeToAPIString(submissionTypes[i]);

            if(i < submissionTypes.length -1){
                submissionTypesQueryString += "&assignment[submission_types][]=";
            }
        }

        return submissionTypesQueryString;
    }

}
