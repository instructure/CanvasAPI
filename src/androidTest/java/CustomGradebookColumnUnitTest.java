import com.google.gson.Gson;
import com.instructure.canvasapi.model.CustomColumn;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;


@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class CustomGradebookColumnUnitTest extends Assert {

    @Test
    public void testCustomColumnData() {
        Gson gson = CanvasRestAdapter.getGSONParser();
        CustomColumn[] customColumns = gson.fromJson(customColumnData, CustomColumn[].class);

        assertNotNull(customColumns);
        assertEquals(3, customColumns.length);

        for(CustomColumn customColumn : customColumns){
            assertNotNull(customColumn.getId());
            assertNotNull(customColumn.getPosition());
            assertNotNull(customColumn.isTeacher_notes());
            assertNotNull(customColumn.isHidden());
        }
    }

    private static final String customColumnData = "["
            +"{\"id\":1234,"
                +"\"position\":0,"
                +"\"teacher_notes\":false,"
                +"\"title\":\"Column1\","
                +"\"hidden\":false},"
            +"{\"id\":2345,"
                +"\"position\":1,"
                +"\"teacher_notes\":false,"
                +"\"title\":\"Column2\","
                +"\"hidden\":false},"
            +"{\"id\":3456,"
                +"\"position\":2,"
                +"\"teacher_notes\":true,"
                +"\"title\":\"Column3\","
                +"\"hidden\":false}"
        +"]";
}
