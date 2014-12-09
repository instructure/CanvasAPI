import com.google.gson.Gson;
import com.instructure.canvasapi.model.DiscussionEntry;
import com.instructure.canvasapi.model.DiscussionTopic;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class DiscussionEntryUnitTest extends Assert {

    @Test
    public void testDiscussionEntry() {
        Gson gson = CanvasRestAdapter.getGSONParser();

        DiscussionTopic longIdTopic = gson.fromJson(longIdJson, DiscussionTopic.class);

        assertNotNull(longIdTopic);
        for(DiscussionEntry discussionEntry : longIdTopic.getViews()){
            testDiscussionEntryView(discussionEntry);
        }


        DiscussionTopic stringIdTopic = gson.fromJson(stringIdJson, DiscussionTopic.class);
        for(DiscussionEntry discussionEntry : stringIdTopic.getViews()){
            testDiscussionEntryView(discussionEntry);
        }
    }

    public static void testDiscussionEntryView(DiscussionEntry discussionEntry){
        assertNotNull(discussionEntry);

        assertTrue(discussionEntry.getId() > 0);

        if(discussionEntry.getReplies() != null){
            for(DiscussionEntry reply : discussionEntry.getReplies()){
                testDiscussionEntryView(reply);
            }
        }

        assertTrue(discussionEntry.getUserId() > 0);

        assertNotNull(discussionEntry.getCreatedAt());

        assertNotNull(discussionEntry.getLastUpdated());
    }

    String longIdJson = "{ \"unread_entries\": [], \"forced_entries\": [], \"participants\": [{\"id\":3828648,\"display_name\":\"Drip Derskey\",\"avatar_image_url\":\"https://mobiledev.instructure.com/images/thumbnails/32957548/krblSV5HHvhqqlxUCtvAsR6AkGMI21qsw8i2y1Tx\",\"html_url\":\"https://mobiledev.instructure.com/courses/24219/users/3828648\"}], \"view\": [{\"created_at\":\"2013-05-29T15:50:24Z\",\"id\":5203752,\"parent_id\":null,\"updated_at\":\"2013-05-29T15:50:24Z\",\"user_id\":3828648,\"message\":\"Clojure1!!11!!\"},{\"created_at\":\"2013-05-29T15:51:18Z\",\"id\":5203767,\"parent_id\":null,\"updated_at\":\"2013-05-29T15:51:18Z\",\"user_id\":3828648,\"message\":\"I mean: Clojure is the best programming language.\"}], \"new_entries\": [] }";
    String stringIdJson = "{ \"unread_entries\": [], \"forced_entries\": [], \"participants\": [{\"id\":3828648,\"display_name\":\"Drip Derskey\",\"avatar_image_url\":\"https://mobiledev.instructure.com/images/thumbnails/32957548/krblSV5HHvhqqlxUCtvAsR6AkGMI21qsw8i2y1Tx\",\"html_url\":\"https://mobiledev.instructure.com/courses/24219/users/3828648\"}], \"view\": [{\"created_at\":\"2013-05-29T15:50:24Z\",\"id\":\"5203752\",\"parent_id\":null,\"updated_at\":\"2013-05-29T15:50:24Z\",\"user_id\":\"3828648\",\"message\":\"Clojure1!!11!!\"},{\"created_at\":\"2013-05-29T15:51:18Z\",\"id\":\"5203767\",\"parent_id\":null,\"updated_at\":\"2013-05-29T15:51:18Z\",\"user_id\":\"3828648\",\"message\":\"I mean: Clojure is the best programming language.\"}], \"new_entries\": [] }";
}
