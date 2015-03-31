import com.google.gson.Gson;
import com.instructure.canvasapi.model.GroupCategory;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class GroupCategoryUnitTest extends Assert {


    @Test
    public void testGroupCategories() {
        Gson gson = CanvasRestAdapter.getGSONParser();
        GroupCategory[] groupCateogries = gson.fromJson(groupCategoriesJSON, GroupCategory[].class);

        assertNotNull(groupCateogries);

        assertEquals(3, groupCateogries.length);
        
        for(GroupCategory groupCategory : groupCateogries){
            assertNotNull(groupCategory.getId());
            assertNotNull(groupCategory.getName());
            assertNotNull(groupCategory.getSelf_signup());
            assertNotNull(groupCategory.getContext_type());
            assertNotNull(groupCategory.getSelf_signup());
            assertNotNull(groupCategory.getCourse_id());
        }
    }

    public static final String groupCategoriesJSON = "["
        +"{\"auto_leader\": \"random\","
            +"\"group_limit\": 4,"
            +"\"id\": 55525,"
            +"\"name\": \"Group Set 1\","
            +"\"role\": null,"
            +"\"self_signup\": \"restricted\","
            +"\"context_type\": \"Course\","
            +"\"course_id\": 833052,"
            +"\"allows_multiple_memberships\": false,"
            +"\"is_member\": false"
            +"},"
        +"{\"auto_leader\": \"random\","
            +"\"group_limit\": 4,"
            +"\"id\": 55524,"
            +"\"name\": \"Group Set 2\","
            +"\"role\": null,"
            +"\"self_signup\": \"restricted\","
            +"\"context_type\": \"Course\","
            +"\"course_id\": 833052,"
            +"\"allows_multiple_memberships\": false,"
            +"\"is_member\": false"
            +"},"
        +"{\"auto_leader\": \"random\","
            +"\"group_limit\": 4,"
            +"\"id\": 54322,"
            +"\"name\": \"Group Set 3\","
            +"\"role\": null,"
            +"\"self_signup\": \"restricted\","
            +"\"context_type\": \"Course\","
            +"\"course_id\": 833052,"
            +"\"allows_multiple_memberships\": false,"
            +"\"is_member\": false"
            +"}"
    +"]";
}
