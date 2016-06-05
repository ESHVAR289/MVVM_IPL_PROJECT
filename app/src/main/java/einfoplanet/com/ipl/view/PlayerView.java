package einfoplanet.com.ipl.view;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import einfoplanet.com.ipl.R;
import einfoplanet.com.ipl.adapter.PlayerViewBindingAdapter;
import einfoplanet.com.ipl.constants.Constants;
import einfoplanet.com.ipl.controller.PlayerViewController;
import einfoplanet.com.ipl.loacaldatabase.PlayerDBHandler;
import einfoplanet.com.ipl.utility.InternetConnection;
import einfoplanet.com.ipl.viewmodel.PlayerViewModel;

public class PlayerView extends AppCompatActivity {
    private String mIntentTeamName;
    private RecyclerView mRecyclerView;
    private PlayerViewController mPlayerViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //--Initialization of instances
        mRecyclerView = (RecyclerView) findViewById(R.id.player_recycler_view);
        PlayerDBHandler mPlayerDBHandler = new PlayerDBHandler(this);
        mPlayerViewController = new PlayerViewController(this);
        String mIntentTeamName=getIntent().getStringExtra(Constants.TEAM_NAME);
        LoadPlayerData playerData = new LoadPlayerData();
        playerData.execute();
    }

    private class LoadPlayerData extends AsyncTask<Void, Void, ArrayList<PlayerViewModel>> {
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(PlayerView.this);
            if (InternetConnection.checkInternetConnection(PlayerView.this)) {
                mProgressDialog.setMessage("Loading");
                mProgressDialog.setTitle("please wait");
                mProgressDialog.show();
            } else {
                InternetConnection.noInternetAlertDialog(PlayerView.this);
            }
        }

        @Override
        protected ArrayList<PlayerViewModel> doInBackground(Void... params) {
            ArrayList<PlayerViewModel> mPlayerViewModelData = mPlayerViewController.getPlayerViewModelData(mIntentTeamName);
            return mPlayerViewModelData;
        }

        @Override
        protected void onPostExecute(ArrayList<PlayerViewModel> mTeamPlayerViewModel) {
            PlayerViewBindingAdapter mPlayerAdapter = new PlayerViewBindingAdapter(PlayerView.this, mTeamPlayerViewModel);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(PlayerView.this));
            mRecyclerView.setAdapter(mPlayerAdapter);
            mProgressDialog.hide();
        }
    }
}
