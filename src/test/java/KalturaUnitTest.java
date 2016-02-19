import com.google.gson.Gson;
import com.instructure.canvasapi.model.KalturaConfig;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import retrofit.Callback;
import retrofit.http.GET;

@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class KalturaUnitTest extends Assert {

    @Test
    public void test1(){
        Gson gson = CanvasRestAdapter.getGSONParser();
        KalturaConfig kalturaConfig = gson.fromJson(kalturaConfigJSON, KalturaConfig.class);

        assertNotNull(kalturaConfig);
        assertTrue(kalturaConfig.isEnabled());
        assertNotNull(kalturaConfig.getDomain().equals("www.instructuremedia.com"));
        assertTrue(kalturaConfig.getPartner_id() == 101);
    }


    //Kaltura Config
    //@GET("/services/kaltura")
    //void getKalturaConfigaration(Callback<KalturaConfig> callback);
    final String kalturaConfigJSON = "{\n" +
            "\"enabled\": true,\n" +
            "\"domain\": \"www.instructuremedia.com\",\n" +
            "\"resource_domain\": \"www.instructuremedia.com\",\n" +
            "\"rtmp_domain\": \"rtmp.instructuremedia.com\",\n" +
            "\"partner_id\": \"101\"\n" +
            "}";
}
