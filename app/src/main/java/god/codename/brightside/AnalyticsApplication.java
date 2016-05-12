package god.codename.brightside;

/**
 * Created by Loknath Shankar on 3/24/2016.
 */

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;


@ReportsCrashes(formUri = "", // will not be used
        mailTo = "loknathshankar@hotmail.com", // my email here
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text)
public class AnalyticsApplication extends Application {
    //private Tracker mTracker;
    private Tracker mTracker;
    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
        /*FontsOverride.setDefaultFont(this, "DEFAULT", "OpenSans_Regular.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "OpenSans_Regular.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "OpenSans_Regular.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "OpenSans_Regular.ttf");*/
    }
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            mTracker = analytics.newTracker(R.xml.app_tracker);
        }
        return mTracker;
    }
}