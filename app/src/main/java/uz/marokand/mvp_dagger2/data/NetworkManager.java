package uz.marokand.mvp_dagger2.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uz.marokand.mvp_dagger2.data.model.Repo;
import uz.marokand.mvp_dagger2.data.model.User;

/**
 * The manager to implements api service
 *
 * @author akbar
 * @since 2018, May 21
 */

public class NetworkManager implements ApiService {

    private final Context mContext;
    private final GithubApiService mGithubApiService;

    public NetworkManager(Context context, GithubApiService githubApiService) {

        mContext = context;
        mGithubApiService = githubApiService;
    }

    @Override
    public boolean noConnection() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm != null ? cm.getActiveNetworkInfo() : null;
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return !activeNetwork.isConnected();
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return !activeNetwork.isConnected();
        }
        return true;
    }

    @Override
    public Single<User> getUser(String userName) {
        return mGithubApiService.getUser(userName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<Repo>> getRepositories(String userName) {
        return mGithubApiService.getRepositories(userName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
