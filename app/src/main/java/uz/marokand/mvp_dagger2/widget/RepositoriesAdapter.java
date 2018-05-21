package uz.marokand.mvp_dagger2.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uz.marokand.mvp_dagger2.R;
import uz.marokand.mvp_dagger2.data.model.Repo;

/**
 * @author akbar
 * @since 2018, May 20
 */

public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoryViewHolder>
        implements RepositoryViewHolder.ItemClickListener {

    private List<Repo> mRepositories;
    private ItemClickListener mItemClickListener;

    public RepositoriesAdapter(List<Repo> repositories) {
        mRepositories = repositories == null ? new ArrayList<Repo>() : repositories;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RepositoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RepositoryViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_repository, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryViewHolder holder, int position) {
        Repo repository = mRepositories.get(position);
        holder.setRepository(repository);
        holder.setItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mRepositories.size();
    }

    @Override
    public void onItemClick(int position, Repo repository) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(position, repository);
        }
    }

    public interface ItemClickListener {

        void onItemClick(int position, Repo repository);
    }
}
