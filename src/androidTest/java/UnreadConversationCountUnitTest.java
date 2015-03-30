import com.google.gson.Gson;
import com.instructure.canvasapi.model.UnreadConversationCount;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class UnreadConversationCountUnitTest extends Assert {

    @Test
    public void testUnreadConversationCount() {
        Gson gson = CanvasRestAdapter.getGSONParser();
        UnreadConversationCount unreadConversationCount = gson.fromJson(unreadConversationCountJSON, UnreadConversationCount.class);

        assertEquals(unreadConversationCount.getUnreadCount(), "3");
    }

    String unreadConversationCountJSON = "{\"unread_count\":\"3\"}";

}
