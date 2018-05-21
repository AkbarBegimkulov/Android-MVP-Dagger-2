package uz.marokand.mvp_dagger2.ui.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import uz.marokand.mvp_dagger2.App;
import uz.marokand.mvp_dagger2.data.model.Repo;
import uz.marokand.mvp_dagger2.data.model.User;

/**
 * @author akbar
 * @since 2018, May 21
 */

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    MainPresenter() {
        App.getAppComponent().inject(this);
    }

    public void setUser(User user) {
        getViewState().initUserInfo(user.getAvatar(), user.getName(), user.getSecondaryText());
        getViewState().initRepositories(user.getRepositories());
    }

    public void onRepositoryClick(Repo repository) {
        if (repository.getUrl().equals("")) return;
        getViewState().openWebUrl(repository.getUrl());
    }
}
