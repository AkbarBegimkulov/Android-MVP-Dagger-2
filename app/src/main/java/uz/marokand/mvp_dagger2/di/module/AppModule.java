package uz.marokand.mvp_dagger2.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import uz.marokand.mvp_dagger2.App;
import uz.marokand.mvp_dagger2.di.annotation.AppContext;
import uz.marokand.mvp_dagger2.ui.main.MainActivity;
import uz.marokand.mvp_dagger2.ui.main.MainActivityModule;
import uz.marokand.mvp_dagger2.ui.search.SearchActivity;
import uz.marokand.mvp_dagger2.ui.search.SearchActivityModule;

/**
 * Provides application-wide dependencies.
 *
 * @author akbar
 * @since 2018, May 21
 */

@Singleton
@Module(includes = {NetworkModule.class})
public abstract class AppModule {

    /**
     * Provides the application context.
     */
    @Provides
    @AppContext
    static Context provideContext(Application application) {
        return application.getApplicationContext();
    }

    @Binds
    @Singleton
    /*
     * Singleton annotation isn't necessary since Application instance is unique but is here for
     * convention. In general, providing Activity, Fragment, BroadcastReceiver, etc does not require
     * them to be scoped since they are the components being injected and their instance is unique.
     *
     * However, having a scope annotation makes the module easier to read. We wouldn't have to look
     * at what is being provided in order to understand its scope.
     */
    abstract Application application(App app);

    /**
     * Provides the injector for the {@link SearchActivity}, which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @ContributesAndroidInjector(modules = SearchActivityModule.class)
    abstract SearchActivity launchActivityInjector();

    /**
     * Provides the injector for the {@link MainActivity}, which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivityInjector();
}
