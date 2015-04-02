import com.google.gson.Gson;
import com.instructure.canvasapi.model.NeedsGradingCount;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;


@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class NeedsGradingCountUnitTest extends Assert {

    @Test
    public void testNeedsGradingCount() {
        Gson gson = CanvasRestAdapter.getGSONParser();
        NeedsGradingCount needsGradingCount = gson.fromJson(needsGradingCountJSON, NeedsGradingCount.class);
        assertNotNull(needsGradingCount);

        assertTrue(needsGradingCount.getSectionId() > 0);

        assertTrue(needsGradingCount.getNeedsGradingCount() > 0);

    }

    String needsGradingCountJSON =
            "{\n" +
            "\"section_id\": 889720,\n" +
            "\"needs_grading_count\": 1\n" +
            "}";
}
