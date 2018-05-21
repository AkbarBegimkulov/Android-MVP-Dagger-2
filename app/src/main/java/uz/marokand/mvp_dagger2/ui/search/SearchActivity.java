package uz.marokand.mvp_dagger2.ui.search;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import uz.marokand.mvp_dagger2.Navigator;
import uz.marokand.mvp_dagger2.R;
import uz.marokand.mvp_dagger2.data.model.User;

public class SearchActivity extends MvpAppCompatActivity
        implements SearchView {

    @InjectPresenter
    SearchPresenter mPresenter;
    @Inject
    Navigator mNavigator;

    @BindView(R.id.login)
    EditText mSearchView;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        mSearchView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                /*
                 * hide keyboard
                 */
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
                }

                mPresenter.onSearch(mSearchView.getText().toString());
                return true;
            }
            return false;
        });
    }

    @OnClick(R.id.btn_go)
    void onGoClicked() {
        mPresenter.onSearch(mSearchView.getText().toString());
    }

    @Override
    public void showMessage(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton(R.string.action_ok, null)
                .create().show();
    }

    @Override
    public void showMessage(int messageId) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(messageId)
                .setPositiveButton(R.string.action_ok, null)
                .create().show();
    }

    @Override
    public void showProgress(int messageId) {
        mProgressDialog = new ProgressDialog(this, R.style.AlertDialog);
        mProgressDialog.setMessage(getString(messageId));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void startMain(User user) {
        mNavigator.toMain(this, user);
    }
}
