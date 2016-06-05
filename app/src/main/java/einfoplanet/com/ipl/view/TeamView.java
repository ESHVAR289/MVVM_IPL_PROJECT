package einfoplanet.com.ipl.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import einfoplanet.com.ipl.R;
import einfoplanet.com.ipl.adapter.TeamViewBindingAdapter;
import einfoplanet.com.ipl.constants.Constants;
import einfoplanet.com.ipl.controller.TeamViewController;
import einfoplanet.com.ipl.loacaldatabase.TeamDBHandler;
import einfoplanet.com.ipl.recyclerinterface.RecyclerClickListener;
import einfoplanet.com.ipl.utility.InternetConnection;
import einfoplanet.com.ipl.utility.RecyclerTouchListener;
import einfoplanet.com.ipl.viewmodel.TeamViewModel;

public class TeamView extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TeamViewController mTeamViewController;
    private ArrayList<TeamViewModel> mTeamViewModelData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialization of instances
        mRecyclerView = (RecyclerView) findViewById(R.id.team_recycler_view);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(TeamView.this,mRecyclerView, new RecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                TeamViewModel mTeamViewModel=mTeamViewModelData.get(position);
                Intent intent=new Intent(TeamView.this,PlayerView.class);
                intent.putExtra(Constants.TEAM_NAME,mTeamViewModel.getmTeamName());
                startActivity(intent);
                Toast.makeText(TeamView.this,mTeamViewModel.getmTeamName()+" is selected",Toast.LENGTH_LONG).show();
            }
        }));


        TeamDBHandler mTeamDBHandler = new TeamDBHandler(TeamView.this);
        mTeamViewController = new TeamViewController(TeamView.this);

        LoadContentData contentData = new LoadContentData();
        contentData.execute();

    }
    private class LoadContentData extends AsyncTask<Void, Void, ArrayList<TeamViewModel>> {

        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(TeamView.this);
            if (InternetConnection.checkInternetConnection(TeamView.this)) {
                mProgressDialog.setMessage("Loading");
                mProgressDialog.setTitle("please wait");
                mProgressDialog.show();
            } else {
                InternetConnection.noInternetAlertDialog(TeamView.this);
            }

        }

        @Override
        protected ArrayList<TeamViewModel> doInBackground(Void... params) {
            mTeamViewModelData = mTeamViewController.getTeamViewModelData();
            return mTeamViewModelData;
        }

        @Override
        protected void onPostExecute(ArrayList<TeamViewModel> teamViewModels) {

            //creating an IPLTeamViewCustomAdapter instance and set the adapter with dummy data
            TeamViewBindingAdapter mAdapter = new TeamViewBindingAdapter(TeamView.this, teamViewModels);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(TeamView.this));
            mRecyclerView.setAdapter(mAdapter);
            mProgressDialog.hide();
        }
    }

}
