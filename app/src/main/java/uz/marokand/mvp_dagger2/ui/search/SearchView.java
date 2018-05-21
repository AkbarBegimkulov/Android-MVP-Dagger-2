package uz.marokand.mvp_dagger2.ui.search;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;

import uz.marokand.mvp_dagger2.data.model.User;

/**
 * @author akbar
 * @since 2018, May 21
 */

interface SearchView extends MvpView {

    void showMessage(String message);

    void showMessage(@StringRes int messageId);

    void showProgress(@StringRes int messageId);

    void hideProgress();

    void startMain(User user);
}
