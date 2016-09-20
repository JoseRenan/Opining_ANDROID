package br.com.opining.view.activities;

import android.app.Application;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by juan on 20/09/16.
 */
public class OpiningApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        String TWITTER_KEY = "1B0zoSxOGHevPm2viMWYPe1sS";
            String TWITTER_SECRET = "i9GgxK2ZZcM4ok4e0EAPUgPknVcy8XtN4Oo4FHgMzbmHaVrOQn";

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
    }
}
