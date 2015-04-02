import com.google.gson.Gson;
import com.instructure.canvasapi.model.AssignmentGroup;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class AssignmentGroupUnitTest extends Assert {

    @Test
    public void testAssignmentGroup() {
        String assignmentGroupJSON = "[\n" +
                "{\n" +
                "\"group_weight\": 0,\n" +
                "\"id\": 534101,\n" +
                "\"name\": \"Extra Credit\",\n" +
                "\"position\": 1,\n" +
                "\"rules\": {}\n" +
                "},\n" +
                "{\n" +
                "\"group_weight\": 0,\n" +
                "\"id\": 534100,\n" +
                "\"name\": \"Assignments\",\n" +
                "\"position\": 2,\n" +
                "\"rules\": {}\n" +
                "}\n" +
                "]";

        Gson gson = CanvasRestAdapter.getGSONParser();
        AssignmentGroup[] assignmentGroup = gson.fromJson(assignmentGroupJSON, AssignmentGroup[].class);

        assertNotNull(assignmentGroup);

        assertEquals(2, assignmentGroup.length);

        assertNotNull(assignmentGroup[0].getName());
        assertNotNull(assignmentGroup[1].getName());

        assertTrue(assignmentGroup[0].getId() > 0);
        assertTrue(assignmentGroup[1].getId() > 0);
    }
}
