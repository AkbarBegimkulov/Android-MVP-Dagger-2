package uz.marokand.mvp_dagger2.di;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import uz.marokand.mvp_dagger2.App;
import uz.marokand.mvp_dagger2.di.module.AppModule;

/**
 * Injects application dependencies.
 *
 * @author akbar
 * @since 2018, May 21
 */

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class})
public interface AppComponent {

    void inject(App app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(App app);

        AppComponent build();
    }
}
