import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;

/**
 * More dynamic path resolution.
 *
 * This workaround is only for Mac Users necessary and only if they don't use the $MODULE_DIR$
 * workaround. Follow this issue at https://code.google.com/p/android/issues/detail?id=158015
 */
public class RobolectricGradleTestRunner extends RobolectricTestRunner {

    public RobolectricGradleTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
//
//        String buildVariant = (BuildConfig.FLAVOR.isEmpty()
//                ? "" : BuildConfig.FLAVOR+ "/") + BuildConfig.BUILD_TYPE;
//        String intermediatesPath = BuildConfig.class.getResource("")
//                .toString().replace("file:", "");
//        intermediatesPath = intermediatesPath
//                .substring(0, intermediatesPath.indexOf("/classes"));
//
//        System.setProperty("android.package",
//                BuildConfig.APPLICATION_ID);
//        System.setProperty("android.manifest",
//                intermediatesPath + "/manifests/full/"
//                        + buildVariant + "/AndroidManifest.xml");
//        System.setProperty("android.resources",
//                intermediatesPath + "/res/" + buildVariant);
//        System.setProperty("android.assets",
//                intermediatesPath + "/assets/" + buildVariant);
    }
//
//    protected AndroidManifest getAppManifest(Config config) {
//        AndroidManifest appManifest = super.getAppManifest(config);
//        FsFile androidManifestFile = appManifest.getAndroidManifestFile();
//
//        if (androidManifestFile.exists()) {
//            return appManifest;
//        } else {
//            String moduleRoot = getModuleRootPath(config);
//            androidManifestFile = FileFsFile.from(moduleRoot, appManifest.getAndroidManifestFile().getPath().replace("bundles", "manifests/full"));
//            FsFile resDirectory = FileFsFile.from(moduleRoot, appManifest.getResDirectory().getPath());
//            FsFile assetsDirectory = FileFsFile.from(moduleRoot, appManifest.getAssetsDirectory().getPath());
//            return new AndroidManifest(androidManifestFile, resDirectory, assetsDirectory);
//        }
//    }
//
//    private String getModuleRootPath(Config config) {
//        String moduleRoot = config.constants().getResource("").toString().replace("file:", "");
//        return moduleRoot.substring(0, moduleRoot.indexOf("/build"));
//    }
}