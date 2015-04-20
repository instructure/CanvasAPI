import com.google.gson.Gson;
import com.instructure.canvasapi.model.FileUploadParams;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.util.LinkedHashMap;

@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class FileUploadParamsUnitTest extends Assert {

    @Test
    public void testDiscussionTopic() {
        Gson gson = CanvasRestAdapter.getGSONParser();
        FileUploadParams uploadParams = gson.fromJson(json, FileUploadParams.class);

        assertNotNull(uploadParams);
        assertNotNull(uploadParams.getUploadParams());
        assertNotNull(uploadParams.getUploadUrl());

        LinkedHashMap<String,String> paramsHash = uploadParams.getUploadParams();

        assertNotNull("upload_url", "https://instructure-uploads.s3.amazonaws.com/");

        assertEquals(8, paramsHash.size());
        assertEquals(paramsHash.get("AWSAccessKeyId"), "ASDFDDFKJDSF");
        assertEquals(paramsHash.get("key"), "account_99298/attachments/67193665/IMG_FILENAME.jpg");
        assertEquals(paramsHash.get("acl"), "private");
        assertEquals(paramsHash.get("Policy"), "eyJleHBpcmF0aW9uIjoiMjAxT2xyNlYifSx7ImNvbnRlbnQtdHlwZSI6ImltYWdlXC9qcGV");
        assertEquals(paramsHash.get("Signature"), "BNudkjo4pLUIUHYYnbT0D8phmBw");
        assertEquals(paramsHash.get("success_action_redirect"), "https://mobiledev.instructure.com/api/v1/files/67193665/create_success?uuid=dssdfsfdsfs");
        assertEquals(paramsHash.get("content-type"), "image/jpeg");
    }
        private static final String json =
        "{"
        +"\"upload_url\": \"https://instructure-uploads.s3.amazonaws.com/\","
        +"\"upload_params\": {"
            +"\"AWSAccessKeyId\": \"ASDFDDFKJDSF\","
            +"\"Filename\": \"\","
            +"\"key\": \"account_99298/attachments/67193665/IMG_FILENAME.jpg\","
            +"\"acl\": \"private\","
            +"\"Policy\": \"eyJleHBpcmF0aW9uIjoiMjAxT2xyNlYifSx7ImNvbnRlbnQtdHlwZSI6ImltYWdlXC9qcGV\","
            +"\"Signature\": \"BNudkjo4pLUIUHYYnbT0D8phmBw\","
            +"\"success_action_redirect\": \"https://mobiledev.instructure.com/api/v1/files/67193665/create_success?uuid=dssdfsfdsfs\","
            +"\"content-type\": \"image/jpeg\""
        +"},"
        +"\"file_param\": \"file\""
        +"}";
}
