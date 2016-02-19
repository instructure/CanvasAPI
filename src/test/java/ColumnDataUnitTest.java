import com.google.gson.Gson;
import com.instructure.canvasapi.model.ColumnDatum;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class ColumnDataUnitTest extends Assert {

    @Test
    public void testColumnData() {
        Gson gson = CanvasRestAdapter.getGSONParser();
        ColumnDatum[] columnDatums = gson.fromJson(columnDataJSON, ColumnDatum[].class);

        assertNotNull(columnDatums);
        assertEquals(3, columnDatums.length);

        for(ColumnDatum columnDatum : columnDatums){
            assertNotNull(columnDatum.getUser_id());
            assertNotNull(columnDatum.getContent());
        }
    }

    private static final String columnDataJSON = "["
            +"{\"content\":\"This is the content for a column data\","
                +"\"user_id\":123456},"
            +"{\"content\":\"Should be more like Harry.\","
                +"\"user_id\":123456},"
            +"{\"content\":\"Needs more practice.\","
                +"\"user_id\":123456}"
        +"]";
}
