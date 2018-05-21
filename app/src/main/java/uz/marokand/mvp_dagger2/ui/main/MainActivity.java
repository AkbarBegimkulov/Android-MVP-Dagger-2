package uz.marokand.mvp_dagger2.ui.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import uz.marokand.mvp_dagger2.R;
import uz.marokand.mvp_dagger2.data.model.Repo;
import uz.marokand.mvp_dagger2.data.model.User;
import uz.marokand.mvp_dagger2.widget.RepositoriesAdapter;

import static uz.marokand.mvp_dagger2.Navigator.USER;

public class MainActivity extends MvpAppCompatActivity
        implements MainView, RepositoriesAdapter.ItemClickListener {

    @InjectPresenter
    MainPresenter mPresenter;

    @BindView(R.id.avatar)
    RoundedImageView mAvatarView;
    @BindView(R.id.name_view)
    TextView mNameView;
    @BindView(R.id.secondary_view)
    TextView mSecondaryView;
    @BindView(R.id.no_repositories)
    TextView mEmptyView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        User user = intent.getParcelableExtra(USER);
        if (user == null) return;
        mPresenter.setUser(user);
    }

    @Override
    public void initUserInfo(String photo, String name, String secondary) {
        Glide.with(this)
                .load(photo)
                .apply(new RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.bg_avatar))
                .into(mAvatarView);
        mNameView.setText(name);
        mSecondaryView.setText(secondary);
    }

    @Override
    public void initRepositories(List<Repo> repositories) {
        if (repositories.size() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            RepositoriesAdapter adapter = new RepositoriesAdapter(repositories);
            adapter.setItemClickListener(this);
            mRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void openWebUrl(String webUrl) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl)));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int position, Repo repository) {
        mPresenter.onRepositoryClick(repository);
    }
}
