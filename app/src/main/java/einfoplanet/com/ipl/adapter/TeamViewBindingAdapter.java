package einfoplanet.com.ipl.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import einfoplanet.com.ipl.R;
import einfoplanet.com.ipl.databinding.TeamRecyclerLayoutBinding;
import einfoplanet.com.ipl.viewmodel.TeamViewModel;

/**
 * Created by eshvar289 on 5/6/16.
 */

 class TeamViewBindingAdapter extends RecyclerView.Adapter<TeamViewBindingAdapter.TeamViewHolder>{
    private ArrayList<TeamViewModel> mTeamViewModelData;

    public TeamViewBindingAdapter(Context context,ArrayList<TeamViewModel> mTeamViewModelData){
        this.mTeamViewModelData=mTeamViewModelData;
    }

    @Override
    public TeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TeamRecyclerLayoutBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.team_recycler_layout,parent,false);
        return new TeamViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(TeamViewHolder holder, int position) {
        holder.bindConnection(mTeamViewModelData.get(position));
    }

    @Override
    public int getItemCount() {
        return mTeamViewModelData.size();
    }

    class TeamViewHolder extends RecyclerView.ViewHolder {
        TeamRecyclerLayoutBinding binding;
        TeamViewHolder(TeamRecyclerLayoutBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
        void bindConnection(TeamViewModel mTeamViewModel){
            binding.setInformation(mTeamViewModel);
        }
    }
}
