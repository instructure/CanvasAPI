import com.google.gson.Gson;
import com.instructure.canvasapi.model.Author;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class AuthorUnitTest extends Assert {

    @Test
    public void testAuthor() {
        Gson gson = CanvasRestAdapter.getGSONParser();
        Author author = gson.fromJson(authorJSON, Author.class);

        assertNotNull(author);

        assertNotNull(author.getId());
        assertNotNull(author.getDisplayName());
        assertNotNull(author.getAvatarImageUrl());
        assertNotNull(author.getHtmlUrl());
    }

    public static final String authorJSON = "{"
                +"\"id\": 3360251,"
                +"\"display_name\": \"Brady BobLaw\","
                +"\"avatar_image_url\": \"https://mobiledev.instructure.com/files/65129556/download?download_frd=1&verifier=7fiex2XkIhokFK3jkFljObf5aj2QACgnG\","
                +"\"html_url\": \"https://mobiledev.instructure.com/courses/12345/users/123455\""
            +"}";

}
