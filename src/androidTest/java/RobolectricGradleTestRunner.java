import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;


public class RobolectricGradleTestRunner extends RobolectricTestRunner {

    public RobolectricGradleTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

}