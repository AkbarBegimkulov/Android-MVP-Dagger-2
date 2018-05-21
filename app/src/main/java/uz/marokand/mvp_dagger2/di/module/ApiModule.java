package uz.marokand.mvp_dagger2.di.module;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import uz.marokand.mvp_dagger2.data.GithubApiService;
import uz.marokand.mvp_dagger2.di.annotation.GithubApiInfo;
import uz.marokand.mvp_dagger2.util.AppConstants;

/**
 * The module to provide application all api services
 *
 * @author akbar
 * @since 2018, May 21
 */

@Module(includes = RetrofitModule.class)
public class ApiModule implements AppConstants {

    /**
     * Provides the github api url, it's saved here {@link AppConstants}.
     *
     * @return base api url for github
     */
    @Provides
    @GithubApiInfo
    static String provideGithubApiUrl() {
        return GITHUB_API_URL;
    }

    /**
     * The github api service
     *
     * @param retrofit for github api
     * @return github api service
     */
    @Provides
    @GithubApiInfo
    static GithubApiService provideGithubApiService(@GithubApiInfo Retrofit retrofit) {
        return retrofit.create(GithubApiService.class);
    }
}
