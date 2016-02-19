import com.google.gson.Gson;
import com.instructure.canvasapi.model.QuizAnswer;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;


@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class QuizAnswerUnitTest extends Assert {


    @Test
    public void testQuizAnswers() {
        Gson gson = CanvasRestAdapter.getGSONParser();

        QuizAnswer[] quizAnswers = gson.fromJson(quizAnswerJSON, QuizAnswer[].class);

        assertNotNull(quizAnswers);

        for(QuizAnswer quizAnswer : quizAnswers) {
            assertNotNull(quizAnswer);
            assertTrue(quizAnswer.getId() > 0);
            assertNotNull(quizAnswer.getAnswerText());
            assertTrue(quizAnswer.getAnswerWeight() >= 0);
        }
    }

    String quizAnswerJSON = "[\n" +
            "{\n" +
            "\"id\": 6266,\n" +
            "\"text\": \"A\",\n" +
            "\"html\": \"\",\n" +
            "\"comments\": \"\",\n" +
            "\"weight\": 100\n" +
            "},\n" +
            "{\n" +
            "\"id\": 8595,\n" +
            "\"text\": \"B\",\n" +
            "\"html\": \"\",\n" +
            "\"comments\": \"\",\n" +
            "\"weight\": 0\n" +
            "},\n" +
            "{\n" +
            "\"id\": 6695,\n" +
            "\"text\": \"C\",\n" +
            "\"html\": \"\",\n" +
            "\"comments\": \"\",\n" +
            "\"weight\": 0\n" +
            "},\n" +
            "{\n" +
            "\"id\": 9929,\n" +
            "\"text\": \"D\",\n" +
            "\"html\": \"\",\n" +
            "\"comments\": \"\",\n" +
            "\"weight\": 0\n" +
            "}\n" +
            "]";
}
