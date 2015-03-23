import com.google.gson.Gson;
import com.instructure.canvasapi.model.PollSubmission;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class PollSubmissionUnitTest extends Assert {

    @Test
    public void testPollSubmission() {
        Gson gson = CanvasRestAdapter.getGSONParser();
        PollSubmission pollSubmission = gson.fromJson(pollSubmissionJSON, PollSubmission.class);

        assertNotNull(pollSubmission.getCreated_at());

        assertTrue(pollSubmission.getId() > 0);

        assertTrue(pollSubmission.getPoll_choice_id() > 0);
        assertTrue(pollSubmission.getUser_id() > 0);
    }

    String pollSubmissionJSON =
            "{\n" +
            "\"id\": \"7741\",\n" +
            "\"poll_session_id\": \"1230\",\n" +
            "\"poll_choice_id\": \"2212\",\n" +
            "\"user_id\": \"3360251\",\n" +
            "\"created_at\": \"2015-03-17T16:17:08Z\"\n" +
            "}";
}
