package cn.kalyter.css.util;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

import cn.kalyter.css.data.InjectClass;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public class App extends Application {
    private static InjectClass mInjectClass;

    @Override
    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();
        mInjectClass = new InjectClass(context);
        SDKInitializer.initialize(context);
    }

    public static InjectClass getInjectClass() {
        return mInjectClass;
    }
}
