package god.codename.brightside;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Created by Loknath Shankar on 5/7/2016.
 */
public final class ApplicationClass extends Application {

        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
        }

        @Override
        public void onCreate() {
            super.onCreate();
            FontOverride.setDefaultFont(this, "DEFAULT", "OpenSans_SemiboldItalic.ttf");
            FontOverride.setDefaultFont(this, "MONOSPACE", "OpenSans_SemiboldItalic.ttf");
            FontOverride.setDefaultFont(this, "SERIF", "OpenSans_SemiboldItalic.ttf");
            FontOverride.setDefaultFont(this, "SANS_SERIF", "OpenSans_SemiboldItalic.ttf");
        }

        @Override
        public void onLowMemory() {
            super.onLowMemory();
        }

        @Override
        public void onTerminate() {
            super.onTerminate();
        }
}
