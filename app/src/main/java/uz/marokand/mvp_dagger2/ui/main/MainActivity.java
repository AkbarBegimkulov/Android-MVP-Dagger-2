package uz.marokand.mvp_dagger2.ui.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import uz.marokand.mvp_dagger2.R;
import uz.marokand.mvp_dagger2.model.Repo;
import uz.marokand.mvp_dagger2.model.User;
import uz.marokand.mvp_dagger2.widget.RepositoriesAdapter;

public class MainActivity extends AppCompatActivity
        implements RepositoriesAdapter.ItemClickListener {

    private RoundedImageView mAvatarView;
    private TextView mNameView;
    private TextView mSecondaryView;
    private TextView mEmptyView;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAvatarView = findViewById(R.id.avatar);
        mNameView = findViewById(R.id.name_view);
        mSecondaryView = findViewById(R.id.secondary_view);
        mEmptyView = findViewById(R.id.no_repositories);
        mRecyclerView = findViewById(R.id.recycler_view);

        Intent intent = getIntent();
        User user = intent.getParcelableExtra(User.USER);

        if (user == null) return;

        setUserInfos(user);


    }

    private void setUserInfos(User user){
        Glide.with(this)
                .load(user.getAvatar())
                .apply(new RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.bg_avatar))
                .into(mAvatarView);
        mNameView.setText(user.getName());
        mSecondaryView.setText(user.getSecondaryText());


        if (user.getmRepositories().size() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            RepositoriesAdapter adapter = new RepositoriesAdapter(user.getmRepositories());
            adapter.setItemClickListener(this);
            mRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(int position, Repo repository) {
        if (repository.getUrl().equals("")) return;
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(repository.getUrl())));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
