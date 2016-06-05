package einfoplanet.com.ipl.controller;

import android.content.Context;

import java.util.ArrayList;

import einfoplanet.com.ipl.constants.Constants;
import einfoplanet.com.ipl.loacaldatabase.PlayerDBHandler;
import einfoplanet.com.ipl.model.PlayerModel;
import einfoplanet.com.ipl.utility.InternetConnection;
import einfoplanet.com.ipl.utility.JSONParser;
import einfoplanet.com.ipl.utility.PictureUtil;
import einfoplanet.com.ipl.viewmodel.PlayerViewModel;

import static einfoplanet.com.ipl.utility.HttpManager.getData;

/**
 * 1.It is data controller in MVVM for the PlayerView.
 * 2.interact with local db and cloud interaction.
 * 3.it provide interface for VM to interact with DbController,it abstract the
 * DBHandler,model and service.
 * 4.Controller controls the flow of data between the models,DB handler and
 * services.it will acts as manager.
 * 5.controller will get the data from server then controller will handle database call .
 * then controller will call database and give required attributes to viewModel.
 */

public class PlayerViewController {
    private String mIntentTeamName;
    private Context context;
    private ArrayList<PlayerModel> mPlayerModelData;
    private ArrayList<PlayerViewModel> mPlayerViewModelData;

    private PlayerDBHandler mPlayerDBHandler;

    public PlayerViewController(Context context) {
        this.context = context;
        mPlayerDBHandler = new PlayerDBHandler(context);
        mPlayerModelData = new ArrayList<>();
        mPlayerViewModelData = new ArrayList<>();
    }

    public ArrayList<PlayerViewModel> getPlayerViewModelData(final String mIntentTeamName) {
        this.mIntentTeamName=mIntentTeamName;
        PlayerViewModel mPlayerViewModel;
        if ((!InternetConnection.checkInternetConnection(context)) && mPlayerDBHandler.dataCheckFromDB()) {
            mPlayerModelData = getPlayerInfoDataFromDB();

            for (int i = 0; i < mPlayerModelData.size(); i++) {
                mPlayerViewModel = new PlayerViewModel();
                mPlayerViewModel.setmPlayerName(mPlayerModelData.get(i).mPlayerName);
                mPlayerViewModel.setmPlayerRole(mPlayerModelData.get(i).mPlayerRole);
                mPlayerViewModel.setmPlayerBattingStyle(mPlayerModelData.get(i).mPlayerBattingStyle);
                mPlayerViewModel.setmPlayerBowlingStyle(mPlayerModelData.get(i).mPlayerBowlingStyle);
                mPlayerViewModel.setmPlayerNationality(mPlayerModelData.get(i).mPlayerNationality);
                mPlayerViewModel.setmPlayerDOB(mPlayerModelData.get(i).mPlayerDOB);
                mPlayerViewModel.setmPlayerImageBitmap(PictureUtil.loadFileFromStorage(mPlayerModelData.get(i).mPlayerName, mPlayerModelData.get(i).mFolderName));

                mPlayerViewModelData.add(mPlayerViewModel);
            }
        } else {
            mPlayerModelData = getPlayerInfoDataFromREST();

            for (int i = 0; i < mPlayerModelData.size(); i++) {
                mPlayerViewModel = new PlayerViewModel();
                mPlayerViewModel.setmPlayerName(mPlayerModelData.get(i).mPlayerName);
                mPlayerViewModel.setmPlayerRole(mPlayerModelData.get(i).mPlayerRole);
                mPlayerViewModel.setmPlayerBattingStyle(mPlayerModelData.get(i).mPlayerBattingStyle);
                mPlayerViewModel.setmPlayerBowlingStyle(mPlayerModelData.get(i).mPlayerBowlingStyle);
                mPlayerViewModel.setmPlayerNationality(mPlayerModelData.get(i).mPlayerNationality);
                mPlayerViewModel.setmPlayerDOB(mPlayerModelData.get(i).mPlayerDOB);
                mPlayerViewModel.setmPlayerImageBitmap(PictureUtil.urlToImageDownload(Constants.IPL_BASE_URL+mPlayerModelData.get(i).mPlayerImageUrl,
                        mPlayerModelData.get(i).mPlayerName,
                        mPlayerModelData.get(i).mFolderName));

                mPlayerViewModelData.add(mPlayerViewModel);
            }
        }
        return mPlayerViewModelData;
    }

    private ArrayList<PlayerModel> getPlayerInfoDataFromREST() {
        mPlayerModelData = JSONParser.parsePlayerModelDataFromREST(getData(getTeamPlayerInfoUrl()), context);
        return mPlayerModelData;
    }

    private ArrayList<PlayerModel> getPlayerInfoDataFromDB() {
        mPlayerModelData = JSONParser.jsonParserForPlayerDataFromDB(mPlayerDBHandler.getPlayerInfoDatabase());
        return mPlayerModelData;
    }

    private String getTeamPlayerInfoUrl(){
        String finalUrl;
        return finalUrl = Constants.IPL_BASE_URL+mIntentTeamName.toLowerCase().replace(" ","_")+".json";
    }
}
