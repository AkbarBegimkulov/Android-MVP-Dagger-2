package uz.marokand.mvp_dagger2;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import uz.marokand.mvp_dagger2.di.AppComponent;
import uz.marokand.mvp_dagger2.di.DaggerAppComponent;

/**
 * @author akbar
 * @since 2018, May 20
 */

public class App extends Application
        implements HasActivityInjector {

    private static AppComponent sAppComponent;

    @Inject
    DispatchingAndroidInjector<Activity> mActivityInjector;

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sAppComponent = DaggerAppComponent.builder().application(this).build();
        sAppComponent.inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mActivityInjector;
    }
}
