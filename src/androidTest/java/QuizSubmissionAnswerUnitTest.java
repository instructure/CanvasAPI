import com.google.gson.Gson;
import com.instructure.canvasapi.model.QuizSubmissionAnswer;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class QuizSubmissionAnswerUnitTest extends Assert {

    @Test
    public void testQuizSubmissionAnswer() {
        Gson gson = CanvasRestAdapter.getGSONParser();

        QuizSubmissionAnswer[] quizSubmissionAnswers = gson.fromJson(quizSubmissionAnswerJSON, QuizSubmissionAnswer[].class);

        assertNotNull(quizSubmissionAnswers);

        for(QuizSubmissionAnswer quizSubmissionAnswer : quizSubmissionAnswers) {
            assertTrue(quizSubmissionAnswer.getId() > 0);
            assertNotNull(quizSubmissionAnswer.getText());
            assertNotNull(quizSubmissionAnswer.getHtml());
            assertNotNull(quizSubmissionAnswer.getComments());
            assertTrue(quizSubmissionAnswer.getWeight() >= 0);
        }
    }


    String quizSubmissionAnswerJSON = "[\n" +
            "{\n" +
                "\"id\": 7720,\n" +
                "\"text\": \"\",\n" +
                "\"html\": \"<p>Mr. Ryan: Who was Joan of Arc?</p>\\n<p>Ted: Noah's wife?</p>\",\n" +
                "\"comments\": \"\",\n" +
                "\"weight\": 100\n" +
            "},\n" +
            "{\n" +
                "\"id\": 8901,\n" +
                "\"text\": \"Daddy's got to go to work\",\n" +
                "\"html\": \"\",\n" +
                "\"comments\": \"\",\n" +
                "\"weight\": 0\n" +
            "},\n" +
            "{\n" +
                "\"id\": 9650,\n" +
                "\"text\": \"Roads? Where we're going, we don't need roads.\",\n" +
                "\"html\": \"\",\n" +
                "\"comments\": \"\",\n" +
                "\"weight\": 0\n" +
            "},\n" +
            "{\n" +
                "\"id\": 39,\n" +
                "\"text\": \"\",\n" +
                "\"html\": \"\",\n" +
                "\"comments\": \"\",\n" +
                "\"weight\": 0\n" +
            "}\n" +
            "]";

}
