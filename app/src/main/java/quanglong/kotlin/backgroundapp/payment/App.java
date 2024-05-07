package quanglong.kotlin.backgroundapp.payment;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    public Context cnt;

    @Override
    public void onCreate() {
        super.onCreate();
        cnt = this;
        SharedPreferencesManager.init(this);
    }
}