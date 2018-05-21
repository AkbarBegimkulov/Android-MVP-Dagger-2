package uz.marokand.mvp_dagger2.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import uz.marokand.mvp_dagger2.BuildConfig;
import uz.marokand.mvp_dagger2.di.annotation.GithubApiInfo;

import static uz.marokand.mvp_dagger2.data.ApiService.CONNECT_TIMEOUT;
import static uz.marokand.mvp_dagger2.data.ApiService.READ_TIMEOUT;
import static uz.marokand.mvp_dagger2.data.ApiService.WRITE_TIMEOUT;

/**
 * The module to provide retrofit dependencies
 *
 * @author akbar
 * @since 2018, May 21
 */

@Module
public class RetrofitModule {

    /**
     * An OkHttp interceptor which logs HTTP request and response data.
     * <p>
     * You can change the log level at any time by calling setLevel
     * <p>
     * Logging too much information will blow up your Android monitor,
     * that’s why OkHttp’s logging interceptor has four log levels:
     * <b>NONE</b>, <b>BASIC</b>, <b>HEADERS</b>, <b>BODY</b>.
     * <p>
     * We’ll walk you through each of the log levels and describe their output.
     * <b>None</b>
     * No logging
     * <b>Basic</b>
     * Log request type, url, size of request body, response status and size of response body.
     * <b>Headers</b>
     * Log request and response headers, request type, url, response status.
     * <b>Body</b>
     * Log request and response headers and body.
     *
     * @return the OkHttp interceptor
     */
    @Provides
    static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return interceptor;
    }

    /**
     * @param httpLoggingInterceptor the OkHttp interceptor which logs
     *                               HTTP request and response data on android studio logcat
     * @return the HTTP client
     */
    @Provides
    static OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }


    /**
     * Gson (also known as Google Gson) is an open source Java library to serialize
     * and deserialize Java objects to (and from) JSON.
     *
     * @return the gson object
     */
    @Provides
    static Gson provideGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    /**
     * The converter which uses for JSON
     *
     * @param gson for JSON
     * @return JSON converter factory
     */
    @Provides
    static Converter.Factory provideJsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    /**
     * The builder a new {@link Retrofit}.
     *
     * @param okHttpClient the HTTP client
     * @param factory      the converter for json
     * @return the builder
     */
    @Provides
    static Retrofit.Builder provideGsonRetrofitBuilder(OkHttpClient okHttpClient,
                                                       Converter.Factory factory) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(factory);
    }

    /**
     * Retrofit is that turns your HTTP API into a Java interface.
     *
     * @param builder for gson
     * @param baseUrl for github api
     * @return the retrofit object which calls for github api
     */
    @Provides
    @GithubApiInfo
    static Retrofit provideGithubRetrofit(Retrofit.Builder builder, @GithubApiInfo String baseUrl) {

        return builder.baseUrl(baseUrl).build();
    }
}
