package uz.marokand.mvp_dagger2.ui.main;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import uz.marokand.mvp_dagger2.data.model.Repo;

/**
 * @author akbar
 * @since 2018, May 21
 */

interface MainView extends MvpView {

    void initUserInfo(String photo, String name, String secondary);

    void initRepositories(List<Repo> repositories);

    void openWebUrl(String webUrl);
}
