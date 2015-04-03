import com.google.gson.Gson;
import com.instructure.canvasapi.model.AccountDomain;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;


@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class AccountDomainUnitTest extends Assert{

    @Test
    public void testAccountDomain(){
        Gson gson = CanvasRestAdapter.getGSONParser();
        AccountDomain[] accountDomains = gson.fromJson(accountDomainsJSON, AccountDomain[].class);

        assertNotNull(accountDomains);
        assertEquals(8, accountDomains.length);

        for(AccountDomain accountDomain : accountDomains){
            assertNotNull(accountDomain.getName());
            assertNotNull(accountDomain.getDomain());
        }
    }

    private static final String accountDomainsJSON = "["
            +"{\"name\":\"Northeastern Panda Educational\","
                +"\"domain\":\"npe.instructure.com\","
                +"\"distance\":null},"
            +"{\"name\":\"Southern Panda University\","
                +"\"domain\":\"spu.instructure.com\","
                +"\"distance\":null},"
            +"{\"name\":\"University of Panda\","
                +"\"domain\":\"panda.instructure.com\","
                + "\"distance\":null},"
            +"{\"name\":\"Panda Education Network\","
                +"\"domain\":\"pen.instructure.com\","
                +"\"distance\":null},"
            +"{\"name\":\"Panda Electronic High School\","
                +"\"domain\":\"learn.panda.org\","
                +"\"distance\":null},"
            +"{\"name\":\"Panda State University\","
                +"\"domain\":\"psu.instructure.com\","
                +"\"distance\":null},"
            +"{\"name\":\"Panda Students Connect\","
                +"\"domain\":\"pandastudentsconnect.instructure.com\","
                +"\"distance\":null},"
            +"{\"name\":\"Panda Valley University\","
                +"\"domain\":\"pvu.instructure.com\","
                +"\"distance\":null}"
            +"]";


}
