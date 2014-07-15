import com.google.gson.Gson;
import com.instructure.canvasapi.model.Assignment;
import com.instructure.canvasapi.model.LockInfo;
import com.instructure.canvasapi.model.RubricCriterion;
import com.instructure.canvasapi.model.RubricCriterionRating;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(RobolectricGradleTestRunner.class)
public class AssignmentUnitTest extends Assert {

    @Test
    public void testAssignment() {
        Gson gson = CanvasRestAdapter.getGSONParser();
        Assignment assignment = gson.fromJson(assignmentJSON, Assignment.class);
        Assignment rubricAssignment = gson.fromJson(rubricAssignmentJSON, Assignment.class);
        Assignment lockInfoAssignment = gson.fromJson(lockInfoJSON, Assignment.class);

        assertTrue(assignment.getId() > 0);

        assertEquals(assignment.getPointsPossible(), 30.0);

        assertEquals(assignment.getSubmissionTypes().size(), 3);
        assertTrue(assignment.getSubmissionTypes().get(0).toString().equalsIgnoreCase("online_upload"));
        assertTrue(assignment.getSubmissionTypes().get(1).toString().equalsIgnoreCase("online_text_entry"));
        assertTrue(assignment.getSubmissionTypes().get(2).toString().equalsIgnoreCase("media_recording"));

        assertEquals(assignment.getAllowedExtensions().size(), 3);
        assertTrue(assignment.getAllowedExtensions().get(0).equalsIgnoreCase("doc"));
        assertTrue(assignment.getAllowedExtensions().get(1).equalsIgnoreCase("pdf"));
        assertTrue(assignment.getAllowedExtensions().get(2).equalsIgnoreCase("txt"));

        assertEquals(assignment.getCourseId(), 833052);

        assertNotNull(assignment.getDescription());

        assertNotNull(assignment.getDueDate());

        assertNotNull(assignment.getName());

        assertNotNull(assignment.getLastSubmission());


        assertEquals(assignment.getAssignmentGroupId(), 534100);

        assertNotNull(rubricAssignment.getRubric());

        List<RubricCriterion> rubricCriterions = rubricAssignment.getRubric();
        assertEquals(rubricCriterions.size(), 3);
        for(RubricCriterion rubricCriterion : rubricCriterions){
            testRubricCriterion(rubricCriterion);
        }

        assertNotNull(lockInfoAssignment.getLockInfo());
        LockInfo lockInfo = lockInfoAssignment.getLockInfo();
        testLockInfo(lockInfo);
    }

    public static void testRubricCriterion (RubricCriterion rubricCriterion) {

        assertNotNull(rubricCriterion);

        assertNotNull(rubricCriterion.getId());

        assertNotNull(rubricCriterion.getCriterionDescription());

        assertNotNull(rubricCriterion.getLongDescription());

        assertTrue(rubricCriterion.getPoints() >= 0);

        if(rubricCriterion.getRatings() != null) {
            for(RubricCriterionRating rubricCriterionRating : rubricCriterion.getRatings()){
                testRubricCriterionRating(rubricCriterionRating);
            }
        }
    }

    public static void testRubricCriterionRating(RubricCriterionRating rubricCriterionRating) {

        assertNotNull(rubricCriterionRating);

        assertNotNull(rubricCriterionRating.getId());

        assertNotNull(rubricCriterionRating.getRatingDescription());

        assertTrue(rubricCriterionRating.getPoints() >= 0);

        //comments don't have to be there, so don't test those
    }

    public static void testLockInfo(LockInfo lockInfo) {
        assertNotNull(lockInfo);

        assertNotNull(lockInfo.getLockedModuleName());
    }


    String assignmentJSON = "{\n" +
            "\"assignment_group_id\": 534100,\n" +
            "\"automatic_peer_reviews\": false,\n" +
            "\"description\": \"<p>List all the different types of layouts that are used in xml.</p>\",\n" +
            "\"due_at\": \"2012-10-25T05:59:00Z\",\n" +
            "\"grade_group_students_individually\": false,\n" +
            "\"grading_standard_id\": null,\n" +
            "\"grading_type\": \"points\",\n" +
            "\"group_category_id\": null,\n" +
            "\"id\": 2241839,\n" +
            "\"lock_at\": null,\n" +
            "\"peer_reviews\": false,\n" +
            "\"points_possible\": 30,\n" +
            "\"position\": 1,\n" +
            "\"unlock_at\": null,\n" +
            "\"course_id\": 833052,\n" +
            "\"name\": \"Android 101\",\n" +
            "\"submission_types\": [\n" +
            "\"online_upload\",\n" +
            "\"online_text_entry\",\n" +
            "\"media_recording\"\n" +
            "],\n" +
            "\"muted\": false,\n" +
            "\"html_url\": \"https://mobiledev.instructure.com/courses/833052/assignments/2241839\",\n" +
            "\"allowed_extensions\": [\n" +
            "\"doc\",\n" +
            "\"pdf\",\n" +
            "\"txt\"\n" +
            "],\n" +
            "\"submission\": {\n" +
            "\"assignment_id\": 2241839,\n" +
            "\"attempt\": 15,\n" +
            "\"body\": \"Hey Hey Hey \",\n" +
            "\"grade\": \"28\",\n" +
            "\"grade_matches_current_submission\": false,\n" +
            "\"graded_at\": \"2012-10-09T02:01:58Z\",\n" +
            "\"grader_id\": 3356518,\n" +
            "\"id\": 10186303,\n" +
            "\"score\": 28,\n" +
            "\"submission_type\": \"online_text_entry\",\n" +
            "\"submitted_at\": \"2013-09-12T19:44:55Z\",\n" +
            "\"url\": null,\n" +
            "\"user_id\": 3360251,\n" +
            "\"workflow_state\": \"submitted\",\n" +
            "\"late\": true,\n" +
            "\"preview_url\": \"https://mobiledev.instructure.com/courses/833052/assignments/2241839/submissions/3360251?preview=1\"\n" +
            "},\n" +
            "\"locked_for_user\": false\n" +
            "}";

