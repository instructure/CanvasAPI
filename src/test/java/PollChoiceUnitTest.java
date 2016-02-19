import com.google.gson.Gson;
import com.instructure.canvasapi.model.PollChoice;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class PollChoiceUnitTest extends Assert {

    @Test
    public void testPollChoice() {
        Gson gson = CanvasRestAdapter.getGSONParser();
        PollChoice[] pollChoices= gson.fromJson(pollChoiceJSON, PollChoice[].class);

        assertNotNull(pollChoices);

        for(PollChoice pollChoice : pollChoices) {
            assertNotNull(pollChoice);

            assertNotNull(pollChoice.getText());

            assertTrue(pollChoice.getPosition() >= 0);

            assertTrue(pollChoice.getId() > 0);
        }
    }

    String pollChoiceJSON = "[\n" +
            "{\n" +
            "\"id\": \"762\",\n" +
            "\"text\": \"Ghbb\",\n" +
            "\"position\": 0,\n" +
            "\"is_correct\": false\n" +
            "},\n" +
            "{\n" +
            "\"id\": \"761\",\n" +
            "\"text\": \"Nnnbbb\",\n" +
            "\"position\": 1,\n" +
            "\"is_correct\": false\n" +
            "}" +
            "]";
}
