import com.google.gson.Gson;
import com.instructure.canvasapi.model.QuizSubmissionTime;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class QuizSubmissionTimeUnitTest extends Assert {

    @Test
    public void testQuizSubmissionTime() {
        Gson gson = CanvasRestAdapter.getGSONParser();

        QuizSubmissionTime quizSubmissionTime = gson.fromJson(quizSubmissionTimeJSON, QuizSubmissionTime.class);

        assertNotNull(quizSubmissionTime);
        assertNotNull(quizSubmissionTime.getEndAt());
    }


    String quizSubmissionTimeJSON = "{\n" +
            "\"end_at\": \"2015-05-19T18:30:22Z\",\n" +
            "\"time_left\": -16\n" +
            "}";
}
