package uz.marokand.mvp_dagger2.data;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Single;
import uz.marokand.mvp_dagger2.data.model.Repo;
import uz.marokand.mvp_dagger2.data.model.User;

/**
 * The service to call upay api methods
 *
 * @author akbar
 * @since 2018, May 21
 */

public interface GithubApiService {

    @GET("users/{user}")
    Single<User> getUser(@Path("user") String userName);

    @GET("users/{user}/repos")
    Single<List<Repo>> getRepositories(@Path("user") String userName);
}
