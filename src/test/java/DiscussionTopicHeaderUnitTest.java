import com.google.gson.Gson;
import com.instructure.canvasapi.model.DiscussionParticipant;
import com.instructure.canvasapi.model.DiscussionTopicHeader;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RobolectricGradleTestRunner.class)
public class DiscussionTopicHeaderUnitTest extends Assert {

    @Test
    public void testDiscussionTopicHeader() {
        String discussionTopicHeaderJSON = "{\"assignment_id\":3301597,\"delayed_post_at\":null,\"id\":2111813,\"last_reply_at\":\"2013-05-29T15:51:18Z\",\"lock_at\":null,\"podcast_has_student_posts\":false,\"posted_at\":\"2013-05-23T22:16:55Z\",\"root_topic_id\":null,\"title\":\"Which programming language is best?\",\"user_name\":\"Derrick Hathaway\",\"discussion_subentry_count\":2,\"permissions\":{\"attach\":true,\"update\":true,\"delete\":true},\"message\":\"\\u003Cp\\u003EUse complete sentences and plenty of adverbs.\\u003C/p\\u003E\",\"discussion_type\":\"side_comment\",\"require_initial_post\":null,\"user_can_see_posts\":true,\"podcast_url\":null,\"pinned\":true,\"position\":236,\"read_state\":\"unread\",\"unread_count\":0,\"subscribed\":false,\"topic_children\":[],\"attachments\":[],\"published\":true,\"locked\":false,\"author\":{\"id\":170000003828513,\"display_name\":\"Derrick Hathaway\",\"avatar_image_url\":\"https://secure.gravatar.com/avatar/1753d19b1ddf16cb0a31d983f97f4488?s=50\\u0026d=https%3A%2F%2Fmobiledev.instructure.com%2Fimages%2Fdotted_pic.png\",\"html_url\":\"https://mobiledev.instructure.com/courses/24219/users/17~3828513\"},\"html_url\":\"https://mobiledev.instructure.com/courses/24219/discussion_topics/2111813\",\"locked_for_user\":false,\"url\":\"https://mobiledev.instructure.com/courses/24219/discussion_topics/2111813\",\"assignment\":{\"assignment_group_id\":6783,\"automatic_peer_reviews\":false,\"description\":\"\\u003Cp\\u003EUse complete sentences and plenty of adverbs.\\u003C/p\\u003E\",\"due_at\":\"2013-05-28T05:59:00Z\",\"grade_group_students_individually\":false,\"grading_standard_id\":null,\"grading_type\":\"letter_grade\",\"group_category_id\":null,\"id\":3301597,\"lock_at\":null,\"peer_reviews\":false,\"points_possible\":6,\"position\":4,\"unlock_at\":null,\"course_id\":24219,\"name\":\"Which programming language is best?\",\"submission_types\":[\"discussion_topic\"],\"muted\":false,\"html_url\":\"https://mobiledev.instructure.com/courses/24219/assignments/3301597\",\"needs_grading_count\":1,\"locked_for_user\":false}}";
        Gson gson = CanvasRestAdapter.getGSONParser();
        DiscussionTopicHeader discussionTopicHeader = gson.fromJson(discussionTopicHeaderJSON, DiscussionTopicHeader.class);

        assertNotNull(discussionTopicHeader);

        assertTrue(discussionTopicHeader.isPinned());

        assertTrue(discussionTopicHeader.getStatus() == DiscussionTopicHeader.ReadState.UNREAD);

        assertTrue(discussionTopicHeader.getId() > 0);

        assertTrue(discussionTopicHeader.getAssignmentId() > 0);

        assertNotNull(discussionTopicHeader.getCreator());

        DiscussionParticipant participant = discussionTopicHeader.getCreator();
        assertTrue(participant.getId() > 0);
        assertNotNull(participant.getAvatarUrl());
        assertNotNull(participant.getDisplayName());
        assertNotNull(participant.getHtmlUrl());

        assertNotNull(discussionTopicHeader.getHtmlUrl());

        assertNotNull(discussionTopicHeader.getLastReply());

        assertNotNull(discussionTopicHeader.getMessage());

        assertNotNull(discussionTopicHeader.getMessage());

        assertNotNull(discussionTopicHeader.getPostedAt());

        assertNotNull(discussionTopicHeader.getTitle());

        assertTrue(discussionTopicHeader.getType() == DiscussionTopicHeader.DiscussionType.SIDE_COMMENT);
    }
}
