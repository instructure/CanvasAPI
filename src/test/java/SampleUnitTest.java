import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@Config(emulateSdk = 17)
@RunWith(RobolectricGradleTestRunner.class)
public class SampleUnitTest extends Assert {

    @Before
    public void shouldBeforeSomething() throws Exception {

    }

    @Test
    public void shouldFailForFun() throws Exception {
        assertTrue(true);
    }


}
