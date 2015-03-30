import com.google.gson.Gson;
import com.instructure.canvasapi.model.CommunicationChannel;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import junit.framework.Assert;

@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class CommunicationChannelUnitTest extends Assert{

    @Test
    public void testCommunicationChannel(){
        Gson gson = CanvasRestAdapter.getGSONParser();
        CommunicationChannel[] communicationChannels = gson.fromJson(communicationChannelJSON, CommunicationChannel[].class);

        assertNotNull(communicationChannels);
        assertEquals(3, communicationChannels.length);

        for(CommunicationChannel communicationChannel : communicationChannels){
            assertNotNull(communicationChannel.getId());
            assertNotNull(communicationChannel.getPosition());
            assertNotNull(communicationChannel.getUserId());
            assertNotNull(communicationChannel.getWorkflowState());
            assertNotNull(communicationChannel.getAddress());
            assertNotNull(communicationChannel.getType());
        }
    }

    private static final String communicationChannelJSON = "["
            +"{\"id\":123245,"
                +"\"position\":1,"
                +"\"user_id\":123245,"
                +"\"workflow_state\":\"active\","
                +"\"address\":\"test@test.com\","
                +"\"type\":\"email\"},"
            +"{\"id\":123245,"
                +"\"position\":2,"
                +"\"user_id\":123245,"
                +"\"workflow_state\":\"active\","
                +"\"address\":\"test@test.com\","
                +"\"type\":\"email\"},"
            +"{\"id\":123245,"
                +"\"position\":3,"
                +"\"user_id\":123245,"
                +"\"workflow_state\":\"active\","
                +"\"address\":\"For All Devices\","
                +"\"type\":\"push\"}"
            +"]";
}
