package uz.marokand.mvp_dagger2.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import uz.marokand.mvp_dagger2.R;
import uz.marokand.mvp_dagger2.data.model.Repo;

/**
 * @author akbar
 * @since 2018, May 20
 */

class RepositoryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.name_view)
    TextView mNameView;
    @BindView(R.id.description_view)
    TextView mDescriptionView;
    @BindView(R.id.watchers_view)
    TextView mWatchersView;
    @BindView(R.id.stars_view)
    TextView mStartsView;
    @BindView(R.id.forks_view)
    TextView mForksView;

    private Repo mRepository;
    private ItemClickListener mItemClickListener;

    RepositoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.findViewById(R.id.item).setOnClickListener(v -> {
            if (mItemClickListener != null && mRepository != null) {
                mItemClickListener.onItemClick(getAdapterPosition(), mRepository);
            }
        });
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setRepository(Repo repository) {
        mRepository = repository;
        mNameView.setText(repository.getName());
        mDescriptionView.setText(repository.getDescription());
        mWatchersView.setText(repository.getWatchers());
        mStartsView.setText(repository.getStars());
        mForksView.setText(repository.getForks());
    }

    public interface ItemClickListener {

        void onItemClick(int position, Repo repository);
    }
}
