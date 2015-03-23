import com.google.gson.Gson;
import com.instructure.canvasapi.model.Poll;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;


@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class PollsUnitTest extends Assert {

    @Test
    public void testPoll() {
        Gson gson = CanvasRestAdapter.getGSONParser();
        Poll[] polls= gson.fromJson(pollsJSON, Poll[].class);

        for(Poll poll : polls) {
            assertNotNull(poll.getCreated_at());

            assertNotNull(poll.getQuestion());

            assertTrue(poll.getId() > 0);
        }
    }

    String pollsJSON = "[\n" +
            "{\n" +
            "\"id\": \"289\",\n" +
            "\"question\": \"Jcjjdjjdd\",\n" +
            "\"description\": null,\n" +
            "\"created_at\": \"2014-08-19T15:34:06Z\",\n" +
            "\"total_results\": {},\n" +
            "\"user_id\": \"4599568\"\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"270\",\n" +
            "\"question\": \"fewqfewq\",\n" +
            "\"description\": null,\n" +
            "\"created_at\": \"2014-08-11T20:16:44Z\",\n" +
            "\"total_results\": {},\n" +
            "\"user_id\": \"4599568\"\n" +
            "}" +
            "]";

}
