package probe.com.realmssl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

public class MainActivity extends AppCompatActivity implements SyncUser.Callback {
    private static final String AUTH_URL = "https://chope.cafe24.com/auth";
    private static final String REALM_URL = "realms://chope.cafe24.com/~/default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SyncCredentials credentials = SyncCredentials.usernamePassword("realm@github.com", "realm");
        SyncUser.loginAsync(credentials, AUTH_URL, this);
    }

    @Override
    public void onSuccess(SyncUser user) {
        Realm.setDefaultConfiguration(new SyncConfiguration.Builder(user, REALM_URL).build());

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Person person = new Person();
        person.setName("hello ssl");
        realm.insertOrUpdate(person);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void onError(ObjectServerError error) {
        Log.e("ssl", error.toString());
    }
}
