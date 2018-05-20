package uz.marokand.mvp_dagger2.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import uz.marokand.mvp_dagger2.R;
import uz.marokand.mvp_dagger2.model.Repo;

/**
 * @author akbar
 * @since 2018, May 20
 */

class RepositoryViewHolder extends RecyclerView.ViewHolder {

    private TextView mNameView;
    private TextView mDescriptionView;
    private TextView mWatchersView;
    private TextView mStartsView;
    private TextView mForksView;

    private Repo mRepository;
    private ItemClickListener mItemClickListener;

    RepositoryViewHolder(View itemView) {
        super(itemView);
        mNameView = itemView.findViewById(R.id.name_view);
        mDescriptionView = itemView.findViewById(R.id.description_view);
        mWatchersView = itemView.findViewById(R.id.watchers_view);
        mStartsView = itemView.findViewById(R.id.stars_view);
        mForksView = itemView.findViewById(R.id.forks_view);
        itemView.findViewById(R.id.item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null && mRepository != null) {
                    mItemClickListener.onItemClick(getAdapterPosition(), mRepository);
                }
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
