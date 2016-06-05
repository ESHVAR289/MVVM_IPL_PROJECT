package einfoplanet.com.ipl.model;

import android.graphics.Bitmap;

import org.json.JSONObject;

import einfoplanet.com.ipl.constants.Constants;
import einfoplanet.com.ipl.utility.PictureUtil;

/**
 * Created by eshvar289 on 5/6/16.
 */

public class PlayerModel {
    public String mPlayerName;
    public String mPlayerRole;
    public String mPlayerBattingStyle;
    public String mPlayerBowlingStyle;
    public String mPlayerNationality;
    public String mPlayerDOB;
    public String mPlayerImageUrl;
    public String mFolderName;
    public Bitmap mPlayerImageBitmap;

    public void setTeamPlayerData(JSONObject jsonObject){
        mPlayerName=jsonObject.optString(Constants.KEY_PLAYER_NAME);
        mPlayerRole=jsonObject.optString(Constants.KEY_PLAYER_ROLE);
        mPlayerBattingStyle=jsonObject.optString(Constants.KEY_PLAYER_BATTING_STYLE);
        mPlayerBowlingStyle=jsonObject.optString(Constants.KEY_PLAYER_BOWLING_STYLE);
        mPlayerNationality=jsonObject.optString(Constants.KEY_PLAYER_NATIONALITY);
        mPlayerDOB=jsonObject.optString(Constants.KEY_PLAYER_DOB);
        mPlayerImageUrl=jsonObject.optString(Constants.KEY_PLAYER_IMG_URL);
        mFolderName=jsonObject.optString(Constants.FOLDER_NAME);
        mPlayerImageBitmap= PictureUtil.urlToBitmap(mPlayerImageUrl);
    }
}
