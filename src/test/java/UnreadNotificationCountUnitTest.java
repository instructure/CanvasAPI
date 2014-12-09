import com.google.gson.Gson;
import com.instructure.canvasapi.model.UnreadNotificationCount;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class UnreadNotificationCountUnitTest extends Assert {

    @Test
    public void testUnreadNotificationCount() {
        Gson gson = CanvasRestAdapter.getGSONParser();
        UnreadNotificationCount[] unreadNotificationCount = gson.fromJson(unreadNotificationCountJSON, UnreadNotificationCount[].class);

        assertNotNull(unreadNotificationCount);

        assertEquals(unreadNotificationCount.length, 5);

        for(UnreadNotificationCount unc : unreadNotificationCount){
            assertTrue(unc.getCount() > 0);

            assertTrue(unc.getUnreadCount() > 0);
            assertNotNull(unc.getNotificationCategory());
            assertNotNull(unc.getType());
        }
    }

    String unreadNotificationCountJSON = "[{\"type\":\"Announcement\",\"count\":1,\"unread_count\":1,\"notification_category\":\"null\"},{\"type\":\"DiscussionTopic\",\"count\":17,\"unread_count\":9,\"notification_category\":\"null\"},{\"type\":\"Message\",\"count\":10,\"unread_count\":10,\"notification_category\":\"Due Date\"},{\"type\":\"Message\",\"count\":8,\"unread_count\":10,\"notification_category\":\"Late Grading\"},{\"type\":\"Submission\",\"count\":5,\"unread_count\":10,\"notification_category\":\"null\"}]";

}
