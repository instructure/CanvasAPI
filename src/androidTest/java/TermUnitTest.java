import com.google.gson.Gson;
import com.instructure.canvasapi.model.Term;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class TermUnitTest extends Assert {


    @Test
    public void testTerm() {
        Gson gson = CanvasRestAdapter.getGSONParser();
        Term term = gson.fromJson(termJSON, Term.class);

        assertNotNull(term);

        assertNotNull(term.getName());
        assertTrue(term.getId() > 0);
    }

    //https://mobiledev.instructure.com/api/v1/courses/833052/?include[]=term&include[]=permissions&include[]=license&include[]=is_public&include[]=needs_grading_count
    String termJSON =
            "{\n" +
            "\"end_at\": null,\n" +
            "\"id\": 3142,\n" +
            "\"name\": \"Default Term\",\n" +
            "\"start_at\": null,\n" +
            "\"workflow_state\": \"active\"\n" +
            "}";
}