    String rubricAssignmentJSON = "{\n" +
            "\"assignment_group_id\": 534100,\n" +
            "\"automatic_peer_reviews\": false,\n" +
            "\"description\": \"Replacement description\",\n" +
            "\"due_at\": \"2013-06-01T05:59:00Z\",\n" +
            "\"grade_group_students_individually\": false,\n" +
            "\"grading_standard_id\": null,\n" +
            "\"grading_type\": \"points\",\n" +
            "\"group_category_id\": null,\n" +
            "\"id\": 3119886,\n" +
            "\"lock_at\": null,\n" +
            "\"peer_reviews\": false,\n" +
            "\"points_possible\": 15,\n" +
            "\"position\": 20,\n" +
            "\"unlock_at\": null,\n" +
            "\"course_id\": 833052,\n" +
            "\"name\": \"Education\",\n" +
            "\"submission_types\": [\n" +
            "\"online_text_entry\",\n" +
            "\"online_url\",\n" +
            "\"media_recording\",\n" +
            "\"online_upload\"\n" +
            "],\n" +
            "\"muted\": false,\n" +
            "\"html_url\": \"https://mobiledev.instructure.com/courses/833052/assignments/3119886\",\n" +
            "\"use_rubric_for_grading\": true,\n" +
            "\"free_form_criterion_comments\": false,\n" +
            "\"rubric\": [\n" +
            "{\n" +
            "\"id\": \"176919_1697\",\n" +
            "\"points\": 5,\n" +
            "\"description\": \"Grammar\",\n" +
            "\"long_description\": \"\",\n" +
            "\"ratings\": [\n" +
            "{\n" +
            "\"id\": \"blank\",\n" +
            "\"points\": 5,\n" +
            "\"description\": \"Perfect Grammar\"\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"176919_53\",\n" +
            "\"points\": 4,\n" +
            "\"description\": \"1 or two mistakes\"\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"blank_2\",\n" +
            "\"points\": 3,\n" +
            "\"description\": \"A few mistakes\"\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"176919_1429\",\n" +
            "\"points\": 2,\n" +
            "\"description\": \"Several mistakes\"\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"176919_9741\",\n" +
            "\"points\": 0,\n" +
            "\"description\": \"Abysmal\"\n" +
            "}\n" +
            "]\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"176919_6623\",\n" +
            "\"points\": 5,\n" +
            "\"description\": \"Coolness Factor\",\n" +
            "\"long_description\": \"\",\n" +
            "\"ratings\": [\n" +
            "{\n" +
            "\"id\": \"176919_9675\",\n" +
            "\"points\": 5,\n" +
            "\"description\": \"Super cool\"\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"176919_3172\",\n" +
            "\"points\": 4,\n" +
            "\"description\": \"Moderately Cool\"\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"176919_393\",\n" +
            "\"points\": 3,\n" +
            "\"description\": \"Un-Cool and Geeky\"\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"176919_5761\",\n" +
            "\"points\": 0,\n" +
            "\"description\": \"Un-Cool and Nerdy\"\n" +
            "}\n" +
            "]\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"176919_8253\",\n" +
            "\"points\": 5,\n" +
            "\"description\": \"How much I like you\",\n" +
            "\"long_description\": \"\",\n" +
            "\"ratings\": [\n" +
            "{\n" +
            "\"id\": \"176919_5103\",\n" +
            "\"points\": 5,\n" +
            "\"description\": \"You're my favorite in the class\"\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"176919_6271\",\n" +
            "\"points\": 4,\n" +
            "\"description\": \"I like having you around\"\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"176919_8307\",\n" +
            "\"points\": 3,\n" +
            "\"description\": \"You don't annoy me\"\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"176919_377\",\n" +
            "\"points\": 2,\n" +
            "\"description\": \"I can barely tolerate you\"\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"176919_2255\",\n" +
            "\"points\": 0,\n" +
            "\"description\": \"I wish you were dead\"\n" +
            "}\n" +
            "]\n" +
            "}\n" +
            "],\n" +
            "\"rubric_settings\": {\n" +
            "\"points_possible\": 15,\n" +
            "\"free_form_criterion_comments\": false\n" +
            "},\n" +
            "\"locked_for_user\": false\n" +
            "}";

