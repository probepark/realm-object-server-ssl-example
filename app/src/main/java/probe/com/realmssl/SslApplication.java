package probe.com.realmssl;

import android.app.Application;
import android.util.Log;

import io.realm.Realm;
import io.realm.log.RealmLog;
import io.realm.log.RealmLogger;
import timber.log.Timber;

public class SslApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Timber.plant(new Timber.DebugTree());

        if (BuildConfig.DEBUG) {
            RealmLog.clear();
            RealmLog.setLevel(Log.DEBUG);
            RealmLog.add(new RealmLogger() {
                @Override
                public void log(int level, String tag, Throwable throwable, String message) {
                    Timber.log(level, throwable, message);
                }
            });
        }
    }
}
