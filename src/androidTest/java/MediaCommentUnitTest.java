import com.google.gson.Gson;
import com.instructure.canvasapi.model.MediaComment;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class MediaCommentUnitTest extends Assert {

    @Test
    public void testMediaComment() {
        Gson gson = CanvasRestAdapter.getGSONParser();
        MediaComment mediaComment = gson.fromJson(mediaCommentJSON, MediaComment.class);

        assertNotNull(mediaComment);

        assertNotNull(mediaComment.getMediaType());
        assertNotNull(mediaComment.getMediaId());
        assertNotNull(mediaComment.getMimeType());
        assertNotNull(mediaComment.getUrl());
    }

    String mediaCommentJSON =
           "{\n" +
            "\"content-type\": \"video/mp4\",\n" +
            "\"display_name\": null,\n" +
            "\"media_id\": \"m-C8q5qs5QXaR13VzeBwDoFWwn896LpZa\",\n" +
            "\"media_type\": \"video\",\n" +
            "\"url\": \"https://mobiledev.instructure.com/users/3360251/media_download?entryId=m-C8q5qs5QXaR13VzeBwDoFWwn896LpZa&redirect=1&type=mp4\"\n" +
            "}";

}
