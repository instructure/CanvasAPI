import com.google.gson.Gson;
import com.instructure.canvasapi.model.User;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class UserUnitTest extends Assert {

    @Test
    public void testUser() {
        Gson gson = CanvasRestAdapter.getGSONParser();
        User user = gson.fromJson(userJSON, User.class);

        assertEquals(user.getAvatarURL(), "https://www.example.com");

        assertEquals(user.getId(), 1111);

        assertEquals(user.getEmail(), "primary_email");

        assertEquals(user.getLoginId(), "login_id");

        assertEquals(user.getName(), "Sam Franklen");

        assertEquals(user.getShortName(),"Samf");
    }

    String userJSON = "{\"id\":1111,\"name\":\"Sam Franklen\",\"short_name\":\"Samf\",\"sortable_name\":\"Franklen, Sam\",\"login_id\":\"login_id\",\"avatar_url\":\"https://www.example.com\",\"title\":null,\"bio\":null,\"primary_email\":\"primary_email\",\"time_zone\":\"America/Denver\",\"calendar\":{\"ics\":\"https://mobiledev.instructure.com/feeds/calendars/user_8JCkdINx6RO3dB8Ao5aPQCJO49p8XUpCbZgmqk7X.ics\"}}";
}
