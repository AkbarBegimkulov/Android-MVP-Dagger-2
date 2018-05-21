package uz.marokand.mvp_dagger2.ui.search;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import uz.marokand.mvp_dagger2.App;
import uz.marokand.mvp_dagger2.R;
import uz.marokand.mvp_dagger2.data.ApiService;
import uz.marokand.mvp_dagger2.data.model.User;

/**
 * @author akbar
 * @since 2018, May 21
 */

@InjectViewState
public class SearchPresenter extends MvpPresenter<SearchView> {

    @Inject
    ApiService mApiService;

    private User mUser;

    SearchPresenter() {
        App.getAppComponent().inject(this);
    }

    public void onSearch(String userName) {
        if (userName.isEmpty()) {
            getViewState().showMessage(R.string.empty_login);
            return;
        }
        if (mApiService.noConnection()) {
            getViewState().showMessage(R.string.no_internet);
            return;
        }
        getUserInfo(userName);
    }

    private void getUserInfo(String userName) {
        getViewState().showProgress(R.string.msg_api_calling);
        mApiService.getUser(userName).subscribe(response -> {
            mUser = response;
            getUserRepositories(response.getLogin());
        }, error -> {
            getViewState().hideProgress();
            getViewState().showMessage(R.string.user_not_found);
        });
    }

    private void getUserRepositories(String userName) {
        mApiService.getRepositories(userName).subscribe(response -> {
            getViewState().hideProgress();
            mUser.addRepositories(response);
            getViewState().startMain(mUser);
        }, error -> {
            getViewState().hideProgress();
            getViewState().showMessage(R.string.user_not_found);
        });
    }
}
