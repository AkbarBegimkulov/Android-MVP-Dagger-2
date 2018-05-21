package uz.marokand.mvp_dagger2.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uz.marokand.mvp_dagger2.data.ApiService;
import uz.marokand.mvp_dagger2.data.GithubApiService;
import uz.marokand.mvp_dagger2.data.NetworkManager;
import uz.marokand.mvp_dagger2.di.annotation.AppContext;
import uz.marokand.mvp_dagger2.di.annotation.GithubApiInfo;
import uz.marokand.mvp_dagger2.util.AppConstants;

/**
 * The module to provide api service
 *
 * @author akbar
 * @since 2018, May 21
 */

@Singleton
@Module(includes = ApiModule.class)
public class NetworkModule implements AppConstants {

    /**
     * Provides the ApiService to call network api methods
     *
     * @param context          the application context
     * @param githubApiService for github api methods
     * @return api service
     */
    @Provides
    @Singleton
    static ApiService provideApiService(@AppContext Context context,
                                        @GithubApiInfo GithubApiService githubApiService) {

        return new NetworkManager(context, githubApiService);
    }
}
