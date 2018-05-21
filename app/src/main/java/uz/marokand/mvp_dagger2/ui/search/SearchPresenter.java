package uz.marokand.mvp_dagger2.ui.search;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import uz.marokand.mvp_dagger2.App;

/**
 * @author akbar
 * @since 2018, May 21
 */

@InjectViewState
public class SearchPresenter extends MvpPresenter<SearchView> {

    public SearchPresenter() {
        App.getAppComponent().inject(this);
    }
}
