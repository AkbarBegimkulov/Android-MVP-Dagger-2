package uz.marokand.mvp_dagger2.ui.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import uz.marokand.mvp_dagger2.App;

/**
 * @author akbar
 * @since 2018, May 21
 */

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView>{

    public MainPresenter() {
        App.getAppComponent().inject(this);
    }
}
