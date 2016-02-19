import com.google.gson.Gson;
import com.instructure.canvasapi.model.ModuleItem;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class ModuleItemUnitTest extends Assert {

    @Test
    public void testModuleItem() {
        Gson gson = CanvasRestAdapter.getGSONParser();
        ModuleItem[] moduleItems = gson.fromJson(moduleItemJSON, ModuleItem[].class);

        for (ModuleItem moduleItem : moduleItems){
            assertTrue(moduleItem.getId() > 0);

            assertNotNull(moduleItem.getType());

            assertNotNull(moduleItem.getTitle());

            assertNotNull(moduleItem.getHtml_url());

            assertNotNull(moduleItem.getUrl());

            if(moduleItem.getCompletionRequirement() != null) {
                assertNotNull(moduleItem.getCompletionRequirement().getType());
            }
        }
    }

    String moduleItemJSON = "[\n" +
            "{\n" +
            "\"id\": 9012239,\n" +
            "\"indent\": 0,\n" +
            "\"position\": 1,\n" +
            "\"title\": \"Android 101\",\n" +
            "\"type\": \"Assignment\",\n" +
            "\"module_id\": 1059720,\n" +
            "\"html_url\": \"https://mobiledev.instructure.com/courses/833052/modules/items/9012239\",\n" +
            "\"content_id\": 2241839,\n" +
            "\"url\": \"https://mobiledev.instructure.com/api/v1/courses/833052/assignments/2241839\",\n" +
            "\"completion_requirement\": {\n" +
            "\"type\": \"must_submit\",\n" +
            "\"completed\": true\n" +
            "}\n" +
            "},\n" +
            "{\n" +
            "\"id\": 9012244,\n" +
            "\"indent\": 0,\n" +
            "\"position\": 2,\n" +
            "\"title\": \"Favorite App Video\",\n" +
            "\"type\": \"Assignment\",\n" +
            "\"module_id\": 1059720,\n" +
            "\"html_url\": \"https://mobiledev.instructure.com/courses/833052/modules/items/9012244\",\n" +
            "\"content_id\": 2241864,\n" +
            "\"url\": \"https://mobiledev.instructure.com/api/v1/courses/833052/assignments/2241864\",\n" +
            "\"completion_requirement\": {\n" +
            "\"type\": \"min_score\",\n" +
            "\"min_score\": \"5\",\n" +
            "\"completed\": true\n" +
            "}\n" +
            "},\n" +
            "{\n" +
            "\"id\": 9012248,\n" +
            "\"indent\": 0,\n" +
            "\"position\": 3,\n" +
            "\"title\": \"Android vs. iOS\",\n" +
            "\"type\": \"Discussion\",\n" +
            "\"module_id\": 1059720,\n" +
            "\"html_url\": \"https://mobiledev.instructure.com/courses/833052/modules/items/9012248\",\n" +
            "\"content_id\": 1369942,\n" +
            "\"url\": \"https://mobiledev.instructure.com/api/v1/courses/833052/discussion_topics/1369942\",\n" +
            "\"completion_requirement\": {\n" +
            "\"type\": \"must_contribute\",\n" +
            "\"completed\": false\n" +
            "}\n" +
            "},\n" +
            "{\n" +
            "\"id\": 9012251,\n" +
            "\"indent\": 0,\n" +
            "\"position\": 4,\n" +
            "\"title\": \"Easy Quiz\",\n" +
            "\"type\": \"Quiz\",\n" +
            "\"module_id\": 1059720,\n" +
            "\"html_url\": \"https://mobiledev.instructure.com/courses/833052/modules/items/9012251\",\n" +
            "\"content_id\": 757314,\n" +
            "\"url\": \"https://mobiledev.instructure.com/api/v1/courses/833052/quizzes/757314\",\n" +
            "\"completion_requirement\": {\n" +
            "\"type\": \"must_submit\",\n" +
            "\"completed\": true\n" +
            "}\n" +
            "}\n" +
            "]";

}
