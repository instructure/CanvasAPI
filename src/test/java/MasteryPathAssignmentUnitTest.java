import com.google.gson.Gson;
import com.instructure.canvasapi.model.Assignment;
import com.instructure.canvasapi.model.MasteryPathAssignment;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

/**
 * Copyright (c) 2016 Instructure. All rights reserved.
 */
@Config(sdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class MasteryPathAssignmentUnitTest extends Assert {

    @Test
    public void testMasteryPathAssignment(){
        Gson gson = CanvasRestAdapter.getGSONParser();
        MasteryPathAssignment[] masteryPathAssignments = gson.fromJson(masteryPathAssignmentJSON, MasteryPathAssignment[].class);

        assertNotNull(masteryPathAssignments);
        assertTrue(masteryPathAssignments.length > 0);
        assertNotNull(masteryPathAssignments[0].getAssignment());

        //check the assignment
        Assignment assignment = masteryPathAssignments[0].getAssignment();
        assertTrue(assignment.getId() == 5);
        assertTrue(assignment.getPointsPossible() == 0);
        assertNotNull(assignment.getSubmissionTypes());

    }

    private String masteryPathAssignmentJSON = "[\n" +
            "            {\n" +
            "              \"id\": 2,\n" +
            "              \"assignment_id\": \"5\",\n" +
            "              \"created_at\": \"2016-08-03T19:04:44.865Z\",\n" +
            "              \"updated_at\": \"2016-08-03T19:04:44.865Z\",\n" +
            "              \"override_id\": 8,\n" +
            "              \"assignment_set_id\": 2,\n" +
            "              \"position\": 1,\n" +
            "              \"model\": {\n" +
            "                \"id\": 5,\n" +
            "                \"title\": \"Quiz 1~1\",\n" +
            "                \"description\": \"\",\n" +
            "                \"due_at\": null,\n" +
            "                \"unlock_at\": null,\n" +
            "                \"lock_at\": null,\n" +
            "                \"points_possible\": 0,\n" +
            "                \"min_score\": null,\n" +
            "                \"max_score\": null,\n" +
            "                \"grading_type\": \"points\",\n" +
            "                \"submission_types\": [\n" +
            "                   \"online_quiz\"],\n" +
            "                \"workflow_state\": \"published\",\n" +
            "                \"context_id\": 1,\n" +
            "                \"context_type\": \"Course\",\n" +
            "                \"updated_at\": \"2016-08-11T17:32:34Z\",\n" +
            "                \"context_code\": \"course_1\"\n" +
            "              }\n" +
            "            }\n" +
            "          ]";
}