    String lockInfoJSON = "{\n" +
            "\"assignment_group_id\": 534104,\n" +
            "\"automatic_peer_reviews\": false,\n" +
            "\"due_at\": \"2013-08-15T05:59:00Z\",\n" +
            "\"grade_group_students_individually\": false,\n" +
            "\"grading_standard_id\": null,\n" +
            "\"grading_type\": \"points\",\n" +
            "\"group_category_id\": null,\n" +
            "\"id\": 3546452,\n" +
            "\"lock_at\": null,\n" +
            "\"peer_reviews\": false,\n" +
            "\"points_possible\": 75,\n" +
            "\"position\": 16,\n" +
            "\"unlock_at\": null,\n" +
            "\"lock_info\": {\n" +
            "\"asset_string\": \"assignment_3546452\",\n" +
            "\"context_module\": {\n" +
            "\"cloned_item_id\": null,\n" +
            "\"completion_requirements\": [\n" +
            "{\n" +
            "\"id\": 6756870,\n" +
            "\"type\": \"min_score\",\n" +
            "\"min_score\": \"80\",\n" +
            "\"max_score\": null\n" +
            "},\n" +
            "{\n" +
            "\"id\": 8944431,\n" +
            "\"type\": \"must_submit\",\n" +
            "\"min_score\": \"\",\n" +
            "\"max_score\": null\n" +
            "},\n" +
            "{\n" +
            "\"id\": 8944445,\n" +
            "\"type\": \"min_score\",\n" +
            "\"min_score\": \"50\",\n" +
            "\"max_score\": null\n" +
            "},\n" +
            "{\n" +
            "\"id\": 8951510,\n" +
            "\"type\": \"must_view\",\n" +
            "\"min_score\": \"\",\n" +
            "\"max_score\": null\n" +
            "},\n" +
            "{\n" +
            "\"id\": 8951513,\n" +
            "\"type\": \"must_view\",\n" +
            "\"min_score\": \"\",\n" +
            "\"max_score\": null\n" +
            "},\n" +
            "{\n" +
            "\"id\": 8955141,\n" +
            "\"type\": \"must_submit\",\n" +
            "\"min_score\": \"\",\n" +
            "\"max_score\": null\n" +
            "},\n" +
            "{\n" +
            "\"id\": 8955142,\n" +
            "\"type\": \"must_view\",\n" +
            "\"min_score\": \"\",\n" +
            "\"max_score\": null\n" +
            "},\n" +
            "{\n" +
            "\"id\": 8955144,\n" +
            "\"type\": \"must_contribute\",\n" +
            "\"min_score\": \"\",\n" +
            "\"max_score\": null\n" +
            "},\n" +
            "{\n" +
            "\"id\": 8955147,\n" +
            "\"type\": \"must_view\",\n" +
            "\"min_score\": \"\",\n" +
            "\"max_score\": null\n" +
            "}\n" +
            "],\n" +
            "\"context_id\": 836357,\n" +
            "\"context_type\": \"Course\",\n" +
            "\"created_at\": \"2013-03-06T23:44:07Z\",\n" +
            "\"deleted_at\": null,\n" +
            "\"downstream_modules\": null,\n" +
            "\"end_at\": null,\n" +
            "\"id\": 805092,\n" +
            "\"migration_id\": null,\n" +
            "\"name\": \"Locked Prereq\",\n" +
            "\"position\": 7,\n" +
            "\"prerequisites\": [\n" +
            "{\n" +
            "\"id\": 793427,\n" +
            "\"type\": \"context_module\",\n" +
            "\"name\": \"Car Movies\"\n" +
            "}\n" +
            "],\n" +
            "\"require_sequential_progress\": false,\n" +
            "\"start_at\": null,\n" +
            "\"unlock_at\": \"2013-07-31T06:00:00Z\",\n" +
            "\"updated_at\": \"2013-07-23T21:09:46Z\",\n" +
            "\"workflow_state\": \"active\"\n" +
            "}\n" +
            "},\n" +
            "\"course_id\": 836357,\n" +
            "\"name\": \"Superhero\",\n" +
            "\"submission_types\": [\n" +
            "\"online_text_entry\",\n" +
            "\"online_url\"\n" +
            "],\n" +
            "\"description\": null,\n" +
            "\"muted\": false,\n" +
            "\"html_url\": \"https://mobiledev.instructure.com/courses/836357/assignments/3546452\",\n" +
            "\"locked_for_user\": true,\n" +
            "\"lock_explanation\": \"This assignment is part of the module <b>Locked Prereq</b> and hasn&#39;t been unlocked yet.<br/><a href='/courses/836357/modules'>Visit the course modules page for information on how to unlock this content.</a><a href='/courses/836357/modules/805092/prerequisites/assignment_3546452' style='display: none;' id='module_prerequisites_lookup_link'>&nbsp;</a>\"\n" +
            "}";


}
