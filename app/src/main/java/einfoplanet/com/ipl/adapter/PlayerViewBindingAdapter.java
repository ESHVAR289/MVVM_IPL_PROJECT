package einfoplanet.com.ipl.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import einfoplanet.com.ipl.R;
import einfoplanet.com.ipl.databinding.PlayerRecyclerLayoutBinding;
import einfoplanet.com.ipl.viewmodel.PlayerViewModel;

/**
 * Created by eshvar289 on 5/6/16.
 */

class PlayerViewBindingAdapter extends RecyclerView.Adapter<PlayerViewBindingAdapter.PlayerViewHolder>{
    private ArrayList<PlayerViewModel> mPlayerViewModelData;

    public PlayerViewBindingAdapter(Context mContext, ArrayList<PlayerViewModel> mPlayerViewModelData) {
        this.mPlayerViewModelData = mPlayerViewModelData;
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PlayerRecyclerLayoutBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.player_recycler_layout,parent,false);
        return new PlayerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        holder.bindConnection(mPlayerViewModelData.get(position));
    }

    @Override
    public int getItemCount() {
        return mPlayerViewModelData.size();
    }

    class PlayerViewHolder extends RecyclerView.ViewHolder {
        PlayerRecyclerLayoutBinding binding;
        PlayerViewHolder(PlayerRecyclerLayoutBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
        void bindConnection(PlayerViewModel playerViewModel){
            binding.setPlayer(playerViewModel);
        }
    }
}
