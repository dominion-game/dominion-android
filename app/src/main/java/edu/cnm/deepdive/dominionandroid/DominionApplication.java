package edu.cnm.deepdive.dominionandroid;

import android.app.Application;
import edu.cnm.deepdive.dominionandroid.service.GoogleSignInService;


/**
 * Class containing main (non-UI) entry point for this app.
 */
public class DominionApplication extends Application{

    /**
     * Initializes the app by passing this instance (as the context) to {@link GoogleSignInService}.
     */
    @Override
    public void onCreate() {
      super.onCreate();
      GoogleSignInService.setApplicationContext(this);
    }
}
