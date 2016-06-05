package einfoplanet.com.ipl.controller;

import android.content.Context;

import java.util.ArrayList;

import einfoplanet.com.ipl.constants.Constants;
import einfoplanet.com.ipl.loacaldatabase.TeamDBHandler;
import einfoplanet.com.ipl.model.TeamModel;
import einfoplanet.com.ipl.utility.HttpManager;
import einfoplanet.com.ipl.utility.InternetConnection;
import einfoplanet.com.ipl.utility.JSONParser;
import einfoplanet.com.ipl.utility.PictureUtil;
import einfoplanet.com.ipl.viewmodel.TeamViewModel;

/**
 * 1.It is data controller in MVVM.
 * 2.interact with local db and cloud interaction.
 * 3.it provide interface for VM to interact with DbController,it abstract the
 * DBHandler,model and service.
 * 4.Controller controls the flow of data between the models,DB handler and
 * services.it will acts as manager.
 * 5.controller will get the data from server then controller will handle database call .
 * then controller will call database and give required attributes to viewModel.
 */

public class TeamViewController {
    private Context context;
    private ArrayList<TeamModel> mTeamModelData;
    private ArrayList<TeamViewModel> mTeamViewModelData;
    private TeamDBHandler mTeamDBHandler;

    public TeamViewController(Context context) {
        this.context = context;
        mTeamDBHandler = new TeamDBHandler(context);
        mTeamModelData = new ArrayList<>();
        mTeamViewModelData = new ArrayList<>();
    }

    public ArrayList<TeamViewModel> getTeamViewModelData() {
        TeamViewModel mTeamViewModel;
        if ((!InternetConnection.checkInternetConnection(context)) && mTeamDBHandler.dataCheckFromDB()) {
            mTeamModelData = getTeamInfoDataFromDB();

            for (int i = 0; i < mTeamModelData.size(); i++) {
                mTeamViewModel = new TeamViewModel();
                mTeamViewModel.setmTeamName(mTeamModelData.get(i).mTeamName);
                mTeamViewModel.setmTeamOwner(mTeamModelData.get(i).mTeamOwner);
                mTeamViewModel.setmTeamCaptain(mTeamModelData.get(i).mTeamCaptain);
                mTeamViewModel.setmTeamCoach(mTeamModelData.get(i).mTeamCoach);
                mTeamViewModel.setmTeamHomeVenue(mTeamModelData.get(i).mTeamHomeVenue);
                mTeamViewModel.setImageBitmap(PictureUtil.loadFileFromStorage(mTeamModelData.get(i).mTeamName
                        , mTeamModelData.get(i).mFolderName));
                mTeamViewModelData.add(mTeamViewModel);
            }
            return mTeamViewModelData;
        } else {
            mTeamModelData = getTeamInfoData();
            for (int i = 0; i < mTeamModelData.size(); i++) {
                mTeamViewModel = new TeamViewModel();
                mTeamViewModel.setmTeamName(mTeamModelData.get(i).mTeamName);
                mTeamViewModel.setmTeamOwner(mTeamModelData.get(i).mTeamOwner);
                mTeamViewModel.setmTeamCaptain(mTeamModelData.get(i).mTeamCaptain);
                mTeamViewModel.setmTeamCoach(mTeamModelData.get(i).mTeamCoach);
                mTeamViewModel.setmTeamHomeVenue(mTeamModelData.get(i).mTeamHomeVenue);
                mTeamViewModel.setmImageUrl(mTeamModelData.get(i).mTeamLogoUrl);
                mTeamViewModel.setImageBitmap(PictureUtil.urlToImageDownload(Constants.IPL_BASE_URL + mTeamModelData.get(i).mTeamLogoUrl,
                        mTeamModelData.get(i).mTeamName,
                        mTeamModelData.get(i).mFolderName));

                mTeamViewModelData.add(mTeamViewModel);
            }
            return mTeamViewModelData;
        }

    }

    //--Getting the ArrayList<TeamModel> by passing the url and content to the parseTeamModelContent();
    private ArrayList<TeamModel> getTeamInfoData() {

        mTeamModelData = JSONParser.parseTeamModelDataFromREST(HttpManager.getData(Constants.IPL_BASE_URL + Constants.TEAM_VIEW_URL), context);
        return mTeamModelData;
    }

    private ArrayList<TeamModel> getTeamInfoDataFromDB() {
        mTeamModelData = JSONParser.jsonParserForTeamDataFromDB(mTeamDBHandler.getTeamInfoDatabase());
        return mTeamModelData;
    }

}
