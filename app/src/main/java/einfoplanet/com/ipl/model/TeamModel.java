package einfoplanet.com.ipl.model;

import android.graphics.Bitmap;

import org.json.JSONObject;

import einfoplanet.com.ipl.constants.Constants;
import einfoplanet.com.ipl.utility.PictureUtil;

/**
 * Created by eshvar289 on 5/6/16.
 */

public class TeamModel {
    public String mTeamName;
    public String mTeamCaptain;
    public String mTeamCoach;
    public String mTeamOwner;
    public String mTeamHomeVenue;
    public String mTeamLogoUrl;
    public String mFolderName;
    public Bitmap mImageBitmap;

    public void setTeamViewData(JSONObject jsonObject) {
        //--optString() Returns the value mapped by name if it exists, coercing it if necessary,
        // or the empty string if no such mapping exists
        mTeamName = jsonObject.optString(Constants.KEY_TEAM_NAME);
        mTeamCaptain = jsonObject.optString(Constants.KEY_TEAM_CAPTAIN);
        mTeamCoach = jsonObject.optString(Constants.KEY_TEAM_COACH);
        mTeamOwner = jsonObject.optString(Constants.KEY_TEAM_OWNER);
        mTeamHomeVenue = jsonObject.optString(Constants.KEY_TEAM_HOME_VENUE);
        mTeamLogoUrl = jsonObject.optString(Constants.KEY_TEAM_IMG_URL);
        mFolderName = jsonObject.optString(Constants.FOLDER_NAME);
        mImageBitmap = PictureUtil.urlToBitmap(mTeamLogoUrl);
    }
}
